package com.phonepe.cabmgmt.request;

import com.phonepe.cabmgmt.enums.CabState;
import com.phonepe.cabmgmt.model.City;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CityRequest {
	
	private String name;

}
