package com.murat.configServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}
	//tüm mikroservislerin konfigürasyon ayarları (portlar, database ayarları,
	// eureka bağlantı adresleri gibi) burada merkezi olarak tutulur.
}
