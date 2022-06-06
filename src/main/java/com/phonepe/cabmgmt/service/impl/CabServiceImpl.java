package com.phonepe.cabmgmt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phonepe.cabmgmt.dao.CabDao;
import com.phonepe.cabmgmt.enums.CabState;
import com.phonepe.cabmgmt.exception.CabNotAvailableException;
import com.phonepe.cabmgmt.exception.CityNotExistsException;
import com.phonepe.cabmgmt.exception.OperationNotAllwoedException;
import com.phonepe.cabmgmt.model.Cab;
import com.phonepe.cabmgmt.model.CabHistory;
import com.phonepe.cabmgmt.model.City;
import com.phonepe.cabmgmt.request.CabRequest;
import com.phonepe.cabmgmt.request.CabStateChangeRequest;
import com.phonepe.cabmgmt.request.Snapshot;
import com.phonepe.cabmgmt.request.SnapshotRequest;
import com.phonepe.cabmgmt.service.ICabHistoryService;
import com.phonepe.cabmgmt.service.ICabService;
import com.phonepe.cabmgmt.service.ICityService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CabServiceImpl implements ICabService {

	@Autowired
	private CabDao cabDao;
	
	@Autowired
	private ICityService cityService;
	
	@Autowired
	private ICabHistoryService cabHistoryService;
	
	private static final String CAB_NOT_EXISTS = "Cab doesn't exists with ";
	
	@Override
	public Cab registerCab(CabRequest cab) {
		City city = cityService.getById(cab.getCityId());
		if(city == null) {
			log.error("No servicer provider in city {}", cab.getCityId());
			throw new CityNotExistsException("Cab is not operating in provided city");
		}
		Cab extCab = getByNumber(cab.getNumber().toUpperCase());
		if(extCab != null )
			return extCab;
		Cab newCab =  new Cab(null,cab.getNumber().toUpperCase(),CabState.IDLE,city);
		cabDao.addCab(newCab);
		CabHistory cabHistory = new CabHistory(newCab.getId(), null, newCab.getCurrentState(), new Date());
		cabHistoryService.addHistory(cabHistory);
		if(log.isDebugEnabled()) {
			log.debug("Registered new cab details {}", newCab.toString());
		}
		return newCab;
	}

	@Override
	public Cab getById(int id) {
		Cab cab = cabDao.getById(id);
		if(cab == null) {
			log.error("Cab not found with id {}",id);
			throw new CabNotAvailableException(CAB_NOT_EXISTS+" with id: "+id);
		}
		return cab;
	}

	@Override
	public Cab update(Integer id, CabRequest cabRequest) {
		City city = cityService.getById(cabRequest.getCityId());
		if(city == null) {
			log.error("No servicer provider in city {}", cabRequest.getCityId());
			throw new CityNotExistsException("City does not exits!!"); 
		}
		Cab cab = getById(id);
		if (cab == null) {
			log.error("No cab found with id {}", id);
			throw new CabNotAvailableException(CAB_NOT_EXISTS+" with id: "+id);
		}
		cab.setCurrentCity(city);
		cabDao.update(cab);
		return cab;
	}

	@Override
	public Cab getByNumber(String number) {
		Cab cab = cabDao.getByNumber(number);
		if(cab == null) {
			return null;
		}
		return cab;
	}
	
	public Cab changeState(CabStateChangeRequest request) throws OperationNotAllwoedException {
		Cab cab = getById(request.getCabId());
		if (cab == null) {
			log.error("No cab found with id {}", request.getCabId());
			throw new CabNotAvailableException(CAB_NOT_EXISTS+" with id: "+request.getCabId());
		}
		if (cab.getCurrentState() != request.getCurrentState()) {
			throw new OperationNotAllwoedException("Cab current state doesn't match with request's current state");
		}
		if (request.getCurrentState().isNextStateAllowed(request.getNextState())) {
			cab.setCurrentState(request.getNextState());
			cabDao.update(cab);
			CabHistory cabHistory = new CabHistory(request.getCabId(), request.getCurrentState(), request.getNextState(), new Date());
			cabHistoryService.addHistory(cabHistory);
			return cab;
		}
		return null;
	}

	@Override
	public List<Cab> getAvailableCabsByCityId(int cityId) {
		City city = cityService.getById(cityId);
		if(city == null) {
			log.error("No servicer provide in city {}", cityId);
			throw new CityNotExistsException("City not found with Id: "+cityId); 
		}
		return cabDao.getAllAvailableCabsByCityId(cityId);
	}

	@Override
	public List<Cab> getAllCabs() {
		return cabDao.getAllCabs();
	}

	@Override
	public List<Cab> updateCabData(SnapshotRequest request) throws OperationNotAllwoedException {
		List<Cab> updatedCabData = new ArrayList<>();
		for(Snapshot snapshot:request.getSnapshots())
		{
			Cab cab = cabDao.getById(snapshot.getCabId());
			updatedCabData.add(changeState(new CabStateChangeRequest(cab.getCurrentState(), snapshot.getCabState(), cab.getId())));
		}
		
		return updatedCabData;
	}
	
}
