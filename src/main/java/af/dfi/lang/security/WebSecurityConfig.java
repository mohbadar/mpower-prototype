//package af.dfi.lang.security;
//
//import af.dfi.lang.ParamsConfig;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.provider.ClientDetailsService;
//import org.springframework.security.oauth2.provider.approval.ApprovalStore;
//import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
//import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
//import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
///**
// *
// * @author Dell
// */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private UserDetailsService customUserDetailsService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private LoggingAccessDeniedHandler accessDeniedHandler;
//
//    @Autowired
//    private ClientDetailsService clientDetailsService;
//
//    /**
//     * Configure user and password
//     *
//     * @param auth
//     * @throws Exception
//     */
//    @Autowired
//    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(customUserDetailsService)
//                .passwordEncoder(passwordEncoder);
//    }
//
//    /**
//     * Configure HttpSecurity for http call
//     *
//     * @param http
//     * @throws Exception
//     */
//    @Override
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public void configure(HttpSecurity http) throws Exception {
//
//        //@formatter::off
//        http
//                                .sessionManagement()
//                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                                .and()
//                                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers(
//                        "/authenticate",
//                        "/api/**",
//                        "/oauth/**",
//                        ParamsConfig.TOKEN_GENERATION_URL
//                ).permitAll()
//                .antMatchers("/secure/**").authenticated()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .and()
//                .logout()
//                .invalidateHttpSession(true)
//                .clearAuthentication(true)
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/login?logout")
//                .permitAll()
//                //                .and()
//                //                .httpBasic()
//                //                .realmName("CRM_REALM")
//                .and()
//                .exceptionHandling()
//                .accessDeniedHandler(accessDeniedHandler);
//        //@formatter::on
//
////        http
////                .sessionManagement()
////                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////                .and()
////                .csrf().disable()
////                .authorizeRequests()
////                .antMatchers("/be/**").permitAll()
////                .antMatchers("/fe/**").permitAll()
////                .antMatchers("/signup").permitAll()
////                .antMatchers("/oauth/token").permitAll()
////                .antMatchers("/api/**").authenticated()
//////                .anyRequest().authenticated()
////                .and()
////                .httpBasic()
////                .realmName("CRM_REALM");
//    }
//
//    /**
//     * Create AuthenticationManager Bean
//     *
//     *
//     * @return
//     * @throws Exception
//     */
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Bean
//    public TokenStore tokenStore() {
//        return new JwtTokenStore(jwtTokenEnhancer());
//    }
//
//    @Bean
//    @Autowired
//    public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore) {
//        TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
//        handler.setTokenStore(tokenStore);
//        handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
//        handler.setClientDetailsService(clientDetailsService);
//        return handler;
//    }
//
//    @Bean
//    @Autowired
//    public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
//        TokenApprovalStore store = new TokenApprovalStore();
//        store.setTokenStore(tokenStore);
//        return store;
//    }
//
//    @Bean
//    protected JwtAccessTokenConverter jwtTokenEnhancer() {
//        /*
//	    KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "mySecretKey".toCharArray());
//	    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//	    converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt"));
//         */
//        //-- for the simple demo purpose, used the secret for signing.
//        //-- for production, it is recommended to use public/private key pair
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setSigningKey("Demo-Key-1");
//
//        return converter;
//    }
//
////    @Bean
////    public FilterRegistrationBean corsFilter() {
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        CorsConfiguration config = new CorsConfiguration();
////        config.setAllowCredentials(true);
////        config.addAllowedOrigin("*");
////        config.addAllowedHeader("*");
////        config.addAllowedMethod("*");
////        source.registerCorsConfiguration("/**", config);
////        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
////        bean.setOrder(0);
////        return bean;
////    }
//}
