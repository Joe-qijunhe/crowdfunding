package com.joe.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.joe.crowd.entity.Admin;

import java.util.List;

public interface AdminService {
    public void saveAdmin(Admin admin);

    public List<Admin> getAll();

    public Admin getAdminByLoginAcct(String loginAcct, String userPswd);

    PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    void removeById(Integer adminId);

    Admin getAdminById(Integer adminId);

    void update(Admin admin);

    void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList);

    Admin getAdminByLoginAcct(String username);
}
