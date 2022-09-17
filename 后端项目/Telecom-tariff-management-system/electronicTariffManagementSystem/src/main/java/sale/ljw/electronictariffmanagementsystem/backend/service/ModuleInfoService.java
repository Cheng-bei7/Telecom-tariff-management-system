package sale.ljw.electronictariffmanagementsystem.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.ModuleInfo;

/**
 * @author 86155
 * @description 针对表【module_info】的数据库操作Service
 * @createDate 2022-09-13 10:58:47
 */
public interface ModuleInfoService extends IService<ModuleInfo> {

    String findAllModuleInfoList();
}
