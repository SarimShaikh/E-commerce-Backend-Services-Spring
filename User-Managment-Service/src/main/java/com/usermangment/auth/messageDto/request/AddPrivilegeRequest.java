package com.usermangment.auth.messageDto.request;

import com.usermangment.auth.entities.RoleName;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AddPrivilegeRequest {

    @NotNull
    private RoleName roleName;

    @NotNull
    private List<String> privileges;

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        RoleName roleEnum=RoleName.valueOf(roleName);
        this.roleName = roleEnum;
    }

    public List<String> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<String> privileges) {
        this.privileges = privileges;
    }
}
