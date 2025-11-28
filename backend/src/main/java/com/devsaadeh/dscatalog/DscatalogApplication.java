package com.devsaadeh.dscatalog;

import com.devsaadeh.dscatalog.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class  DscatalogApplication {

	@Autowired
	private S3Service service;

	public static void main(String[] args) {
		SpringApplication.run(DscatalogApplication.class, args);
	}


}
