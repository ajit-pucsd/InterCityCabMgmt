package com.phonepe.cabmgmt.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phonepe.cabmgmt.enums.CabState;
import com.phonepe.cabmgmt.exception.CabNotAvailableException;
import com.phonepe.cabmgmt.exception.OperationNotAllwoedException;
import com.phonepe.cabmgmt.model.Cab;
import com.phonepe.cabmgmt.model.CabHistory;
import com.phonepe.cabmgmt.request.BookingRequest;
import com.phonepe.cabmgmt.request.CabStateChangeRequest;
import com.phonepe.cabmgmt.service.IBookingService;
import com.phonepe.cabmgmt.service.ICabHistoryService;
import com.phonepe.cabmgmt.service.ICabService;

@Service
public class BookingServiceImpl implements IBookingService {
	
	@Autowired
	private ICabService cabService;
	
	@Autowired
	private ICabHistoryService cabHistoryService;

	@Override
	public Cab book(BookingRequest request) throws OperationNotAllwoedException {
		
		List<Cab> availableCabs = cabService.getAvailableCabsByCityId(request.getSourceCityId());
		if(availableCabs.isEmpty()) {
			throw new CabNotAvailableException("Cab not available for booking in city: "+request.getSourceCityId());
		}
		
		if(availableCabs.size() == 1) {
			Cab cab = availableCabs.get(0);
			cabService.changeState(new CabStateChangeRequest(cab.getCurrentState(), CabState.ON_TRIP, cab.getId()));;
			return cabService.getById(cab.getId());
		}
		
		List<Integer> cabIds = availableCabs.stream().map( s -> s.getId()).collect(Collectors.toList());
		
		List<CabHistory> cabHistory = cabHistoryService.getLatestHistoryForCabs(cabIds);
		CabHistory leastIdleCabHistory = cabHistory.get(0);
		Cab cab = cabService.changeState(new CabStateChangeRequest(leastIdleCabHistory.getCurrentState(), CabState.ON_TRIP, leastIdleCabHistory.getCabId()));
		
		return cab;
	}

}
