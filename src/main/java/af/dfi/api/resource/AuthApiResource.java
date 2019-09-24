package af.dfi.api.resource;

import af.dfi.api.handler.ResponseHandler;
import af.dfi.core.service.CustomUserService;
import af.dfi.core.service.UserService;
import af.dfi.data.dto.CustomUser;
import af.dfi.data.model.Role;
import af.dfi.data.model.User;
import af.dfi.lang.aspect.Loggable;
import af.dfi.lang.security.JwtTokenUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.Principal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

//import af.aalpr.lang.security.JwtTokenUtil;

@RestController
@RequestMapping(value = "/api")
@Slf4j
public class AuthApiResource extends ResponseHandler {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userAuthService;

    @Autowired
    private CustomUserService customUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    ObjectMapper mapper = new ObjectMapper();


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody Map<String, String> loginUser) throws AuthenticationException {
        final String username = loginUser.get("username");
        final String password = loginUser.get("password");

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final CustomUser customUser = customUserService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(customUser);

        User authenticatedUser = userService.getLoggedInUser();


        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("username", customUser.getUsername());
        result.put("privileges", customUserService.getPrivileges(authenticatedUser.getRoles()));
        result.put("roles", authenticatedUser.getRoles());
        result.put("authenticated", true);

        // User Autorities
        ArrayNode authorities = mapper.createArrayNode();
        for(GrantedAuthority auth : customUser.getAuthorities()) {
            authorities.add(auth.getAuthority());
        }
        result.put("authorities", authorities);

        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity logout(HttpServletRequest request) {
        // TODO delete the token
        return ResponseEntity.ok(true);
    }

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @PutMapping(path = "/config")
    public ObjectNode updateConfig(Authentication authentication, @RequestParam("lang") String lang, @RequestParam("env") String envSlug) throws JsonParseException, IOException
    {
        String currentEnv = envSlug;
        String currentLang = lang;
        System.out.println("current environment:"+currentEnv+"current lang"+currentLang);
        CustomUser userDetails = (CustomUser) authentication.getPrincipal();
        userDetails.setCurrentEnv(currentEnv);
        userDetails.setCurrentLang(currentLang);
        System.out.println("inside user details "+userDetails.getCurrentEnv()+"--"+userDetails.getCurrentLang());
        userDetails = customUserService.loadUserByUsername(userDetails.getUsername(), currentEnv, currentLang);

        Authentication newAuth = new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        // as CurrentEnv and CurrentLang are changed. so it is required to create new token and share it with client
        final String newToken = jwtTokenUtil.generateToken(userDetails);

        User user = userService.findByUsername(userDetails.getUsername());

        ObjectNode result = getConfiguration(user, userDetails);
        result.put("token", newToken);
        return result;
    }

    @Loggable
    @GetMapping(path = "/config")
    public ObjectNode config(Authentication authentication)throws JsonParseException, IOException {
        CustomUser userDetails = (CustomUser) authentication.getPrincipal();

        User user = userService.findByUsername(userDetails.getUsername());
        return getConfiguration(user, userDetails);
    }

    private ObjectNode getConfiguration(User user, CustomUser userDetails) throws JsonParseException, IOException{
        ObjectNode config = mapper.createObjectNode();

        // User Details
        config.put("id", user.getId());
        config.put("name", user.getName());
        config.put("username", userDetails.getUsername());
        config.put("authenticated", true);
        config.put("currentEnv", userDetails.getCurrentEnv());
        config.put("currentLang", userDetails.getCurrentLang());

        // user preferences
//        String prefStr = user.getPreferences();
//        JsonNode prefJson = null;
//        if(prefStr != null && prefStr.equals("") == false) {
//            prefJson = mapper.readTree(prefStr);
//        }
//        config.set("envPreferences", prefJson );

        // User Autorities
        ArrayNode authorities = mapper.createArrayNode();
        for(GrantedAuthority auth : userDetails.getAuthorities()) {
            authorities.add(auth.getAuthority());
        }
        config.set("authorities", authorities);

        // List all accessed environments
        ArrayNode environments = mapper.createArrayNode();
//        for(Environment env : user.getEnvironments()) {
//            ObjectNode newEnvObj = mapper.createObjectNode();
//            newEnvObj.put("id", env.getId());
//            newEnvObj.put("name", env.getName());
//            newEnvObj.put("slug", env.getSlug());
//            environments.add(newEnvObj);
//        }
        config.set("environments", environments);

        config.set("preferences", environments);

        System.out.println("final config is:"+config.toString());
        return config;
    }

    @RequestMapping("/token")
    public Map<String,String> token(HttpSession session) {
        return Collections.singletonMap("token", session.getId());
    }

    @GetMapping("/profile")
    public User profile() {
        log.debug("Getting loggedin user profile");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.toString();

        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        }

        return userAuthService.findByUsername(username);
    }


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
