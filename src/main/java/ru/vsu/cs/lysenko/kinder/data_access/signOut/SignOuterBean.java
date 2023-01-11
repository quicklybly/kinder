package ru.vsu.cs.lysenko.kinder.data_access.signOut;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SignOuterBean {
    @Bean
    SignOuter getSignOuterBean() {
        return new SignOuter();
    }
}
