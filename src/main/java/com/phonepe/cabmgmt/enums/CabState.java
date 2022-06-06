package com.phonepe.cabmgmt.enums;

import com.phonepe.cabmgmt.exception.OperationNotAllwoedException;

public enum CabState {
	
	IDLE {
		@Override
		public Boolean isNextStateAllowed(CabState nextState) {
			return true;
			//All state transitions are allowed from IDLE state
		}
	},
	ON_TRIP {
		@Override
		public Boolean isNextStateAllowed(CabState nextState) throws OperationNotAllwoedException {
			if (nextState == OFFLINE) {
				throw new OperationNotAllwoedException("Changing state from ON_TRIP to OFFLIEN is NOT ALLOWED");
			}
			return true;
		}
	},
	OFFLINE {
		@Override
		public Boolean isNextStateAllowed(CabState nextState) throws OperationNotAllwoedException {
			if (nextState == ON_TRIP) {
				throw new OperationNotAllwoedException("Changing state from OFFLINE to ON_TRIP is NOT ALLOWED");
			}
			return true;
		}
	};
	
	public abstract Boolean isNextStateAllowed(CabState nextState) throws OperationNotAllwoedException;
}
