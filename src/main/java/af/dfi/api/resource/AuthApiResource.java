package af.dfi.api.resource;

import af.dfi.api.handler.ResponseHandler;
import af.dfi.core.service.CustomUserService;
import af.dfi.core.service.UserService;
import af.dfi.data.model.Role;
import af.dfi.data.model.User;
import af.dfi.lang.aspect.Loggable;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//import af.aalpr.lang.security.JwtTokenUtil;

@RestController
public class AuthApiResource extends ResponseHandler {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserService customUserService;

    @Autowired
    private UserService userService;

//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;

    ObjectMapper mapper = new ObjectMapper();

//
//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public ResponseEntity login(@RequestBody Map<String, String> loginUser) throws AuthenticationException {
//        final String username = loginUser.get("username");
//        final String password = loginUser.get("passwrd");
//
//        System.out.println("Username: " + username);
//        System.out.println("Password: " + password);
//
//        final Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        username,
//                        password
//                )
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        final User customUser = customUserService.loadUserByUsername(username);
//        final String token = jwtTokenUtil.generateToken(customUser);
//
//        Map<String, Object> result = new HashMap<>();
//        result.put("token", token);
//
//        return ResponseEntity.ok(result);
//    }
//
//    @RequestMapping(value = "/logout", method = RequestMethod.GET)
//    public ResponseEntity logout(HttpServletRequest request) {
//        // TODO delete the token
//        return ResponseEntity.ok(true);
//    }
//
//    @RequestMapping("/user")
//    public Principal user(Principal user) {
//        return user;
//    }
//
//    @GetMapping("/token")
//    public Map<String,String> token(HttpSession session) {
//        return Collections.singletonMap("token", session.getId());
//    }
//
//    @Loggable
//    @GetMapping(path = "/config")
//    public ObjectNode config(Authentication authentication)throws JsonParseException, IOException {
//        User userDetails = (User) authentication.getPrincipal();
//
//        User user = userService.findByUsername(userDetails.getUsername());
//        return getConfiguration(user);
//    }
//
//    @Loggable
//    private ObjectNode getConfiguration(User user) throws JsonParseException, IOException{
//        ObjectNode config = mapper.createObjectNode();
//
//        // User Details
//        config.put("id", user.getId());
//        config.put("name", user.getName());
//        config.put("username", user.getUsername());
//        config.put("authenticated", true);
//
//        // User Autorities
//        ArrayNode authorities = mapper.createArrayNode();
//        for(GrantedAuthority auth : user.getAuthorities()) {
//            authorities.add(auth.getAuthority());
//        }
//
//        System.out.println("final config is:"+config.toString());
//        return config;
//    }



    /**
     * Authenticate Route
     *
     * @param username
     * @param password
     * @param response
     * @return JSON contains token and user after success authentication.
     * @throws IOException
     */
    @Loggable
    @PostMapping(value = "/authenticate")
    public ResponseEntity<Map<String, Object>> login(@RequestParam(value = "username", required = true) String username,
                                                     @RequestParam(value = "password", required = true) String password,
                                                     HttpServletResponse response)
            throws IOException {

        Map<String, Object> tokenMap = new HashMap<String, Object>();

        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final User appUser = userService.findByUsername(username);
        final String token = Jwts.builder().setSubject(username).claim("roles", appUser.getRoles())
                .setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "aalpr-auth-key").compact();

        tokenMap.put("token", token);
        tokenMap.put("user", appUser);
        tokenMap.put("roles", appUser.getRoles());
        tokenMap.put("is_admin", hasSysAdminRole(appUser.getRoles()));
        return new ResponseEntity<Map<String, Object>>(tokenMap, HttpStatus.OK);

    }

    @Loggable
    private boolean hasSysAdminRole(Collection<Role> roles) {
        boolean result = false;
        for (Role r : roles) {
            if (r.getName().equals("ADMIN")) {
                result = true;
                break;
            }
        }
        return result;
    }


}
