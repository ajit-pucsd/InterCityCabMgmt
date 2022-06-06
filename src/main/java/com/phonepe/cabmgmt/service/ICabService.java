package com.phonepe.cabmgmt.service;

import java.util.List;

import com.phonepe.cabmgmt.exception.OperationNotAllwoedException;
import com.phonepe.cabmgmt.model.Cab;
import com.phonepe.cabmgmt.request.CabRequest;
import com.phonepe.cabmgmt.request.CabStateChangeRequest;
import com.phonepe.cabmgmt.request.SnapshotRequest;

public interface ICabService {

	Cab registerCab(CabRequest cab);
	Cab getById(int id);
	List<Cab> getAvailableCabsByCityId(int cityId);
	List<Cab> getAllCabs();
	Cab update(Integer id, CabRequest cabRequest);
	Cab getByNumber(String number);
	Cab changeState(CabStateChangeRequest request) throws OperationNotAllwoedException;
	List<Cab> updateCabData(SnapshotRequest request) throws OperationNotAllwoedException;
}
