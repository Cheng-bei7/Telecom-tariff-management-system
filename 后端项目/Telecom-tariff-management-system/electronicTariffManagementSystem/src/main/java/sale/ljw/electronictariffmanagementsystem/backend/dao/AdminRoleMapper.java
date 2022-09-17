package sale.ljw.electronictariffmanagementsystem.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.AdminRole;

/**
 * @author 86155
 * @description 针对表【admin_role】的数据库操作Mapper
 * @createDate 2022-09-13 16:46:10
 * @Entity backend.pojo.AdminRole
 */
@Mapper
@Repository
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

}




