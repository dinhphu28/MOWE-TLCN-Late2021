package com.ndp.audiosn;

import com.ndp.audiosn.Configs.Property.FileStorageProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableConfigurationProperties({
	FileStorageProperties.class
})
public class AudiosnApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AudiosnApplication.class, args);
	}

}
