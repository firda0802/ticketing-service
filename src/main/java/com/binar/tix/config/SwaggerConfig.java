package com.binar.tix.config;

import com.binar.tix.utility.Constant;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        ArrayList<Server> servers = new ArrayList<>();
        servers.add(new Server().url("https://tix-service-bej5.up.railway.app/ticketing-service").description("Staging Server"));
        servers.add(new Server().url("http:localhost:8080/ticketing-service").description("Local Server Server"));
        return new OpenAPI()
                .info(new Info().title("Ticketing Service").version("1.0.0"))
                .components(new Components()
                        .addSecuritySchemes(Constant.AUTH, new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name(Constant.AUTH)))
                .addSecurityItem(new SecurityRequirement().addList(Constant.AUTH)).servers(servers);
    }
}