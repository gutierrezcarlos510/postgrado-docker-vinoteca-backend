package com.casavieja;

import com.casavieja.platform.auth.JWTAuthenticationFilter;
import com.casavieja.platform.auth.JWTAuthorizationFilter;
import com.casavieja.platform.auth.JWTService;
import com.casavieja.platform.config.CustomClientErrorHandler;
import com.casavieja.platform.config.CustomClientHttpRequestInterceptor;
import com.casavieja.platform.services.UserRolSpringSecurityS;
import lombok.RequiredArgsConstructor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRolSpringSecurityS userDetailService;
    private final JWTService jwtService;
    private final CloseableHttpClient httpClient;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests().anyRequest().permitAll();
        // Enable CORS and disable CSRF
        http = http.cors().and().csrf().disable();

        // Set session management to stateless
        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        // Set unauthorized requests exception handler
        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            ex.getStackTrace();
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
                        }
                )
                .and();

        // Set permissions on endpoints
        http.authorizeRequests()
                // Our public endpoints
                .antMatchers("/", "/css/**", "/js/**", "/img/**", "/icon/**", "/fonts/**").permitAll()
                .antMatchers("/producto/**","/Reportes/**","/trabajador/**","/cliente/**").permitAll()
                .antMatchers("/lugar/**","/systemUser/findByEmail","/systemUser/findByUsername").permitAll()
                .antMatchers("/systemUser/enviarCodigoVerificacion","/systemUser/resetPasswordCelular").permitAll()
                .antMatchers("/trabajador/registroPublico","/cliente/registroPublico").permitAll()
                .antMatchers("/refreshToken","/signout").permitAll()
                .anyRequest().authenticated()
                .and().addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtService))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtService));
//	List<DataSecurityMatcher> matcherList = rolS.listActiveSecurity();
//				if(UtilValidation.exist(matcherList)) {
//			for (DataSecurityMatcher data : matcherList) {
//				http.authorizeRequests().antMatchers(data.getNameTask()).hasAnyRole(data.getNameRole());
//			}
//		}else {
//			logger.info("No existe url controladas por usuarios");
//		}
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
//    @Autowired
//    public SpringSecurityConfig(CloseableHttpClient httpClient) {
//        this.httpClient = httpClient;
//    }
    @Bean
    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(httpClient);
        return clientHttpRequestFactory;
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .requestFactory(this::clientHttpRequestFactory)
                .errorHandler(new CustomClientErrorHandler())
                .interceptors(new CustomClientHttpRequestInterceptor())
                .build();
    }
}
