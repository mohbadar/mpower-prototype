package af.dfi.lang.security;//package af.aalpr.lang.security;
//
//import af.aalpr.data.model.User;
//import af.aalpr.service.service.CustomUserService;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.SignatureException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.context.support.WebApplicationContextUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//import static af.aalpr.lang.ParamsConfig.HEADER_STRING;
//import static af.aalpr.lang.ParamsConfig.TOKEN_PREFIX;
//
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    // @Autowired
//    // private UserDetailsService userDetailsService;
//    @Autowired
//    private CustomUserService customUserService;
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        logger.error("Request URL = " + request.getRequestURL());
//
//        if(jwtTokenUtil == null) {
//            ServletContext servletContext = request.getServletContext();
//            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//            jwtTokenUtil = webApplicationContext.getBean(JwtTokenUtil.class);
//
//            if(customUserService == null) {
//                customUserService = webApplicationContext.getBean(CustomUserService.class);
//            }
//        }
//
//        // SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
//        // SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
//
//        final String requestTokenHeader = request.getHeader(HEADER_STRING);
//        String username = null;
//        String authToken = null;
//        // JWT Token is in the form "Bearer token". Remove Bearer word and get
//        // only the Token
//        if (requestTokenHeader != null && requestTokenHeader.startsWith(TOKEN_PREFIX)) {
//            authToken = requestTokenHeader.substring(7);
//            try {
//                username = jwtTokenUtil.getUsernameFromToken(authToken);
//            } catch (IllegalArgumentException e) {
//                logger.error("an error occured during getting username from token", e);
//            } catch (ExpiredJwtException e) {
//                logger.warn("the token is expired and not valid anymore", e);
//            } catch(SignatureException e){
//                logger.error("Authentication Failed. Username or Password not valid.");
//            }
//        } else {
//            logger.warn("couldn't find bearer string, will ignore the header");
//        }
//
//        // Once we get the token validate it.
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//            User userDetails = customUserService.loadUserByUsername(username);
//
//            // if token is valid configure Spring Security to manually set
//            // authentication
//            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
//
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                logger.info("authenticated user " + username + ", setting security context");
//                // After setting the Authentication in the context, we specify
//                // that the current user is authenticated. So it passes the
//                // Spring Security Configurations successfully.
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        }
//
//        chain.doFilter(request, response);
//    }
//}