package com.murat.lessonService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LessonServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LessonServiceApplication.class, args);
	}
	// bir servis (örneğin lessonService), studentService'e istek yapacağı zaman,
	// doğrudan IP’ye değil, Eureka’ya sorar ve öğrenci servisin mevcut IP ve portunu alır.
	//Böylece dynamik, esnek, otomatik servis keşfi sağlanır.
}
