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
import org.springframework.util.DigestUtils;
import sale.ljw.electronictariffmanagementsystem.backend.dao.AccountMapper;
import sale.ljw.electronictariffmanagementsystem.backend.dao.CostMapper;
import sale.ljw.electronictariffmanagementsystem.backend.dao.ServiceMapper;
import sale.ljw.electronictariffmanagementsystem.backend.form.AddServiceByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.form.EditServiceByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.form.FindServiceByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.Account;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.Cost;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.ServiceProp;
import sale.ljw.electronictariffmanagementsystem.backend.service.ServiceService;
import sale.ljw.electronictariffmanagementsystem.common.http.ResponseResult;
import sale.ljw.electronictariffmanagementsystem.common.http.StatusCode;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author 86155
 * @description 针对表【service】的数据库操作Service实现
 * @createDate 2022-09-08 10:32:14
 */
@Service
public class ServiceServiceImpl extends ServiceImpl<ServiceMapper, ServiceProp>
        implements ServiceService {

    @Autowired
    private ServiceMapper serviceMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private CostMapper costMapper;

    @Override
    public String findServiceByPage(FindServiceByAdmin service) {
        PageHelper.startPage(service.getPage(), 10);
        ArrayList<Map<String, Objects>> services = serviceMapper.findService(service);
        PageInfo pageInfo = new PageInfo(services);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo, "查找成功"));
    }

    @Override
    public String suspendBusinessAccountById(Integer serviceId) {
        //判断当前状态
        QueryWrapper<ServiceProp> queryWrapper_0 = new QueryWrapper<>();
        queryWrapper_0.eq("service_id", serviceId);
        ServiceProp serviceProp = serviceMapper.selectOne(queryWrapper_0);
        if (serviceProp == null) {
            return JSON.toJSONString(ResponseResult.getErrorResult("账号id不存在", StatusCode.NO_CONTENT));
        }
        if (serviceProp.getStatus().equals("2")) {
            return JSON.toJSONString(ResponseResult.getErrorResult("当前业务账号状态异常，不支持操作", StatusCode.NO_CONTENT));
        }
        QueryWrapper<Account> queryWrapper_1 = new QueryWrapper<>();
        queryWrapper_1.eq("account_id", serviceProp.getAccountId()).select("status");
        Account account = accountMapper.selectOne(queryWrapper_1);
        if (account.getStatus().equals("1") || account.getStatus().equals("2")) {
            return JSON.toJSONString(ResponseResult.getErrorResult("父账户账号状态异常，不支持该操作", StatusCode.NO_CONTENT));
        }
        //当前状态判断
        if (serviceProp.getStatus().equals("0")) {
            serviceProp.setPauseDate(new Date());
            serviceProp.setStatus("1");
        } else {
            serviceProp.setPauseDate(null);
            serviceProp.setStatus("0");
        }
        if (serviceMapper.updateById(serviceProp) == 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("此账号修改失败", StatusCode.NOT_MODIFIED));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null, "账号修改成功"));
    }

    @Override
    public String deleteAccountById(Integer serviceId) {
        QueryWrapper<ServiceProp> queryWrapper_0 = new QueryWrapper<>();
        queryWrapper_0.eq("service_id", serviceId);
        ServiceProp serviceProp = serviceMapper.selectOne(queryWrapper_0);
        if (serviceProp == null) {
            return JSON.toJSONString(ResponseResult.getErrorResult("账号id不存在", StatusCode.NO_CONTENT));
        }
        if (serviceProp.getStatus().equals("2")) {
            return JSON.toJSONString(ResponseResult.getErrorResult("当前业务账号已经处于删除状态", StatusCode.NO_CONTENT));
        }
        serviceProp.setPauseDate(null);
        serviceProp.setCloseDate(new Date());
        serviceProp.setStatus("2");
        if (serviceMapper.updateById(serviceProp) == 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("此账号删除失败", StatusCode.NOT_MODIFIED));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null, "账号删除成功"));
    }

    @Override
    public String editAccountServiceById(EditServiceByAdmin service) {
        //判断账户账号是否存在
        Integer referrerPolicy = accountMapper.findReferrerPolicy(service.getAccountIdCard());
        if (referrerPolicy == null) {
            return JSON.toJSONString(ResponseResult.getErrorResult("账户身份证号不存在", StatusCode.NOT_FOUND));
        }
        //判断当前父账户账户状态如果为1：子账户状态必须为1\2
        QueryWrapper<Account> queryWrapper_1 = new QueryWrapper<>();
        queryWrapper_1.eq("account_id", referrerPolicy).select("status");
        Account account = accountMapper.selectOne(queryWrapper_1);
        if (!account.getStatus().equals("0")) {
            if (service.getStatus().equals("0") || account.getStatus().equals("2")) {
                return JSON.toJSONString(ResponseResult.getErrorResult("父账户账号状态异常，不支持该操作", StatusCode.NO_CONTENT));
            }
        }
        //判断cost账号是否存在
        QueryWrapper<Cost> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cost_id", service.getCostId());
        if (costMapper.selectOne(queryWrapper) == null) {
            return JSON.toJSONString(ResponseResult.getErrorResult("套餐id不存在", StatusCode.NOT_FOUND));
        }
        //判断os用户名是否存在
        QueryWrapper<ServiceProp> queryWrapper_0 = new QueryWrapper<>();
        queryWrapper_0.eq("os_username", service.getOsUsername()).notIn("service_id", service.getServiceId());
        if (serviceMapper.selectList(queryWrapper_0).size() != 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("os用户名重复", StatusCode.NO_CONTENT));
        }
        UpdateWrapper<ServiceProp> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("service_id", service.getServiceId())
                .set("os_username", service.getOsUsername())
                .set("status", service.getStatus())
                .set("unix_host", service.getUnixHost())
                .set("account_id", referrerPolicy)
                .set("cost_id", service.getCostId());
        if (service.getStatus().equals("1")) {
            updateWrapper.set("pause_date", new Date());
        }
        if (!update(updateWrapper)) {
            return JSON.toJSONString(ResponseResult.getErrorResult("账号修改失败", StatusCode.NOT_MODIFIED));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null, "账号修改成功"));
    }

    @Override
    public String addAccountService(AddServiceByAdmin service) {
        //判断账户账号是否存在
        Integer referrerPolicy = accountMapper.findReferrerPolicy(service.getAccountIdCard());
        if (referrerPolicy == null) {
            return JSON.toJSONString(ResponseResult.getErrorResult("账户身份证号不存在", StatusCode.NOT_FOUND));
        }
        //判断当前父账户账户状态如果为1：子账户状态必须为1\2
        QueryWrapper<Account> queryWrapper_1 = new QueryWrapper<>();
        queryWrapper_1.eq("account_id", referrerPolicy).select("status");
        Account account = accountMapper.selectOne(queryWrapper_1);
        if (!account.getStatus().equals("0")) {
            if (service.getStatus().equals("0") || account.getStatus().equals("2")) {
                return JSON.toJSONString(ResponseResult.getErrorResult("父账户账号状态异常，不支持该操作", StatusCode.NO_CONTENT));
            }
        }
        //判断cost账号是否存在
        QueryWrapper<Cost> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cost_id", service.getCostId());
        if (costMapper.selectOne(queryWrapper) == null) {
            return JSON.toJSONString(ResponseResult.getErrorResult("套餐id不存在", StatusCode.NOT_FOUND));
        }
        //判断os用户名是否存在
        QueryWrapper<ServiceProp> queryWrapper_0 = new QueryWrapper<>();
        queryWrapper_0.eq("os_username", service.getOsUsername());
        if (serviceMapper.selectList(queryWrapper_0).size() != 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("os用户名重复", StatusCode.NO_CONTENT));
        }
        ServiceProp serviceProp = new ServiceProp();
        BeanUtils.copyProperties(service, serviceProp);
        serviceProp.setCreateDate(new Date());
        serviceProp.setAccountId(referrerPolicy);
        serviceProp.setLoginPasswd(DigestUtils.md5DigestAsHex(service.getLoginPasswd().getBytes(StandardCharsets.UTF_8)));
        if (service.getStatus().equals("1")) {
            serviceProp.setPauseDate(new Date());
        }
        if (serviceMapper.insert(serviceProp) == 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("账号添加失败", StatusCode.NOT_MODIFIED));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null, "账号添加成功"));
    }
}




