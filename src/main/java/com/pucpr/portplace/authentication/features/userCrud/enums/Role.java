package com.pucpr.portplace.authentication.features.userCrud.enums;

import java.util.Arrays;
import java.util.List;

public enum Role {
    PMO(Arrays.asList(
            Permission.MANAGE_PORTFOLIO,
            Permission.VIEW_PORTFOLIO,
            Permission.VIEW_PROJECTS,
            Permission.MANAGE_STRATEGY,
            Permission.MANAGE_SCENARIOS
            )),
    PMO_ADM(Arrays.asList(
            Permission.MANAGE_USERS,
            Permission.MANAGE_PORTFOLIO,
            Permission.VIEW_PORTFOLIO,
            Permission.VIEW_PROJECTS,
            Permission.MANAGE_STRATEGY,
            Permission.MANAGE_SCENARIOS
            )),
    PROJECT_MANAGER(Arrays.asList(
            Permission.MANAGE_PROJECTS,
            Permission.VIEW_PROJECTS,
            Permission.VIEW_PORTFOLIO
            )),
    READER(Arrays.asList(
            Permission.VIEW_PROJECTS,
            Permission.VIEW_PORTFOLIO
            ));


    private List<Permission> permissionList;

    Role(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }


    public List<Permission> getPermissionsList() {
        return permissionList;
    }

    public void setPermissionsList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }
}