package sale.ljw.electronictariffmanagementsystem.backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import sale.ljw.electronictariffmanagementsystem.backend.dao.AdminRoleMapper;
import sale.ljw.electronictariffmanagementsystem.backend.dao.RoleInfoMapper;
import sale.ljw.electronictariffmanagementsystem.backend.dao.RoleModuleMapper;
import sale.ljw.electronictariffmanagementsystem.backend.form.AddRoleInfoByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.form.EditRoleInfoByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.form.RoleDisplayData;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.AdminRole;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.RoleInfo;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.RoleModule;
import sale.ljw.electronictariffmanagementsystem.backend.service.RoleInfoService;
import sale.ljw.electronictariffmanagementsystem.common.http.ResponseResult;
import sale.ljw.electronictariffmanagementsystem.common.http.StatusCode;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 86155
 * @description 针对表【role_info】的数据库操作Service实现
 * @createDate 2022-09-13 10:58:51
 */
@Service
public class RoleInfoServiceImpl extends ServiceImpl<RoleInfoMapper, RoleInfo>
        implements RoleInfoService {
    @Autowired
    private RoleInfoMapper roleInfoMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Autowired
    private RoleModuleMapper roleModuleMapper;

    @Override
    public String findAllRoleModule(Integer page) {
        PageHelper.startPage(page, 10);
        ArrayList<RoleDisplayData> allDisplayData = roleInfoMapper.findAllDisplayData();
        PageInfo pageInfo = new PageInfo(allDisplayData);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo, "获取数据成功"), SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String deleteRoleInfoById(Integer id) {
        //查找该权限是否存在数据
        QueryWrapper<AdminRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", id);
        if (adminRoleMapper.selectCount(queryWrapper) != 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("该权限已被使用中，请先移除使用该权限的管理员", StatusCode.NOT_MODIFIED));
        }
        //不存在使用权删除即可
        if (roleInfoMapper.deleteById(id) == 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("删除失败", StatusCode.NOT_MODIFIED));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null, "删除成功！"));
    }

    @Transactional
    @Override
    public String editRoleInfo(EditRoleInfoByAdmin editRoleInfo) {
        //查询权限名称是否重复
        QueryWrapper<RoleInfo> queryWrapper_roleName = new QueryWrapper<>();
        queryWrapper_roleName.eq("name", editRoleInfo.getName()).notIn("role_id", editRoleInfo.getId());
        if (roleInfoMapper.selectCount(queryWrapper_roleName) != 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("名称重复！请修改名称之后重试", StatusCode.NO_CONTENT));
        }
        //设置回滚点
        Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
        //修改权限名称
        UpdateWrapper<RoleInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("role_id", editRoleInfo.getId()).set("name", editRoleInfo.getName());
        if (!update(updateWrapper)) {
            return JSON.toJSONString(ResponseResult.getErrorResult("修改名称失败", StatusCode.NOT_MODIFIED));
        }
        // 修改权限列表！注意！原列表的数据还存在，因为数据无法可控所以不是update数据，
        // 先取出全部权限列表，此时我们有两张list表，删除其中相同的数据，存储互相不在的数据
        QueryWrapper<RoleModule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", editRoleInfo.getId()).select("module_id");
        ArrayList<Integer> newModuleIds = editRoleInfo.getModuleId();
        ArrayList<Integer> oldModuleIds = (ArrayList<Integer>) roleModuleMapper.selectList(queryWrapper).stream().map(RoleModule::getModuleId).collect(Collectors.toList());
        //使用指针过滤相同数据
        int m = 0, i = 0;
        while (true) {
            if (oldModuleIds.size() == 0 || newModuleIds.size() == 0 || m >= oldModuleIds.size()) {
                break;
            }
            if (Objects.equals(oldModuleIds.get(m), newModuleIds.get(i))) {
                newModuleIds.remove(newModuleIds.get(i));
                oldModuleIds.remove(oldModuleIds.get(m));
            } else {
                i++;
            }
            if (i >= newModuleIds.size()) {
                i = 0;
                m++;
            }
        }
        //过滤掉相同数据new中存储的是需要添加的数据
        //old是需要删除的数据
        //删除数据
        for (Integer oldModuleId : oldModuleIds) {
            QueryWrapper<RoleModule> queryWrapper_Delete = new QueryWrapper<>();
            queryWrapper_Delete.eq("role_id", editRoleInfo.getId()).eq("module_id", oldModuleId);
            if (roleModuleMapper.delete(queryWrapper_Delete) == 0) {
                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                return JSON.toJSONString(ResponseResult.getErrorResult("修改失败", StatusCode.NOT_MODIFIED));
            }
        }
        for (Integer newModuleId : newModuleIds) {
            RoleModule roleModule = new RoleModule();
            roleModule.setRoleId(editRoleInfo.getId());
            roleModule.setModuleId(newModuleId);
            if (roleModuleMapper.insert(roleModule) == 0) {
                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                return JSON.toJSONString(ResponseResult.getErrorResult("修改失败", StatusCode.NOT_MODIFIED));
            }
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null, "修改成功！"));
    }

    @Transactional
    @Override
    public String addRoleInfo(AddRoleInfoByAdmin roleInfo) {
        //查询权限名称是否重复
        QueryWrapper<RoleInfo> queryWrapper_roleName = new QueryWrapper<>();
        queryWrapper_roleName.eq("name", roleInfo.getName());
        if (roleInfoMapper.selectCount(queryWrapper_roleName) != 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("名称重复！请修改名称之后重试", StatusCode.NO_CONTENT));
        }
        //设置回滚点
        Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
        //添加名
        RoleInfo newRoleInfo = new RoleInfo();
        newRoleInfo.setName(roleInfo.getName());
        if (roleInfoMapper.insert(newRoleInfo) == 0) {
            //回滚事务
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            return JSON.toJSONString(ResponseResult.getErrorResult("添加失败", StatusCode.NOT_MODIFIED));
        }
        for (Integer integer : roleInfo.getModuleId()) {
            RoleModule newRoleModule = new RoleModule();
            newRoleModule.setRoleId(newRoleInfo.getRoleId());
            newRoleModule.setModuleId(integer);
            if (roleModuleMapper.insert(newRoleModule) == 0) {
                //回滚事务
                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                return JSON.toJSONString(ResponseResult.getErrorResult("添加权限列表失败，请重新分配权限", StatusCode.NOT_MODIFIED));
            }
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null, "添加成功！"));
    }
}




