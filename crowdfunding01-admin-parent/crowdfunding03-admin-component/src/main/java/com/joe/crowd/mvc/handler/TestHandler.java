package com.joe.crowd.mvc.handler;

import com.joe.crowd.entity.Admin;
import com.joe.crowd.service.api.AdminService;
import org.omg.CORBA.DynAnyPackage.Invalid;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
public class TestHandler {
    @Autowired
    public AdminService adminService;

    @RequestMapping("/test/ssm.html")
    public String testSSM(ModelMap modelMap) {
        List<Admin> adminList = adminService.getAll();
        modelMap.addAttribute("adminList", adminList);
        return "target";
    }

    @RequestMapping("/send/array/one.html")
    @ResponseBody
    public String testReceiveArrayOne(@RequestParam("array[]") Integer[] list) {
        System.out.println(Arrays.toString(list));
        return "hi";
    }

    @RequestMapping("/send/array/two.html")
    @ResponseBody
    public String testReceiveArrayTwo(ParamData paramData) {
        System.out.println(paramData.getArray());
        return "hi";
    }

    @RequestMapping("/send/array/three.html")
    @ResponseBody
    public String testReceiveArrayThree(@RequestBody List<Integer> array) {
        System.out.println(array);
        return "hi";
    }


    /*
    假如data是简单的（简单的Map的也行）如
    "stuId" : 1,
    "stuName" : "Joe",
     "map" : {
     "k1": "v1",
     "k2": "v2"
     }
    用Student可以接的
    */

    /*
     但是如果data是带有复合自定义对象属性的，用Student就接不了
     主要是看request的payload，contentType是application/x-www-form-urlencoded时:
     stuId: 1
     stuName: Joe
     role[id]: 10
     role[name]: dev
     roles[0][id]: 1
     roles[0][name]: boss
     roles[1][id]: 2
     roles[1][name]: boss2
     map[k1][id]: 1
     map[k1][name]: boss
     map[k2][id]: 2
     map[k2][name]: boss2

     报错：
     InvalidPropertyException: Invalid property 'role[id]' of bean class
     [com.joe.crowd.mvc.handler.Student]: Property referenced in indexed
     property path 'role[id]' is neither an array nor a List nor a Map;
     returned value was [Role{id=null, name='null'}]}

     .InvalidPropertyException: Invalid property 'map[k1][id]' of bean class
     [com.joe.crowd.mvc.handler.Student]: Property referenced in indexed property
     path 'map[k1][id]' is neither an array nor a List nor a Map; returned value
     was [Role{id=null, name='null'}]}]

     InvalidPropertyException: Invalid property 'roles[0][id]' of bean class
     [com.joe.crowd.mvc.handler.Student]: Property referenced in indexed property path
     'roles[0][id]' is neither an array nor a List nor a Map; returned value was
     [Role{id=null, name='null'}]}
     */


    @RequestMapping("/send/compose/object.html")
    @ResponseBody
    public String tesetReceiveComposeObject(@RequestBody Student student) {
        System.out.println(student);
        return "hi";
    }
}
