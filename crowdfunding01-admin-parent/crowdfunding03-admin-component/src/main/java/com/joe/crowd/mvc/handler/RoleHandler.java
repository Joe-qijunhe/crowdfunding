package com.joe.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.joe.crowd.entity.Role;
import com.joe.crowd.service.api.RoleService;
import com.joe.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RoleHandler {

    @Autowired
    private RoleService roleService;

    /**
     * 角色分页
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PreAuthorize("hasRole('minister')")
    @RequestMapping("/role/get/page/info.json")
    @ResponseBody
    public ResultEntity<PageInfo<Role>> getPageInfo(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize
    ) {
        // 调用service获取分页数据
        PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);
        return ResultEntity.successWithData(pageInfo);
    }

    @RequestMapping("/role/save.json")
    @ResponseBody
    public ResultEntity<String> saveRole(Role role) {
        roleService.saveRole(role);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/role/update.json")
    @ResponseBody
    public ResultEntity<String> updateRole(Role role) {
        roleService.updateRole(role);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/role/remove/by/role/id/array.json")
    @ResponseBody
    public ResultEntity<String> removeByRoleIdArray(@RequestBody List<Integer> roleIdList) {
        roleService.removeRole(roleIdList);
        return ResultEntity.successWithoutData();
    }
}
