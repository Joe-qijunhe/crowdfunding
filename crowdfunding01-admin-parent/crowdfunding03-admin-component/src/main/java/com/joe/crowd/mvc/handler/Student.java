package com.joe.crowd.mvc.handler;

import com.joe.crowd.entity.Role;

import java.util.List;
import java.util.Map;

//Test
public class Student {
    private Integer stuId;
    private String stuName;
    private Role role;
    private List<Role> roles;
    private Map<String, Role> map;

    public Student() {
    }

    public Student(Integer stuId, String stuName, Role role, List<Role> roles, Map<String, Role> map) {
        this.stuId = stuId;
        this.stuName = stuName;
        this.role = role;
        this.roles = roles;
        this.map = map;
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Map<String, Role> getMap() {
        return map;
    }

    public void setMap(Map<String, Role> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuId=" + stuId +
                ", stuName='" + stuName + '\'' +
                ", role=" + role +
                ", roles=" + roles +
                ", map=" + map +
                '}';
    }
}
