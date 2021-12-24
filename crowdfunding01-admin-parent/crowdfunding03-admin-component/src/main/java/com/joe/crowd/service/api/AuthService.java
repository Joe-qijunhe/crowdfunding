package com.joe.crowd.service.api;

import com.joe.crowd.entity.Auth;

import java.util.List;
import java.util.Map;

public interface AuthService {

    List<Auth> getAll();

    List<Integer> getAssignAuthIdByRoleId(Integer roleId);

    void saveRoleAuthRelationship(Map<String, List<Integer>> map);

    List<String> getAssignedAuthNameByAdminId(Integer adminId);
}
