package com.jhonatanborges.picpaydesafiobackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

@EnableJdbcAuditing
@SpringBootApplication
public class DesafioPicPayApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioPicPayApplication.class, args);
	}

}
