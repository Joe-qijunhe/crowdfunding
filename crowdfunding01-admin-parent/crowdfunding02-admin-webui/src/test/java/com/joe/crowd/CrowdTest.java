package com.joe.crowd;

import com.joe.crowd.entity.Admin;
import com.joe.crowd.entity.Role;
import com.joe.crowd.mapper.AdminMapper;
import com.joe.crowd.mapper.RoleMapper;
import com.joe.crowd.service.api.AdminService;
import com.joe.crowd.util.CrowdUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-tx.xml", "classpath:spring-persist-mybatis.xml"})
public class CrowdTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void test1() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void test2() {
//        Admin admin;
//        admin = new Admin(null,"Rachel","123123","rui","rui@qq.com",null);
//        int count = adminMapper.insert(admin);
//        System.out.println(count);
        for (int i = 38; i < 258; i++) {
            adminMapper.insert(new Admin(null, "user"+i, CrowdUtil.md5("pswd"+i), "u"+i, i+"qq.com", null));
        }
    }

    @Test
    public void testRole() {
        for (int i = 0; i < 235; i++) {
            roleMapper.insert(new Role(null, "role_" + i));
        }
    }

    @Test
    public void testLog() {
        //获取Logger对象，这里传入的Class就是当前打印日志的类
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);
        logger.debug("Hello I am debugging now......");
        logger.info("Hello info level");
        logger.warn("Hello warn level");
        logger.error("Hello error level");

    }

    @Test
    public void testService() {
        Admin admin = new Admin(null, "jerry", "1234", "j", "123@qq.com", null);
        adminService.saveAdmin(admin);
    }

    @Test
    public void testPasswordEncode() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("123123"));
    }
}
