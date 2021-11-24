package org.generation.blogPessoal.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
		.select()
		.apis(RequestHandlerSelectors
		.basePackage("org.generation.blogPessoal.controller"))
		.paths(PathSelectors.any())
		.build()
		.apiInfo(metadata())
		.useDefaultResponseMessages(false);
	}

	public static ApiInfo metadata() {

		return new ApiInfoBuilder()
			.title("API - Blog Pessoal")
			.description("Projeto API Spring - Blog Pessoal")
			.version("1.0.0")
			.license("Apache License Version 2.0")
			.licenseUrl("https://github.com/BartRamandu")
			.contact(contact())
			.build();
	}

	private static Contact contact() {

		return new Contact("Bartolomeu Neto", 
			"https://github.com/BartRamandu", 
			"bartmelo_93@hotmail.com");

	}
}