package sale.ljw.electronictariffmanagementsystem.backend.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.DigestUtils;
import sale.ljw.electronictariffmanagementsystem.backend.dao.AdminInfoMapper;
import sale.ljw.electronictariffmanagementsystem.backend.dao.AdminRoleMapper;
import sale.ljw.electronictariffmanagementsystem.backend.dao.ModuleInfoMapper;
import sale.ljw.electronictariffmanagementsystem.backend.dao.RoleInfoMapper;
import sale.ljw.electronictariffmanagementsystem.backend.form.*;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.AdminInfo;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.AdminRole;
import sale.ljw.electronictariffmanagementsystem.backend.service.AdminInfoService;
import sale.ljw.electronictariffmanagementsystem.common.http.ResponseResult;
import sale.ljw.electronictariffmanagementsystem.common.http.StatusCode;
import sale.ljw.electronictariffmanagementsystem.common.sercurity.utils.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author 86155
 * @description 针对表【admin_info】的数据库操作Service实现
 * @createDate 2022-09-03 15:20:36
 */
@Service
public class AdminInfoServiceImpl extends ServiceImpl<AdminInfoMapper, AdminInfo>
        implements AdminInfoService {
    @Autowired
    private AdminInfoMapper adminInfoMapper;
    @Autowired
    private ModuleInfoMapper moduleInfoMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;
    @Autowired
    private RoleInfoMapper roleInfoMapper;

    @Override
    public String loginByAdmin(LoginAdminFrom loginFrom, HttpServletRequest request, HttpServletResponse response) {
        //直接进行登录
        QueryWrapper<AdminInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_code", loginFrom.getLoginName())
                .eq("password", DigestUtils.md5DigestAsHex(loginFrom.getPassword().getBytes(StandardCharsets.UTF_8)))
                .select("name", "telephone", "email", "admin_id");
        AdminInfo adminInfo = adminInfoMapper.selectOne(queryWrapper);
        if (adminInfo == null) {
            //密码错误，用户名不存在
            return JSON.toJSONString(ResponseResult.getErrorResult("密码错误或用户名不存在", StatusCode.NOT_FOUND));
        }
        //用户存在,请求头存数据
        response.setHeader("token", JwtUtils.sign(adminInfo.getName(), adminInfo.getAdminId()));
        ArrayList<Map<String, Object>> byAdminId = moduleInfoMapper.findByAdminId(adminInfo.getAdminId());
        Map<String, Object> map = new HashMap<>();
        map.put("adminInformation", adminInfo);
        map.put("showView", byAdminId);
        //返回数据
        return JSON.toJSONString(ResponseResult.getSuccessResult(map, "登录成功"));
    }

    @Override
    public String findAllAdmin(FindAdminInformationByAdmin adminInformation) {
        PageHelper.startPage(adminInformation.getPage(), 10);
        if(adminInformation.getRoleName()==null){
            adminInformation.setRoleName("");
        }
        if(adminInformation.getModuleId()==null){
            adminInformation.setModuleId(new ArrayList<>());
        }
        ArrayList<Map<String, Object>> allAdmin = adminInfoMapper.findAllAdmin(adminInformation);
        PageInfo pageInfo = new PageInfo(allAdmin);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo, "查找成功"));
    }

    @Transactional
    @Override
    public String deleteAdminById(Integer adminId) {
        //创建回滚点
        Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
        //先撤销该管理员的权限
        if (adminRoleMapper.deleteById(adminId) == 0) {
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            return JSON.toJSONString(ResponseResult.getErrorResult("撤销管理员权限时出错", StatusCode.NOT_MODIFIED));
        }
        //删除管理员信息
        if (adminInfoMapper.deleteById(adminId) == 0) {
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            return JSON.toJSONString(ResponseResult.getErrorResult("删除管理员时出错", StatusCode.NOT_MODIFIED));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null, "删除成功！"));
    }

    @Override
    public String passwordReset(idsInt ids) {
        //将密码重置为123456
        if (adminInfoMapper.passwordReset(ids.getIds()) != ids.getIds().size()) {
            return JSON.toJSONString(ResponseResult.getErrorResult("修改失败", StatusCode.NOT_MODIFIED));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null, "修改成功！"));
    }

    @Transactional
    @Override
    public String addAdminInformation(AdminInformationByAdmin adminInformation) {
        //检测管理员账号是否重复
        QueryWrapper<AdminInfo> queryWrapper_0 = new QueryWrapper<>();
        queryWrapper_0.eq("admin_code", adminInformation.getAdminCode());
        if (adminInfoMapper.selectCount(queryWrapper_0) != 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("管理员账号重复，请修改", StatusCode.NOT_FOUND));
        }
        //封装
        AdminInfo adminInfo = new AdminInfo();
        BeanUtils.copyProperties(adminInformation, adminInfo);
        adminInfo.setEnrolldate(new Date());
        adminInfo.setPassword(DigestUtils.md5DigestAsHex(adminInformation.getPassword().getBytes(StandardCharsets.UTF_8)));
        //创建回滚点
        Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
        //添加、主键回填
        if (adminInfoMapper.insert(adminInfo) == 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("添加失败", StatusCode.NOT_MODIFIED));
        }
        //添加权限
        for (Integer roleId : adminInformation.getRoleIds()) {
            AdminRole role = new AdminRole();
            role.setAdminId(adminInfo.getAdminId());
            role.setRoleId(roleId);
            if (adminRoleMapper.insert(role) == 0) {
                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                return JSON.toJSONString(ResponseResult.getErrorResult("为管理员分配权限时出错", StatusCode.NOT_MODIFIED));
            }
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null, "添加成功！"));
    }

    @Override
    public String getAllRole() {
        return JSON.toJSONString(ResponseResult.getSuccessResult(roleInfoMapper.selectList(null), null));
    }

    @Transactional
    @Override
    public String editAdminInformation(EditAdminInformationByAdmin adminInformation) {
        //设置回滚点
        Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
        //修改权限
        for (Integer roleId : adminInformation.getRoleIds()) {
            AdminRole role = new AdminRole();
            role.setAdminId(adminInformation.getAdminId());
            role.setRoleId(roleId);
            if (adminRoleMapper.insert(role) == 0) {
                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                return JSON.toJSONString(ResponseResult.getErrorResult("为管理员分配权限时出错", StatusCode.NOT_MODIFIED));
            }
        }
        //修改管理员基本信息
        AdminInfo adminInfo = new AdminInfo();
        BeanUtils.copyProperties(adminInformation, adminInfo);
        UpdateWrapper<AdminInfo> updateWrapper_0 = new UpdateWrapper<>();
        updateWrapper_0.eq("admin_id", adminInformation.getAdminId())
                .set("email", adminInformation.getEmail())
                .set("telephone", adminInformation.getTelephone())
                .set("name", adminInformation.getName());
        if (!update(updateWrapper_0)) {
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            return JSON.toJSONString(ResponseResult.getErrorResult("修改管理员信息的时候出错", StatusCode.NOT_MODIFIED));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null, "修改成功！"));
    }

    @Override
    public String changePassword(ChangePassword password, HttpServletRequest req, HttpServletResponse resp) {
        //解析token的值获取其中封装的管理员id
        String token = req.getHeader("token");
        Integer adminId = JwtUtils.parseJWT(token);
        //检测旧密码
        QueryWrapper<AdminInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("password", DigestUtils.md5DigestAsHex(password.getOldPassword().getBytes(StandardCharsets.UTF_8))).eq("admin_id", adminId);
        if (adminInfoMapper.selectCount(queryWrapper) == 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("管理员旧密码错误，请重新输入", StatusCode.NOT_MODIFIED));
        }
        //修改新密码
        UpdateWrapper<AdminInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("admin_id", adminId).set("password", DigestUtils.md5DigestAsHex(password.getNewPassword().getBytes(StandardCharsets.UTF_8)));
        if (!update(updateWrapper)) {
            return JSON.toJSONString(ResponseResult.getErrorResult("修改失败！", StatusCode.NOT_MODIFIED));
        }
        resp.setHeader("token", null);
        return JSON.toJSONString(ResponseResult.getSuccessResult(null, "修改成功！请重新登录"));
    }
}
