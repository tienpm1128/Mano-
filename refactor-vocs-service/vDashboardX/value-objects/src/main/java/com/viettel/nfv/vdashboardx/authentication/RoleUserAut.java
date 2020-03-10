package com.viettel.nfv.vdashboardx.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleUserAut {

	private String roleType;

	private String authority;

}
