package com.phonepe.cabmgmt.model;

import com.phonepe.cabmgmt.enums.CabState;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cab {

	private Integer id;
	private String number;
	private CabState currentState;
	private City currentCity;
	//TODO cab to city mapping
}
