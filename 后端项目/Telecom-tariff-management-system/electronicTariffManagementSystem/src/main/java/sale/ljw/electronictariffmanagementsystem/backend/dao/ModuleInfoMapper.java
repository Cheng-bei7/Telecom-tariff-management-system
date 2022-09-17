package sale.ljw.electronictariffmanagementsystem.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.ModuleInfo;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【module_info】的数据库操作Mapper
 * @createDate 2022-09-13 10:58:47
 * @Entity sale.ljw.electronictariffmanagementsystem.backend.pojo.ModuleInfo
 */
@Mapper
@Repository
public interface ModuleInfoMapper extends BaseMapper<ModuleInfo> {
    ArrayList<ModuleInfo> findRoleModule(Integer role_id);

    ArrayList<Map<String, Object>> findByAdminId(Integer id);
}




