package com.phonepe.cabmgmt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phonepe.cabmgmt.exception.CabNotAvailableException;
import com.phonepe.cabmgmt.exception.OperationNotAllwoedException;
import com.phonepe.cabmgmt.model.Cab;
import com.phonepe.cabmgmt.request.BookingRequest;
import com.phonepe.cabmgmt.service.IBookingService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("book")
@Slf4j
public class BookingController {

	@Autowired
	private IBookingService bookingService;
	
	@PostMapping
	public ResponseEntity<Object> book(@RequestBody BookingRequest request) throws OperationNotAllwoedException {
		
		if(request.getSourceCityId() == null || request.getDestinationCityId() == null) {
			log.error("Source city id or Dest city id cannot be null");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Cab cab = bookingService.book(request);
		if(cab == null)
			throw new CabNotAvailableException("Cab not available for booking in cityId: "+request.getSourceCityId());
		return new ResponseEntity<>(cab, HttpStatus.CREATED);
	}
	
}
