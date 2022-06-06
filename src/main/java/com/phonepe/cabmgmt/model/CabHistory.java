package com.phonepe.cabmgmt.model;

import java.util.Date;

import com.phonepe.cabmgmt.enums.CabState;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CabHistory {
	
	private Integer cabId;
	private CabState pastState;
	private CabState currentState;
	private Date creationTime;

}
