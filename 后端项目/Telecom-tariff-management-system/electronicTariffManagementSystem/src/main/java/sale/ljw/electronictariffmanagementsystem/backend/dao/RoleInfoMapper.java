package sale.ljw.electronictariffmanagementsystem.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.electronictariffmanagementsystem.backend.form.RoleDisplayData;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.RoleInfo;

import java.util.ArrayList;

/**
 * @author 86155
 * @description 针对表【role_info】的数据库操作Mapper
 * @createDate 2022-09-13 10:58:51
 * @Entity sale.ljw.electronictariffmanagementsystem.backend.pojo.RoleInfo
 */
@Mapper
@Repository
public interface RoleInfoMapper extends BaseMapper<RoleInfo> {
    ArrayList<RoleDisplayData> findAllDisplayData();

    ArrayList<RoleInfo> findRoleInfo(Integer admin_id);
}




