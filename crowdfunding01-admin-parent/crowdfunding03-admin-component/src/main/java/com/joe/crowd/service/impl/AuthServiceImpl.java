package com.joe.crowd.service.impl;

import com.joe.crowd.entity.Auth;
import com.joe.crowd.entity.AuthExample;
import com.joe.crowd.mapper.AuthMapper;
import com.joe.crowd.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Auth> getAll() {
        return authMapper.selectByExample(new AuthExample());
    }

    @Override
    public List<Integer> getAssignAuthIdByRoleId(Integer roleId) {
        return authMapper.selectAssignAuthIdByRoleId(roleId);
    }

    @Override
    public void saveRoleAuthRelationship(Map<String, List<Integer>> map) {
        // 1.获取roleId的值
        List<Integer> roleIdList = map.get("roleId");
        Integer roleId = roleIdList.get(0);

        // 2. 删除旧的关联数据
        authMapper.deleteOldRelationship(roleId);

        // 3.获取 authIdList
        List<Integer> authIdList = map.get("authIdArray");

        // 4.判断 authIdList是否有效
        if (authIdList != null && authIdList.size() > 0) {
            authMapper.insertNewRelationship(roleId, authIdList);
        }
    }

    @Override
    public List<String> getAssignedAuthNameByAdminId(Integer adminId) {
        return authMapper.selectAssignedAuthNameByAdminId(adminId);
    }
}
