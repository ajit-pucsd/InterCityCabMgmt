package com.phonepe.cabmgmt.service;

import java.util.List;

import com.phonepe.cabmgmt.model.City;
import com.phonepe.cabmgmt.request.CityRequest;

public interface ICityService {

	City getById(int id);
	City addCity(CityRequest cityRequst);
	List<City> getAllCities();
}
