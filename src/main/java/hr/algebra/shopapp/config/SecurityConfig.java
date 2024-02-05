package hr.algebra.shopapp.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> response.sendRedirect("/login")).and()
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/authentication/register/**").permitAll()
                                .requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers("/authentication/login").permitAll()
                                .requestMatchers("/authentication/login").permitAll()
                                .requestMatchers("/home").permitAll()
                                .requestMatchers("/authentication/users").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/item").permitAll()
                                .requestMatchers("/item/**").hasRole("ADMIN")
                                .requestMatchers("/category/**").hasRole("ADMIN")
                                .requestMatchers("/category").hasRole("ADMIN")
                                .requestMatchers("/cart").permitAll()
                                .requestMatchers("/cart/**").permitAll()
                                .requestMatchers("/payment/**").hasAnyRole("USER","ADMIN")
                                .requestMatchers("/payment").hasRole("USER")
                                .requestMatchers("/payment").hasRole("ADMIN")
                                .requestMatchers("/error").permitAll()
                                .requestMatchers("/afterLogin").permitAll()
                )    .formLogin(
                        form -> form
                                .loginPage("/authentication/login").defaultSuccessUrl("/home", true).usernameParameter("username")


                ).logout(
                        logout -> logout
                                .invalidateHttpSession(true)
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );


        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

}
