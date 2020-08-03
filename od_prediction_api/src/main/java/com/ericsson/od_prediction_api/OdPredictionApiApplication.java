package com.ericsson.od_prediction_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import od_prediction_controller.*;
import od_prediction_model.*;
import od_prediction_service.*;
@SpringBootApplication
@ComponentScan(basePackages= {"od_prediction_controller"})

public class OdPredictionApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OdPredictionApiApplication.class, args);
	}

}
