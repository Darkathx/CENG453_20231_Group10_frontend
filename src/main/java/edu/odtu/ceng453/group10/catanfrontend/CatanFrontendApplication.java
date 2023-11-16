package edu.odtu.ceng453.group10.catanfrontend;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CatanFrontendApplication {

	public static void main(String[] args) {
		Application.launch(CatanGameApplication.class, args);
	}

}
