package com.phonepe.cabmgmt.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.phonepe.cabmgmt.dao.CabDao;
import com.phonepe.cabmgmt.dao.CabHistoryDao;
import com.phonepe.cabmgmt.dao.CityDao;
import com.phonepe.cabmgmt.enums.CabState;
import com.phonepe.cabmgmt.exception.CabNotAvailableException;
import com.phonepe.cabmgmt.exception.CityNotExistsException;
import com.phonepe.cabmgmt.exception.OperationNotAllwoedException;
import com.phonepe.cabmgmt.model.Cab;
import com.phonepe.cabmgmt.model.City;
import com.phonepe.cabmgmt.request.BookingRequest;
import com.phonepe.cabmgmt.request.CabRequest;

@RunWith(MockitoJUnitRunner.class)
public class CabServiceTest {
	
	@InjectMocks
	private CabServiceImpl cabService = new CabServiceImpl();
	
	
	@InjectMocks 
	private BookingServiceImpl bookingService = new BookingServiceImpl();
	 
	
	@Mock
	private CityServiceImpl cityService = new CityServiceImpl();
	
	@Mock
	private CityDao cityDao = new CityDao();
	
	@Mock
	private CabDao cabDao = new CabDao();
	
	@InjectMocks
	private CabHistoryServiceImpl cabHistoryService = new CabHistoryServiceImpl();
	
	@Mock
	private CabHistoryDao cabHistoryDao = new CabHistoryDao();
	
	
	@BeforeEach
	public void before()
	{
		ReflectionTestUtils.setField(cabService, "cityService", cityService);
		ReflectionTestUtils.setField(cabService, "cabHistoryService", cabHistoryService);
		ReflectionTestUtils.setField(cabService, "cabDao", cabDao);
		ReflectionTestUtils.setField(cityService, "cityDao", cityDao);
		ReflectionTestUtils.setField(cabHistoryService, "cabHistoryDao",cabHistoryDao);
		
		ReflectionTestUtils.setField(bookingService, "cabHistoryService",cabHistoryService);
		ReflectionTestUtils.setField(bookingService, "cabService",cabService);
	}
	 	
	@Test
	void testRegisterCab()
	{
		// #1 When wrong cab details given
		CabRequest reqeust = new CabRequest("CS12AB2344", 100);
		assertThrows(CityNotExistsException.class, () -> cabService.registerCab(reqeust));
		
		// #2 Verify added details
		City city = new City(1, "Mumbai");
		cityDao.addCity(city);
		Assert.assertEquals(cityDao.getCityByName("Mumbai").getName(), city.getName());
		
		//#3 Verify cab registration with wrong city id
		assertThrows(CityNotExistsException.class, () -> cabService.registerCab(new CabRequest("MH23CA234", 3)));
		
		//#4 book cab book with existing data
		Cab cab = cabService.registerCab(new CabRequest("mh234sr234",1));
		assertEquals("mh234sr234".toUpperCase(), cab.getNumber());
		
	}
	
	@Test
	void testBookCab() throws OperationNotAllwoedException
	{
		BookingRequest request = new BookingRequest(1, 1);
		assertThrows(CityNotExistsException.class , () -> bookingService.book(request));
		
		City city = new City(1, "Mumbai");
		cityDao.addCity(city);
		assertThrows(CabNotAvailableException.class , () -> bookingService.book(request));
		
		Cab cab = cabService.registerCab(new CabRequest("MH23CA234", 1));
		Cab bookedCab = bookingService.book(request);
		assertEquals(bookedCab.getCurrentState(), CabState.ON_TRIP);
		
		assertThrows(CabNotAvailableException.class, () -> bookingService.book(request));
	}
	

}
