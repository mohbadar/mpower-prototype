package af.dfi.lang.security;//package af.aalpr.lang.security;
//
//
//import af.aalpr.data.model.User;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.io.Serializable;
//import java.util.Date;
//import java.util.function.Function;
//
//import static af.aalpr.lang.ParamsConfig.ACCESS_TOKEN_VALDITION_TIME;
//import static af.aalpr.lang.ParamsConfig.SIGNING_KEY;
//import static  af.aalpr.lang.ParamsConfig.ACCESS_TOKEN_VALIDITY_SECONDS;
//
//@Component
//public class JwtTokenUtil implements Serializable {
//
//    public String getUsernameFromToken(String token) {
//        String[] parts = getClaimFromToken(token, Claims::getSubject).split(",");
//        return parts[0];
//    }
//
//    public Date getExpirationDateFromToken(String token) {
//        return getClaimFromToken(token, Claims::getExpiration);
//    }
//
//    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = getAllClaimsFromToken(token);
//        return claimsResolver.apply(claims);
//    }
//
//    private Claims getAllClaimsFromToken(String token) {
//        return Jwts.parser()
//                .setSigningKey(SIGNING_KEY)
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    private Boolean isTokenExpired(String token) {
//        final Date expiration = getExpirationDateFromToken(token);
//        return expiration.before(new Date());
//    }
//
//    public String generateToken(User user) {
//        return doGenerateToken(user);
//    }
//
//    private String doGenerateToken(User user) {
//        String subject = user.getUsername();
//        Claims claims = Jwts.claims().setSubject(subject);
//
//        // claims.put("currentEnv", customUser.getCurrentEnv());
//        // claims.put("currentLang", customUser.getCurrentLang());
//        // claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setIssuer("http://asims.gov.af")
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000))
//                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
//                .compact();
//    }
//
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = getUsernameFromToken(token);
//        return (
//                username.equals(userDetails.getUsername())
//                        && !isTokenExpired(token));
//    }
//
//}