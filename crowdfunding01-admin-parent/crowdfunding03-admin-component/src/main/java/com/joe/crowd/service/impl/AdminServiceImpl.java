package com.joe.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.joe.crowd.constant.CrowdConstant;
import com.joe.crowd.entity.Admin;
import com.joe.crowd.entity.AdminExample;
import com.joe.crowd.exception.LoginAcctAlreadyInUseException;
import com.joe.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.joe.crowd.exception.LoginFailedException;
import com.joe.crowd.mapper.AdminMapper;
import com.joe.crowd.service.api.AdminService;
import com.joe.crowd.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void saveAdmin(Admin admin) {
        // 密码加密
        String userPswd = admin.getUserPswd();
        userPswd = passwordEncoder.encode(userPswd);
        admin.setUserPswd(userPswd);

        // 创建时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(date);
        admin.setCreateTime(createTime);

        //保存
        try {
            adminMapper.insert(admin);
        } catch (DuplicateKeyException e) {
            // 用户名已存在错误
            throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
        }
    }

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
        // 1、根据登陆账号查询Admin对象
        // 创建AdminExample对象
        AdminExample adminExample = new AdminExample();

        // 创建Criteria对象
        AdminExample.Criteria criteria = adminExample.createCriteria();

        // 在Criteria对象中封装查询的条件
        criteria.andLoginEqualTo(loginAcct);

        // 调用AdminMapper的方法来查询
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        // 2、判断Admin对象是否为null或数据库数据不正常
        if (adminList == null || adminList.size() == 0) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        if (adminList.size() > 1) {
            throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }
        // 前面判断完后无异常，取出admin对象
        Admin admin = adminList.get(0);

        // 3、如果Admin对象为null 则抛出异常
        if (admin == null) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        // 4、如果Admin对象不为null，则取出Admin对象的密码
        String userPswdDB = admin.getUserPswd();

        // 5、对表单提交的密码进行md5加密
        String userPswdForm = CrowdUtil.md5(userPswd);

        // 6、对比两个密码
        if (!Objects.equals(userPswdDB, userPswdForm)) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        // 7、比对结果一致，返回admin对象
        return admin;
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        // 1.调用PageHelper的静态方法开启分页功能
        PageHelper.startPage(pageNum, pageSize);

        // 2.执行查询，这个list实际上是个Page类型，Page类型继承了ArrayList，所以可以用List做类型
        List<Admin> adminList = adminMapper.selectAdminByKeyword(keyword);

        // 3.封装到PageInfo对象
        // 使用PageInfo作为返回值，是因为PageInfo对象中可以携带当前的页码、
        // 每页大小、总页数等数据，在前端取值时，比直接返回一个Admin的List更加方便。
        return new PageInfo<>(adminList);
    }

    @Override
    public void removeById(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }

    @Override
    public void update(Admin admin) {
        // Selective：有选择的更新，对于null值字段不更新
        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (DuplicateKeyException e) {
            throw new LoginAcctAlreadyInUseForUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
        }
    }

    @Override
    public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {
        // 1. 根据adminId删除旧的关联关系数据
        adminMapper.deleteOldRelationship(adminId);

        // 2.根据roleIdList和adminId保存新的关联关系
        if (roleIdList != null && roleIdList.size()>0) {
            adminMapper.insertNewRelationship(adminId, roleIdList);
        }
    }

    @Override
    public Admin getAdminByLoginAcct(String username) {
        AdminExample example = new AdminExample();

        AdminExample.Criteria criteria = example.createCriteria();

        criteria.andLoginEqualTo(username);

        List<Admin> adminList = adminMapper.selectByExample(example);

        return adminList.get(0);
    }


}
