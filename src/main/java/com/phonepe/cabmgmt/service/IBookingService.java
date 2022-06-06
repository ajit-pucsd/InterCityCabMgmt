package com.phonepe.cabmgmt.service;

import com.phonepe.cabmgmt.exception.OperationNotAllwoedException;
import com.phonepe.cabmgmt.model.Cab;
import com.phonepe.cabmgmt.request.BookingRequest;

public interface IBookingService {
	
	Cab book(BookingRequest request) throws OperationNotAllwoedException;

}
