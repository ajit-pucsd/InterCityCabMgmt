package com.phonepe.cabmgmt.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.phonepe.cabmgmt.enums.CabState;
import com.phonepe.cabmgmt.model.Cab;

@Repository
public class CabDao {

	private static final AtomicInteger id = new AtomicInteger(0);
	private static Map<Integer,Cab> cabs = new ConcurrentHashMap<>();
	private static Map<String,Integer> numberToIds = new ConcurrentHashMap<String, Integer>();
	
	public Cab getById(Integer id) {
		return cabs.getOrDefault(id, null);
	}
	
	public Cab addCab(Cab cab) {
		int currentId = id.incrementAndGet();
		cab.setId(currentId);
		cabs.put(currentId, cab);
		numberToIds.put(cab.getNumber(), currentId);
		return cab;
	}
	
	public Cab update(Cab cab)
	{
		cabs.put(cab.getId(), cab);
		return cab;
	}
	
	public Cab getByNumber(String number)
	{
		Integer cabId = numberToIds.getOrDefault(number, null);
		if(cabId == null) {
			return null;
		}
		
		return cabs.getOrDefault(cabId,null);
	}
	
	public List<Cab> getAllAvailableCabsByCityId(int cityId) {
		return cabs.values().stream()
		.filter(cab -> cab.getCurrentCity().getId() == cityId && cab.getCurrentState() == CabState.IDLE)
		.collect(Collectors.toList());
	}
	
	public List<Cab> getAllCabs()
	{
		return new ArrayList<>(cabs.values()) ;
	}
}
