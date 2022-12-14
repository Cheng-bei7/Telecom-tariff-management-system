package sale.ljw.electronictariffmanagementsystem.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.RoleModule;

/**
 * @author 86155
 * @description 针对表【role_module】的数据库操作Mapper
 * @createDate 2022-09-13 10:58:55
 * @Entity sale.ljw.electronictariffmanagementsystem.backend.pojo.RoleModule
 */
@Mapper
@Repository
public interface RoleModuleMapper extends BaseMapper<RoleModule> {
}




