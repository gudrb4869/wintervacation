package com.ssafy.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

//	Swagger-UI 2.x 확인
//	http://localhost:8080/{your-app-root}/swagger-ui.html
//	Swagger-UI 3.x 확인
//	http://localhost:8080/{your-app-root}/swagger-ui/index.html

	private String version = "V1";
	private String title = "SSAFY Board API " + version;
		
    @Bean
    public Docket qnaBoardApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("QnA Board API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ssafy.qna.controller"))
                .paths(regex("/qna-board.*")).build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
    }
    
    @Bean
    public Docket planApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Plan API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ssafy.plan.controller"))
                .paths(regex("/plan.*")).build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
    }
    
    @Bean
    public Docket favoriteApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Favorite API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ssafy.favorite.controller"))
                .paths(regex("/favorite.*")).build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
    }

    @Bean
    public Docket qnaMemoApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("QnA Memo API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ssafy.qna.controller"))
                .paths(regex("/qna-memo.*")).build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
    }
    
    @Bean
    public Docket BoardApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Board API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ssafy.board.controller"))
                .paths(regex("/board-api.*")).build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
    }
    
    @Bean
    public Docket MapApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Map API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ssafy.map.controller"))
                .paths(regex("/map.*")).build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
    }
    
    @Bean
    public Docket MemberApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Member API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ssafy.member.controller"))
                .paths(regex("/member.*")).build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
    }
    
    @Bean
    public Docket MemoApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Memo API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ssafy.board.controller"))
                .paths(regex("/board-memo.*")).build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
    }
	
	private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
//      consumes.add("application/xml;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        return produces;
    }

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(title)
				.description("<h3>SSAFY API Reference for Developers</h3>Swagger를 이용한 Board API<br><img src=\"/assets/img/ssafy_logo.png\" width=\"150\">") 
				.contact(new Contact("SSAFY", "https://edu.ssafy.com", "ssafy@ssafy.com"))
				.license("SSAFY License")
				.licenseUrl("https://www.ssafy.com/ksp/jsp/swp/etc/swpPrivacy.jsp")
				.version("1.0").build();
	}
	
}
