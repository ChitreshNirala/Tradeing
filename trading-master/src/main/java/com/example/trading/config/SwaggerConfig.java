package com.example.trading.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
      new HashSet<>(Arrays.asList("application/json"));

  private ApiInfo apiEndPointsInfo() {
    return new ApiInfoBuilder().title("Spring Boot REST API")
        .description("Demo of  REST API")
        .contact(new Contact("Chitresh Nirala", "www.Chitresh.net", "Chitresh@gmail.com"))
        .license("Apache 2.0")
        .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
        .version("1.0.0")
        .build();
  }

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiEndPointsInfo())
        .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
        .produces(DEFAULT_PRODUCES_AND_CONSUMES)
        .select().paths(Predicates.not(PathSelectors.regex("/error.*")))
        .build();
  }
  
  
 
}