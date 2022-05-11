package com.claro.gestionrecursosweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GestionRecursosWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionRecursosWebApplication.class, args);
    }

	
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}
