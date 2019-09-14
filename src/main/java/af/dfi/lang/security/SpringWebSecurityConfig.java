package af.dfi.lang.security;//package af.aalpr.lang.security;
//
//import af.aalpr.service.service.CustomUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
////import org.springframework.core.annotation.Order;
//
//
//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableWebSecurity
//public class SpringWebSecurityConfig extends WebSecurityConfigurerAdapter {
//
////    @Override
////    public void configure(WebSecurity web) throws Exception {
////        web.ignoring().antMatchers("/resources/**");
////    }
////
////    @Configuration
////    @Order(1)
////    public static class DigestAuthSecurityConfiguration extends WebSecurityConfigurerAdapter {
////
////        @Autowired
////        private CustomDigestUserService customDigestUserService;
////
////        @Override
////        protected void configure(HttpSecurity http) throws Exception {
////            http.cors().and().csrf().disable();
////            http.antMatcher("/api/**")
////                    .authorizeRequests().antMatchers("/api/**")
////                    // .anyRequest()
////                    .fullyAuthenticated()
////                    .and()
////                    //            .anonymous().disable()
////                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////                    .and()
////                    .addFilter(digestAuthFilter())
////                    //        	.addFilterAfter(digestAuthFilter(), BasicAuthenticationFilter.class)
////                    .exceptionHandling()
////                    .authenticationEntryPoint(digestEntryPoint());
////        }
////
////        DigestAuthenticationFilter digestAuthFilter() throws Exception {
////            DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
////
////            digestAuthenticationFilter.setUserDetailsService(customDigestUserService);
////            digestAuthenticationFilter.setAuthenticationEntryPoint(digestEntryPoint());
////            digestAuthenticationFilter.setPasswordAlreadyEncoded(false);
////            digestAuthenticationFilter.setCreateAuthenticatedToken(true);
////            return digestAuthenticationFilter;
////        }
////
////        DigestAuthenticationEntryPoint digestEntryPoint() {
////            DigestAuthenticationEntryPoint bauth = new DigestAuthenticationEntryPoint();
////            bauth.setRealmName("Digest ASIMS");
////            bauth.setKey("MySecureKey");
////            return bauth;
////        }
////
////        // @Override
////        // public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
////        //     authenticationManagerBuilder.userDetailsService(customDigestUserService);
////        // }
////
////    }
//
////    @Configuration
////    @Order(2)
////    public static class DigestAuthSecurityConfigurationODKX extends WebSecurityConfigurerAdapter {
////
////        @Autowired
////        private CustomDigestUserService customDigestUserService;
////
////        @Override
////        protected void configure(HttpSecurity http) throws Exception {
////            http.cors().and().csrf().disable();
////            http.antMatcher("/api/**")
////                    .authorizeRequests().antMatchers("/api/**")
////                    .fullyAuthenticated()
////                    .and()
////                    //            .anonymous().disable()
////                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////                    .and()
////                    .addFilter(digestAuthFilterODKX())
////                    //        	.addFilterAfter(digestAuthFilter(), BasicAuthenticationFilter.class)
////                    .exceptionHandling()
////                    .authenticationEntryPoint(digestEntryPointODKX());
////        }
////
////        DigestAuthenticationFilter digestAuthFilterODKX() throws Exception {
////            DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
////
////            digestAuthenticationFilter.setUserDetailsService(customDigestUserService);
////            digestAuthenticationFilter.setAuthenticationEntryPoint(digestEntryPointODKX());
////            digestAuthenticationFilter.setPasswordAlreadyEncoded(false);
////            digestAuthenticationFilter.setCreateAuthenticatedToken(true);
////            return digestAuthenticationFilter;
////        }
////
////        DigestAuthenticationEntryPoint digestEntryPointODKX() {
////            DigestAuthenticationEntryPoint bauth = new DigestAuthenticationEntryPoint();
////            bauth.setRealmName("Digest ASIMS");
////            bauth.setKey("MySecureKey");
////            return bauth;
////        }
////
////        // @Override
////        // public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
////        //     authenticationManagerBuilder.userDetailsService(customDigestUserService);
////        // }
////
////    }
//
////    @Configuration
//////    @Order(3)
////    @Order(1)
////    public static class JwtAuthSecurityConfiguration extends WebSecurityConfigurerAdapter {
////        @Autowired
////        private CustomUserService customUserService;
////
////        @Autowired
////        private JwtAuthenticationEntryPoint unauthorizedHandler;
////
////        private static final String[] AUTH_WHITE_LIST = {
////                //     "/",
////                //     "/public/**",
////                //     "/login",
////                //     "/**/*.png",
////                //     "/**/*.gif",
////                //     "/**/*.svg",
////                //     "/**/*.jpg",
////                //     "/**/*.html",
////                //     "/**/*.css",
////                //     "/**/*.js"
////                "/api/**",
////                "/authenticate",
////        };
////
////        @Override
////        protected void configure(HttpSecurity http) throws Exception {
////            // Add our custom JWT security filter
////            http.addFilterBefore(jwtAuthenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
////
////            http.cors().and().csrf().disable();
////            http.antMatcher("/api/**")
////                    .authorizeRequests()
////                    .antMatchers(AUTH_WHITE_LIST).permitAll()
////                    .anyRequest().authenticated()
////                    .and()
////                    .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
////                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
////            // .and().authorizeRequests().antMatchers("/").permitAll().anyRequest()
////            // .authenticated();
////        }
////
////        @Autowired
////        public JwtAuthenticationFilter jwtAuthenticationTokenFilterBean() throws Exception {
////            return new JwtAuthenticationFilter();
////        }
////
////        @Override
////        public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
////            authenticationManagerBuilder.userDetailsService(customUserService).passwordEncoder(jwtPasswordEncoder());
////        }
////
////        public BCryptPasswordEncoder jwtPasswordEncoder() {
////            return new BCryptPasswordEncoder();
////        }
////
////
////        @Override
////        @Bean
////        public AuthenticationManager authenticationManagerBean() throws Exception {
////            return super.authenticationManagerBean();
////        }
////
////        // @Autowired
////        // public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
////        //     // configure AuthenticationManager so that it knows from where to load
////        //     // user for matching credentials
////        //     // Use BCryptPasswordEncoder
////        //     auth.userDetailsService(customUserService).passwordEncoder(jwtPasswordEncoder());
////        // }
////    }
//
//    @Configuration
//    @Order(1)
//    public static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
//        @Autowired
//        private CustomUserService customUserService;
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http.cors().and().csrf().disable();
//            http.authorizeRequests()
//                    .antMatchers(
//                            "/api/**",
//                            "/oauth/**"
//                    ).permitAll()
//                    .antMatchers("/secure/**").authenticated()
//                    .anyRequest().authenticated()
//                    .and()
//                    .formLogin()
//                    .loginPage("/login")
//                    .permitAll()
//                    .and()
//                    .logout()
//                    .logoutUrl("/logout")
//                    .logoutSuccessUrl("/login");
//        }
//
//
//        public PasswordEncoder passwordEncoder() {
//            return new BCryptPasswordEncoder();
//        }
//
//        public DaoAuthenticationProvider authenticationProvider() {
//            DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
//            auth.setUserDetailsService(customUserService);
//            auth.setPasswordEncoder(passwordEncoder());
//            auth.setHideUserNotFoundExceptions(false);
//            return auth;
//        }
//
//        @Override
//        @Bean
//        public AuthenticationManager authenticationManagerBean() throws Exception {
//            return super.authenticationManagerBean();
//        }
//
//        @Override
//        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//            auth.authenticationProvider(authenticationProvider());
//        }
//    }
//
//}
