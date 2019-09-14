
package af.dfi.lang.security;

//
//import org.springframework.boot.autoconfigure.security.oauth2.OAuth2AutoConfiguration;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
////import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//
///**
// *
// * @author Dell
// */
//@Configuration
//@EnableResourceServer
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//    public static final String RESOURCE_ID = "resource_id";
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) {
//        resources.resourceId(RESOURCE_ID).stateless(false);
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//                .headers()
//                .frameOptions()
//                .disable()
//                .and()
//                .authorizeRequests()
////                .antMatchers("/login", "/online/**").permitAll()
//                .antMatchers(ParamsConfig.SECURED_RESOURCE_URL).authenticated();
//    }
//
//}
