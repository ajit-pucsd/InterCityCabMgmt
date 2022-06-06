package com.phonepe.cabmgmt.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SnapshotRequest {

	private List<Snapshot> snapshots;
}
