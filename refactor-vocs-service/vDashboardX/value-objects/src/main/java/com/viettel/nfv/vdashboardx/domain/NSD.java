package com.viettel.nfv.vdashboardx.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NSD {

	private String id;

	private String nsdId;

	private String nsdName;

	private String nsdVersion;

	private String nsdDesigner;

	private String nsdInvariantId;

	private String vnfPkgIds;

	private String pnfdInfoIds;

	private String nestedNsdInfoIds;

	private String nsdOnboardingState;

	private String onboardingFailureDetails;

	private String nsdOperationalState;

	private String nsdUsageState;

	private String userDefinedData;

	private String links;
}
