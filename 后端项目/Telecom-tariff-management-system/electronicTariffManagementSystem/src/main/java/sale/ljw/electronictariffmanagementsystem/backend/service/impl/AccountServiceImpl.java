package sale.ljw.electronictariffmanagementsystem.backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.DigestUtils;
import sale.ljw.electronictariffmanagementsystem.backend.dao.AccountMapper;
import sale.ljw.electronictariffmanagementsystem.backend.dao.ServiceMapper;
import sale.ljw.electronictariffmanagementsystem.backend.form.AddAccountByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.form.EditAccountByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.form.FindAccountByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.Account;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.ServiceProp;
import sale.ljw.electronictariffmanagementsystem.backend.service.AccountService;
import sale.ljw.electronictariffmanagementsystem.common.http.ResponseResult;
import sale.ljw.electronictariffmanagementsystem.common.http.StatusCode;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 86155
 * @description 针对表【account】的数据库操作Service实现
 * @createDate 2022-09-06 08:59:52
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account>
        implements AccountService {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private ServiceMapper serviceMapper;

    @Override
    public String findAllAccount(FindAccountByAdmin account) {
        PageHelper.startPage(account.getPage(), 10);
        List<Map<String, Objects>> allAccount = accountMapper.findAllAccount(account);
        PageInfo pageInfo = new PageInfo(allAccount);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo, "查找成功"));
    }

    @Transactional
    @Override
    public String suspendAccount(String id) {
        //查询当前账号是否存在附属账号
        QueryWrapper<Account> QueryWrapper_1 = new QueryWrapper<>();
        QueryWrapper_1.eq("account_id", id);
        //查询原状态
        Account account = accountMapper.selectOne(QueryWrapper_1);
        if (account.getStatus().equals("2")) {
            //删除状态不可开通暂停
            return JSON.toJSONString(ResponseResult.getErrorResult("删除状态不可修改状态", StatusCode.BAD_REQUEST));
        }
        //设置回滚点
        Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
        String status = "0";
        if (account.getStatus().equals("0")) {
            status = "1";
        }
        if (status.equals("1")) {
            QueryWrapper<ServiceProp> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account_id", id);
            List<ServiceProp> accounts = serviceMapper.selectList(queryWrapper);
            //暂停附属业务账号
            if (accounts.size() != 0) {
                for (ServiceProp accountNew : accounts) {
                    //记录修改时间
                    accountNew.setPauseDate(new Date());
                    //修改状态
                    accountNew.setStatus(status);
                    if (serviceMapper.updateById(accountNew) == 0) {
                        TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                        return JSON.toJSONString(ResponseResult.getErrorResult("附属账号修改失败", StatusCode.NOT_MODIFIED));
                    }
                }
            }
        }
        if (status.equals("0")) {
            account.setPauseDate(null);
        } else {
            account.setPauseDate(new Date());
        }
        account.setStatus(status);
        if (accountMapper.updateById(account) == 0) {
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            return JSON.toJSONString(ResponseResult.getErrorResult("此账号修改失败", StatusCode.NOT_MODIFIED));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null, "账号修改成功"));
    }

    @Transactional
    @Override
    public String deleteById(String id) {
        //查询下属账号
        //查询当前账号是否存在附属账号
        QueryWrapper<Account> QueryWrapper_1 = new QueryWrapper<>();
        QueryWrapper_1.eq("account_id", id);
        //查询原状态
        Account account = accountMapper.selectOne(QueryWrapper_1);
        if (account.getStatus().equals("2")) {
            //删除状态不可开通暂停
            return JSON.toJSONString(ResponseResult.getErrorResult("当前已是删除状态", StatusCode.BAD_REQUEST));
        }
        //设置回滚点
        Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
        //暂停旗下账号
        QueryWrapper<ServiceProp> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", id);
        List<ServiceProp> accounts = serviceMapper.selectList(queryWrapper);
        if (accounts.size() != 0) {
            for (ServiceProp accountNew : accounts) {
                //记录删除时间
                accountNew.setCloseDate(new Date());
                //修改状态
                accountNew.setStatus("2");
                if (serviceMapper.updateById(accountNew) == 0) {
                    TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                    return JSON.toJSONString(ResponseResult.getErrorResult("附属账号删除失败", StatusCode.NOT_MODIFIED));
                }
            }
        }
        account.setCloseDate(new Date());
        account.setStatus("2");
        if (accountMapper.updateById(account) == 0) {
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            return JSON.toJSONString(ResponseResult.getErrorResult("此账号修改失败", StatusCode.NOT_MODIFIED));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null, "账号修改成功"));
    }

    @Transactional
    @Override
    public String addAccount(AddAccountByAdmin account) {
        //判断推荐id是否存在
        if (account.getRecommenderId() != null) {
            QueryWrapper<Account> queryWrapper_0 = new QueryWrapper<>();
            queryWrapper_0.eq("account_id", account.getRecommenderId());
            if (accountMapper.selectList(queryWrapper_0).size() == 0) {
                return JSON.toJSONString(ResponseResult.getErrorResult("推荐id不存在，请重新填写推荐id！", StatusCode.NOT_FOUND));
            }
        }
        //查询登录名是否重复
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_name", account.getLoginName());
        if (accountMapper.selectList(queryWrapper).size() != 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("登录名已存在！", StatusCode.NO_CONTENT));
        }
        Account account_new = new Account();
        //封装
        BeanUtils.copyProperties(account, account_new);
        //查询相同身份证号，并查询id添加到推荐人id
        Integer referrerPolicy = accountMapper.findReferrerPolicy(account.getIdcardNo());
        if (referrerPolicy != null) {
            account_new.setRecommenderId(referrerPolicy);
        }
        // 封装密码
        account_new.setLoginPasswd(DigestUtils.md5DigestAsHex(account_new.getLoginPasswd().getBytes(StandardCharsets.UTF_8)));
        //添加创建日期
        account_new.setCreateDate(new Date());
        //添加项目注意状态
        if (account.getStatus().equals("1")) {
            account_new.setPauseDate(new Date());
        } else if (account.getStatus().equals("2")) {
            account_new.setCloseDate(new Date());
        }
        if (accountMapper.insert(account_new) == 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("新增账号失败", StatusCode.NOT_MODIFIED));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null, "新增账号成功"));
    }

    @Override
    public String findAccountOneById(Integer accountId) {
        //检查当前状态是否不是删除状态
        QueryWrapper<Account> QueryWrapper_1 = new QueryWrapper<>();
        QueryWrapper_1.eq("account_id", accountId)
                .select("account_id", "recommender_id", "login_name", "status", "real_name", "idcard_no", "birthdate", "gender", "occupation", "telephone", "email", "mailaddress", "zipcode", "qq");
        //查询原状态
        Account account = accountMapper.selectOne(QueryWrapper_1);
        if (account.getStatus().equals("2")) {
            //删除状态不可开通暂停
            return JSON.toJSONString(ResponseResult.getErrorResult("当前已是删除状态", StatusCode.BAD_REQUEST));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(account, "查找成功"));
    }

    @Override
    public String findReferrerIdByIdCard(String idCard) {
        //使用系统内置获取推荐人id策略
        Integer referrerPolicy = accountMapper.findReferrerPolicy(idCard);
        return JSON.toJSONString(ResponseResult.getSuccessResult(referrerPolicy, "填充推荐人id"));
    }

    @Transactional
    @Override
    public String updateAccountById(EditAccountByAdmin account) {
        //判断当前状态
        QueryWrapper<Account> QueryWrapper_1 = new QueryWrapper<>();
        QueryWrapper_1.eq("account_id", account.getAccountId()).select("status", "create_date");
        //查询原状态
        Account account_1 = accountMapper.selectOne(QueryWrapper_1);
        if (account_1.getStatus().equals("2")) {
            //删除状态不可开通暂停
            return JSON.toJSONString(ResponseResult.getErrorResult("当前已是删除状态", StatusCode.BAD_REQUEST));
        }
        //判断登录名是否重复
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_name", account.getLoginName());
        List<Account> accounts = accountMapper.selectList(queryWrapper);
        AtomicReference<Boolean> result = new AtomicReference<>(true);
        accounts.stream().forEach(s -> {
            if (s.getAccountId().equals(account.getAccountId())) {
                result.set(false);
            }
        });
        if (accounts.size() != 0 && result.get()) {
            return JSON.toJSONString(ResponseResult.getErrorResult("登录名已存在！", StatusCode.NO_CONTENT));
        }
        //更新数据
        Account account_2 = new Account();
        BeanUtils.copyProperties(account, account_2);
        //封装密码
        account_2.setLoginPasswd(DigestUtils.md5DigestAsHex(account_2.getLoginPasswd().getBytes(StandardCharsets.UTF_8)));
        account_2.setCreateDate(account_1.getCreateDate());
        //暂停状态添加暂停时间
        if (account_2.getStatus().equals("1")) {
            account_2.setPauseDate(new Date());
        }
        if (accountMapper.updateById(account_2) == 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("账号修改失败", StatusCode.NOT_MODIFIED));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null, "账号修改成功"));
    }
}




