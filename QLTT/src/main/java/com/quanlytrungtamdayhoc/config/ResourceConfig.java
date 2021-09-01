package com.quanlytrungtamdayhoc.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class ResourceConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
//		registry.addResourceHandler("/resources/templates/**");
//		registry.addResourceHandler("/resources/static/**");

		Path avatarUploadDir = Paths.get("./src/main/resources/static/images");
		String avatarUploadPath = avatarUploadDir.toFile().getAbsolutePath();

		registry.addResourceHandler("/src/main/resources/static/images/**")
				.addResourceLocations("file:/" + avatarUploadPath + "/");
	}

}