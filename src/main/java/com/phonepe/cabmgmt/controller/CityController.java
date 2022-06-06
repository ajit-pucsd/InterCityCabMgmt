package com.phonepe.cabmgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.phonepe.cabmgmt.model.*;
import com.phonepe.cabmgmt.request.CityRequest;
import com.phonepe.cabmgmt.service.ICityService;

@RestController
@RequestMapping("city")
public class CityController {
	
	@Autowired
	private ICityService cityService;
	
	@GetMapping
	public ResponseEntity<List<City>> getAllAvailableCities() {
		return new ResponseEntity<List<City>>(cityService.getAllCities(),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<City> addCity(@RequestBody CityRequest cityRequest) {
		
		City city = cityService.addCity(cityRequest);
		
		return new ResponseEntity<City>(city, HttpStatus.CREATED);
		
	}
}

