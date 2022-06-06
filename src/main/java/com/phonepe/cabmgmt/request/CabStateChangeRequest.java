package com.phonepe.cabmgmt.request;

import com.phonepe.cabmgmt.enums.CabState;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CabStateChangeRequest {
	private CabState currentState;
	private CabState nextState;
	private Integer cabId;
}
