package com.example;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityController {
	private final CityRepository cityRepository;

	public CityController(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}

	@GetMapping("/cities")
	List<City> getCities() {
		return cityRepository.findAll();
	}

	@PostMapping("/cities")
	City postCities(@RequestBody City city) {
		return cityRepository.save(city);
	}
}