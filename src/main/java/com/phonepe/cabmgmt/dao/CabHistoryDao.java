package com.phonepe.cabmgmt.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.phonepe.cabmgmt.model.CabHistory;

@Repository
public class CabHistoryDao {
	
	private static Map<Integer, List<CabHistory>> history = new HashMap<>();
	
	public void addCabHistory(CabHistory cabHistory) {
		List<CabHistory> existingHistory = history.getOrDefault(cabHistory.getCabId(), new ArrayList<>());
		existingHistory.add(cabHistory);
		history.put(cabHistory.getCabId(), existingHistory);
	}
	
	public List<CabHistory> getLatestHistoryByCabIds(List<Integer> cabIds)
	{
		List<CabHistory> cabsHistory = new ArrayList<>();
		for(Integer cabId: cabIds)
		{
			if(history.containsKey(cabId)) {
				{
					List<CabHistory> cabHistory = history.get(cabId);
					cabsHistory.add(cabHistory.get(cabHistory.size()-1));
				}
			}
		}
		return cabsHistory;
	}
	
	public List<CabHistory> getAllHistoryByCabId(Integer cabId)
	{
		return history.get(cabId);
	}

}
