package com.academy.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan({"com.academy.core.model"})
@EnableJpaRepositories({"com.academy.core.repository"})
public class CourseApplication {

	public static void main(String[] args) {

		SpringApplication.run(CourseApplication.class, args);
	}

}
