package com.phonepe.cabmgmt.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phonepe.cabmgmt.exception.CabNotAvailableException;
import com.phonepe.cabmgmt.exception.CityNotExistsException;
import com.phonepe.cabmgmt.exception.OperationNotAllwoedException;
import com.phonepe.cabmgmt.model.Cab;
import com.phonepe.cabmgmt.request.CabRequest;
import com.phonepe.cabmgmt.request.CabStateChangeRequest;
import com.phonepe.cabmgmt.request.SnapshotRequest;
import com.phonepe.cabmgmt.service.impl.CabServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("cab")
@Slf4j
public class CabController {
	
	@Autowired
	private CabServiceImpl cabService;
	
	@RequestMapping
	public ResponseEntity<List<Cab>> getAllCabs()
	{
		return new ResponseEntity<List<Cab>>(cabService.getAllCabs(),HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Object> registerCab(@RequestBody(required = true) CabRequest cabRequest)
	{
		if(cabRequest.getNumber() == null || cabRequest.getCityId() == 0)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		log.info("Registering new cab with number {}", cabRequest.getNumber());
		Cab cab = cabService.registerCab(cabRequest);
		log.info("Registered new cab {}", cabRequest.toString());
		return new ResponseEntity<>(cab, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updateCab(@PathVariable("id") Integer id, @RequestBody CabRequest cabRequest)
	{
		if(id == 0 || cabRequest.getNumber() == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		log.info("Update cab", cabRequest.toString());
		Cab cab = cabService.update(id, cabRequest);
		if(cab == null)
			throw new CityNotExistsException("Cab is not operating in provided city");
		log.info("Updated cab {}", cabRequest.toString());
		return new ResponseEntity<>(cab, HttpStatus.OK);
	}
	
	@PutMapping(value = "/{id}/changeState")
	public ResponseEntity<Object> changeState(@PathVariable("id") Integer id, @RequestBody CabStateChangeRequest cabRequest) throws OperationNotAllwoedException 
	{
		if(id == 0 || cabRequest.getNextState() == null || cabRequest.getCurrentState() == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		log.info("Update state to {} for cab {}", cabRequest.getNextState(), cabRequest.getCabId());
		Cab cab;
		cab = cabService.changeState(cabRequest);
		if(cab == null)
			throw new CabNotAvailableException("Cab not available with given data!!!");
		
		log.info("Updated state for cab {}", cabRequest.toString());
		return new ResponseEntity<>(cab, HttpStatus.OK);
	}
	
	@PostMapping(value = "/uploadSnapshot")
	public ResponseEntity<Object> uploadSnapshots(@RequestBody(required = true) SnapshotRequest request) throws OperationNotAllwoedException
	{
		if(request.getSnapshots() == null || request.getSnapshots().isEmpty())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		List<Cab> updatedCab = cabService.updateCabData(request);
		return new ResponseEntity<>(updatedCab,HttpStatus.OK);
	}
}
