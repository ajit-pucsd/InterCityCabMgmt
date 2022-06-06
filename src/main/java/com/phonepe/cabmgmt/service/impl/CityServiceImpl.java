package com.phonepe.cabmgmt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phonepe.cabmgmt.dao.CityDao;
import com.phonepe.cabmgmt.model.City;
import com.phonepe.cabmgmt.request.CityRequest;
import com.phonepe.cabmgmt.service.ICityService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CityServiceImpl implements ICityService {

	@Autowired
	private CityDao cityDao;
	
	@Override
	public City getById(int id) {
		return cityDao.getById(id);
	}

	@Override
	public City addCity(CityRequest cityRequest) {
		if(cityDao.getCityByName(cityRequest.getName()) != null) {
			log.error("City exist with city name {}",cityRequest.getName());
			return cityDao.getCityByName(cityRequest.getName());
		}
			
		City city = new City(null, cityRequest.getName());
		cityDao.addCity(city);
		return city;
	}

	@Override
	public List<City> getAllCities() {
		return cityDao.getAllCities();
	}

}
