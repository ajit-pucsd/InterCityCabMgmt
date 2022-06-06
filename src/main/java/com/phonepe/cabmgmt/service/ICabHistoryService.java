package com.phonepe.cabmgmt.service;

import java.util.List;

import com.phonepe.cabmgmt.model.CabHistory;

public interface ICabHistoryService {
	
	void addHistory(CabHistory canHistory);
	List<CabHistory> getLatestHistoryForCabs(List<Integer> cabIds);
	List<CabHistory> getCabHistory(Integer cabId);

}
