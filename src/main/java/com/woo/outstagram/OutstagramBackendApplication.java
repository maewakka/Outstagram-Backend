package com.woo.outstagram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.lang.reflect.Array;
import java.util.Arrays;

@SpringBootApplication
@EnableJpaAuditing
public class OutstagramBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(OutstagramBackendApplication.class, args);
	}


}
