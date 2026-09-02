package dev.jorge.projects.gossipuerj.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI gossipUERJAPI() {
        return new OpenAPI()
                .info(new Info().title("Gossip UERJ API")
                        .description("This is the REST API for Gossip UERJ")
                        .version("1.0")
                        .license(new License().name("Apache 2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("You can refer to the Gossip UERJ Wiki Document")
                        .url("https://gossip-uerj-dummy-url.com/docs"));
    }
}