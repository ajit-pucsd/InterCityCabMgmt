package com.phonepe.cabmgmt.request;

import com.phonepe.cabmgmt.enums.CabState;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Snapshot {
	private Integer cabId;
	private Integer cityId;
	private CabState cabState;
}
