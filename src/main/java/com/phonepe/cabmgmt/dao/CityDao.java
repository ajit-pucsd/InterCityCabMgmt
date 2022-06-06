package com.phonepe.cabmgmt.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.phonepe.cabmgmt.model.City;

@Repository
public class CityDao {
	
	private static final AtomicInteger id = new AtomicInteger(0);
	private static Map<Integer,City> cities = new ConcurrentHashMap<>();
	
	public City getById(Integer id) {
		return cities.getOrDefault(id, null);
	}
	
	public City addCity(City city)
	{
		int currentId = id.incrementAndGet();
		city.setId(currentId);
		cities.put(currentId, city);
		return city;
	}
	
	public List<City> getAllCities()
	{
		return new ArrayList<>(cities.values());
	}
	
	public City getCityByName(String name)
	{
		List<City> city = cities.values().stream().filter(c -> c.getName().equals(name)).collect(Collectors.toList());
		if(city == null || city.isEmpty())
			return null;
		return city.get(0);
	}

}
