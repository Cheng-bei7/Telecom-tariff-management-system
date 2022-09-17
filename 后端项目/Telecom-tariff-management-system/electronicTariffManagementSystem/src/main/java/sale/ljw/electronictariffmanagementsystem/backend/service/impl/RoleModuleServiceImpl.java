package sale.ljw.electronictariffmanagementsystem.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.electronictariffmanagementsystem.backend.dao.RoleModuleMapper;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.RoleModule;
import sale.ljw.electronictariffmanagementsystem.backend.service.RoleModuleService;

/**
 * @author 86155
 * @description 针对表【role_module】的数据库操作Service实现
 * @createDate 2022-09-13 10:58:55
 */
@Service
public class RoleModuleServiceImpl extends ServiceImpl<RoleModuleMapper, RoleModule>
        implements RoleModuleService {
    @Autowired
    private RoleModuleMapper roleModuleMapper;
}




