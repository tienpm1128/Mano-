package com.viettel.nfv.vdashboardx.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class AuthRequest {

    private String username;

    private String password;

}
