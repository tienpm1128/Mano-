package com.viettel.vocs.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonPropertyOrder({ "ns_desciptors", "vnf_desciptions", "ns_instances", "vnf_instances" })
public class ValueOverview {

	@JsonProperty("ns_desciptors")
	private int nsDescriptors;

	@JsonProperty("vnf_desciptions")
	private int vnfDesciptions;

	@JsonProperty("ns_instances")
	private int nsInstances;

	@JsonProperty("vnf_instances")
	private int vnfInstances;

}
