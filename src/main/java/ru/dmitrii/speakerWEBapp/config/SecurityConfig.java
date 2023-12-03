package ru.dmitrii.speakerWEBapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import ru.dmitrii.speakerWEBapp.security.AuthProvider;
import ru.dmitrii.speakerWEBapp.service.UserDetailsService_Impl;
import ru.dmitrii.speakerWEBapp.service.UserService;


@Configuration
@EnableWebSecurity
@ComponentScan("ru.dmitrii.speakerWEBapp")
public class SecurityConfig extends WebSecurityConfiguration {
    private final UserDetailsService_Impl userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService_Impl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }





    @Bean
    public PasswordEncoder encoder() {
        return NoOpPasswordEncoder.getInstance();
    }




    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(encoder());
        return  provider;
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // ?? how configure intead of adapter

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/menu/library", "/speaker/settings").authenticated()
                                .requestMatchers("/speaker/**", "/menu/search", "/menu/**", "/show/**").permitAll()
                                .anyRequest().authenticated())
                .formLogin(login ->
                        login.loginPage("/speaker/login")
                                .defaultSuccessUrl("/menu", true)
//                                .failureUrl("/speaker/login?error")
                                .loginProcessingUrl("/speaker/login_process"))
                .logout(logout ->
                        logout.permitAll()
                                .logoutUrl("/speaker/logout")
                                .logoutSuccessUrl("/speaker/login"));

        return http.build();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/source/**", "*/resources/**");
    }
}