package com.phonepe.cabmgmt.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phonepe.cabmgmt.dao.CabHistoryDao;
import com.phonepe.cabmgmt.enums.CabState;
import com.phonepe.cabmgmt.model.CabHistory;
import com.phonepe.cabmgmt.service.ICabHistoryService;

@Service
public class CabHistoryServiceImpl implements ICabHistoryService {

	@Autowired
	private CabHistoryDao cabHistoryDao;
	
	@Override
	public void addHistory(CabHistory cabHistory) {
		cabHistoryDao.addCabHistory(cabHistory);
	}

	/*
	 * GetLatestHistoryForCabs return ListOf CabHistory sorted by date in desc order
	 * whose currentState is idle
	 */
	
	@Override
	public List<CabHistory> getLatestHistoryForCabs(List<Integer> cabIds) {
		
		List<CabHistory> cabsHistory = cabHistoryDao.getLatestHistoryByCabIds(cabIds).stream()
				.filter( cabHisory -> CabState.IDLE.equals(cabHisory.getCurrentState()))
				.collect(Collectors.toList());
		Collections.sort(cabsHistory, new Comparator<CabHistory>() {
			@Override
			public int compare(CabHistory c1,CabHistory c2) {
				return c1.getCreationTime().compareTo(c2.getCreationTime());
			}
		});
		return cabsHistory;
	}

	@Override
	public List<CabHistory> getCabHistory(Integer cabId) {
		return cabHistoryDao.getAllHistoryByCabId(cabId);
	}

}
