package com.quanlytrungtamdayhoc.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer{
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		Path avatarUploadDir = Paths.get("./src/main/resources/static/images");
		String avatarUploadPath = avatarUploadDir.toFile().getAbsolutePath();
		
		registry.addResourceHandler("/src/main/resources/static/images/**").addResourceLocations("file:/" + avatarUploadPath + "/");
	}
}
