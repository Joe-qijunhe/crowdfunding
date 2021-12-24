package com.joe.crowd.mvc.handler;

import com.joe.crowd.entity.Auth;
import com.joe.crowd.entity.Role;
import com.joe.crowd.service.api.AdminService;
import com.joe.crowd.service.api.AuthService;
import com.joe.crowd.service.api.RoleService;
import com.joe.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.transform.Result;
import java.util.List;
import java.util.Map;

@Controller
public class AssignHandler {

    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthService authService;

    /**
     * 获取所有已分配给这个admin的角色，和所有未分配给这个admin的角色
     * @param adminId
     * @param modelMap
     * @return
     */
    @RequestMapping("/assign/to/assign/role/page.html")
    public String toAssignRolePage(Integer adminId, ModelMap modelMap) {
        // 1.查询已分配的角色
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);

        // 2. 查询未分配的角色
        List<Role> unAssignedRoleList = roleService.getUnAssignedRole(adminId);

        // 3.存入模型
        modelMap.addAttribute("assignedRoleList", assignedRoleList);
        modelMap.addAttribute("unAssignedRoleList", unAssignedRoleList);

        return "assign-role";
    }

    /**
     * 保存admin - 角色关系
     * @param adminId
     * @param pageNum
     * @param keyword
     * @param roleIdList
     * @return
     */
    @RequestMapping("/assign/do/role/assign.html")
    public String saveAdminRoleRelationship(@RequestParam("adminId") Integer adminId,
                                            @RequestParam("pageNum") Integer pageNum,
                                            @RequestParam("keyword") String keyword,
                                            //允许用户在页面上取消所有角色，故该参数可以不提交
                                            @RequestParam(value = "roleIdList", required = false) List<Integer> roleIdList) {

        adminService.saveAdminRoleRelationship(adminId, roleIdList);

        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    /**
     * 获取所有权限
     * @return
     */
    @RequestMapping("/assign/get/all/auth.json")
    @ResponseBody
    public ResultEntity<List<Auth>> getAllAuth() {

        List<Auth> authList = authService.getAll();

        return ResultEntity.successWithData(authList);
    }

    /**
     * 获取该角色的所有权限id
     * @param roleId
     * @return
     */
    @RequestMapping("/assign/get/assigned/auth/id/by/role/id.json")
    @ResponseBody
    public ResultEntity<List<Integer>> getAssignAuthIdByRoleId(Integer roleId) {

        List<Integer> authIdList = authService.getAssignAuthIdByRoleId(roleId);

        return ResultEntity.successWithData(authIdList);
    }

    /**
     * 保存角色 - 权限关系
     * @param map
     * @return
     */
    @RequestMapping("assign/do/role/assign/auth.json")
    @ResponseBody
    public ResultEntity<String> saveRoleAuthRelationship(
                                @RequestBody Map<String, List<Integer>> map) {

        authService.saveRoleAuthRelationship(map);

        return ResultEntity.successWithoutData();
    }
}
