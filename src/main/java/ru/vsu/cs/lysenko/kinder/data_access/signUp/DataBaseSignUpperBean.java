package ru.vsu.cs.lysenko.kinder.data_access.signUp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataBaseSignUpperBean {

    @Bean
    DataBaseSignUpper getDataBaseSignUpperBean() {
        return new DataBaseSignUpper();
    }

}
