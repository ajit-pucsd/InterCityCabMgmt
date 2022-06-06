package com.phonepe.cabmgmt.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingRequest {

	private Integer sourceCityId;
	private Integer destinationCityId;
	
}
