package com.joe.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.joe.crowd.constant.CrowdConstant;
import com.joe.crowd.entity.Admin;
import com.joe.crowd.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class AdminHandler {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/admin/do/login.html")
    public String doLogin(@RequestParam("loginAcct") String loginAcct,
                          @RequestParam("userPswd") String userPswd,
                          HttpSession session) {
        // 1.调用service方法执行登录检查
        // 返回admin对象说明登录成功，否则会抛出异常
        Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);

        // 2.将登录成功返回的admin对象存入Session域
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);
        return "redirect:/admin/to/main/page.html";
    }

    @RequestMapping("admin/do/logout.html")
    public String doLogout(HttpSession session) {
        // 使session失效
        session.invalidate();
        return "redirect:/admin/to/login/page.html";
    }

    /**
     * Admin分页
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @param modelMap
     * @return
     */
    @RequestMapping("/admin/get/page.html")
    public String getPageInfo(
            // 使用defaultValue指定默认值
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            ModelMap modelMap) {
        // 调用service方法获取PageInfo对象
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
        // 将PageInfo对象存入模型
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);
        return "admin-page";
    }

    @RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
    public String remove(@PathVariable("adminId") Integer adminId,
                         @PathVariable("pageNum") Integer pageNum,
                         @PathVariable("keyword") String keyword) {
        // 调用service层方法，从数据库根据id删除管理员
        adminService.removeById(adminId);
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    @PreAuthorize("hasAuthority('user:save')")
    @RequestMapping("/admin/save.html")
    public String save(Admin admin) {
        adminService.saveAdmin(admin);
        return "redirect:/admin/get/page.html?pageNum=" + Integer.MAX_VALUE;
    }

    @RequestMapping("/admin/to/edit/page.html")
    public String toEditPage(@RequestParam("adminId") Integer adminId, ModelMap modelMap) {
        // 通过id查询admin对象
        Admin admin = adminService.getAdminById(adminId);
        modelMap.addAttribute("admin", admin);
        return "admin-edit";
    }

    @RequestMapping("/admin/update.html")
    public String update(Admin admin, Integer pageNum, String keyword) {
        adminService.update(admin);
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }
}
