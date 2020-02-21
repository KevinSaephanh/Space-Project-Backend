package com.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@CrossOrigin
@RestController
@RequestMapping("/nasa")
public class NasaController {
	public static final String NASA_KEY = System.getenv("NASA_KEY");
	public static final String BASE_URL = "https://exoplanetarchive.ipac.caltech.edu/cgi-bin/nstedAPI/nph-nstedAPI?";

	RestTemplate restTemplate = new RestTemplate();
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/apod", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getPicOfTheDay() {
		final String apod_url = "https://api.nasa.gov/planetary/apod?api_key=";
		return restTemplate.getForObject(apod_url + NASA_KEY, String.class);
	}
}
