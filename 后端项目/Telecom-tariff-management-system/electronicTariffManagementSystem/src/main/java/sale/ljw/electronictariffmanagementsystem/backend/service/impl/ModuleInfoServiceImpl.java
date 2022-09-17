package sale.ljw.electronictariffmanagementsystem.backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.electronictariffmanagementsystem.backend.dao.ModuleInfoMapper;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.ModuleInfo;
import sale.ljw.electronictariffmanagementsystem.backend.service.ModuleInfoService;
import sale.ljw.electronictariffmanagementsystem.common.http.ResponseResult;

import java.util.List;

/**
 * @author 86155
 * @description 针对表【module_info】的数据库操作Service实现
 * @createDate 2022-09-13 10:58:47
 */
@Service
public class ModuleInfoServiceImpl extends ServiceImpl<ModuleInfoMapper, ModuleInfo>
        implements ModuleInfoService {
    @Autowired
    private ModuleInfoMapper moduleInfoMapper;

    @Override
    public String findAllModuleInfoList() {
        List<ModuleInfo> moduleInfos = moduleInfoMapper.selectByMap(null);
        return JSON.toJSONString(ResponseResult.getSuccessResult(moduleInfos, "获取数据成功"));
    }
}




