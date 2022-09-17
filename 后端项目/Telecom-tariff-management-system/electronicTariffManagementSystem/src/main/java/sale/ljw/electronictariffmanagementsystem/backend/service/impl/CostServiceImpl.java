package sale.ljw.electronictariffmanagementsystem.backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.electronictariffmanagementsystem.backend.dao.CostMapper;
import sale.ljw.electronictariffmanagementsystem.backend.form.AddCostByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.form.EditCostFromByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.form.FindCostFrom;
import sale.ljw.electronictariffmanagementsystem.backend.form.Ids;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.Cost;
import sale.ljw.electronictariffmanagementsystem.backend.service.CostService;
import sale.ljw.electronictariffmanagementsystem.common.http.ResponseResult;
import sale.ljw.electronictariffmanagementsystem.common.http.StatusCode;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author 86155
 * @description 针对表【cost】的数据库操作Service实现
 * @createDate 2022-09-03 15:28:00
 */
@Service
public class CostServiceImpl extends ServiceImpl<CostMapper, Cost>
        implements CostService {
    @Autowired
    private CostMapper costMapper;

    @Override
    public String findAllCost(FindCostFrom costFrom) {
        PageHelper.startPage(costFrom.getPage(), 10);
        ArrayList<Map<String, Objects>> allByNameMaps = costMapper.findAllByName(costFrom.getPackageName());
        PageInfo pageInfo = new PageInfo(allByNameMaps);
        if (allByNameMaps.size() == 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("查找失败", StatusCode.NOT_FOUND));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo, "success"));
    }

    @Override
    public String addCostByAdmin(AddCostByAdmin cost) {
        if (cost.getCostType().equals("0")) {
            if (cost.getBaseDuration() != null && cost.getUnitCost() != null) {
                return JSON.toJSONString(ResponseResult.getErrorResult("参数出错", StatusCode.BAD_REQUEST));
            }
        }
        if (cost.getCostType().equals("1")) {
            if (cost.getBaseDuration() == null && cost.getUnitCost() == null && cost.getBaseCost() == null) {
                return JSON.toJSONString(ResponseResult.getErrorResult("参数出错", StatusCode.BAD_REQUEST));
            }
        }
        if (cost.getCostType().equals("2")) {
            if (cost.getBaseDuration() != null && cost.getBaseCost() != null) {
                return JSON.toJSONString(ResponseResult.getErrorResult("参数出错", StatusCode.BAD_REQUEST));
            }
        }
        //查询当前套餐名称是否存在
        QueryWrapper<Cost> queryWrapperByName = new QueryWrapper<>();
        queryWrapperByName.eq("name", cost.getName());
        if (costMapper.selectList(queryWrapperByName).size() != 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("名称重复", StatusCode.NO_CONTENT));
        }

        Cost newCost = new Cost();
        BeanUtils.copyProperties(cost, newCost);
        newCost.setCreatime(new Date());
        int insert = costMapper.insert(newCost);
        if (insert == 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("修改失败", StatusCode.NO_CONTENT));
        } else {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "修改成功"));
        }
    }

    @Override
    public String deleteCostByIds(Ids ids) {
        int result = costMapper.deleteBatchIds(ids.getIds());
        if (result != 0) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "删除成功"));
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("删除失败", StatusCode.NO_CONTENT));
        }
    }

    @Override
    public String editCostById(EditCostFromByAdmin editCostFrom) {
        if (editCostFrom.getCostType().equals("0")) {
            if (editCostFrom.getBaseDuration() != null && editCostFrom.getUnitCost() != null) {
                return JSON.toJSONString(ResponseResult.getErrorResult("参数出错", StatusCode.BAD_REQUEST));
            }
        }
        if (editCostFrom.getCostType().equals("1")) {
            if (editCostFrom.getBaseDuration() == null && editCostFrom.getUnitCost() == null && editCostFrom.getBaseCost() == null) {
                return JSON.toJSONString(ResponseResult.getErrorResult("参数出错", StatusCode.BAD_REQUEST));
            }
        }
        if (editCostFrom.getCostType().equals("2")) {
            if (editCostFrom.getBaseDuration() != null && editCostFrom.getBaseCost() != null) {
                return JSON.toJSONString(ResponseResult.getErrorResult("参数出错", StatusCode.BAD_REQUEST));
            }
        }
        //判断资费名称是否存在且不是当前资费名称
        QueryWrapper<Cost> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", editCostFrom.getName())
                .ne("cost_id", editCostFrom.getCostId());
        Cost cost = costMapper.selectOne(queryWrapper);
        if (cost != null) {
            return JSON.toJSONString(ResponseResult.getErrorResult("名称重复", StatusCode.NO_CONTENT));
        }
        //修改创建时间
        Cost newCost = new Cost();
        BeanUtils.copyProperties(editCostFrom, newCost);
        newCost.setCreatime(new Date());
        //进行更新操作
        int update = costMapper.updateById(newCost);
        if (update != 0) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null, "更新成功"));
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("更新失败", StatusCode.NO_CONTENT));
        }
    }

    @Override
    public String getCostList() {
        QueryWrapper<Cost> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("cost_id", "name");
        return JSON.toJSONString(ResponseResult.getSuccessResult(costMapper.selectMaps(queryWrapper), "获取列表参数成功"));
    }
}




