package com.phonepe.cabmgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phonepe.cabmgmt.model.CabHistory;
import com.phonepe.cabmgmt.service.ICabHistoryService;

@RestController
@RequestMapping("cab_history")
public class CabHistoryController {
	
	@Autowired
	public ICabHistoryService cabHistoryService;

	@GetMapping(value = "/{cabId}")
	public ResponseEntity<List<CabHistory>> getCabHistoryByCabId(@PathVariable Integer cabId)
	{
		List<CabHistory> cabHistory = cabHistoryService.getCabHistory(cabId);
		return new ResponseEntity<List<CabHistory>>(cabHistory,HttpStatus.OK);
	}
	
}
