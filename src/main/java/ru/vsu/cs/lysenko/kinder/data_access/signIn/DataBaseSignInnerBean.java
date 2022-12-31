package ru.vsu.cs.lysenko.kinder.data_access.signIn;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataBaseSignInnerBean {
    @Bean
    DataBaseSignInner getDataBaseSignInnerBean() {
        return new DataBaseSignInner();
    }
}
