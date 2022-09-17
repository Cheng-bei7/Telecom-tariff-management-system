package sale.ljw.electronictariffmanagementsystem.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.Permissionurl;

/**
 * @author 86155
 * @description 针对表【permissionUrl】的数据库操作Mapper
 * @createDate 2022-09-13 16:25:42
 * @Entity backend.pojo.Permissionurl
 */
@Mapper
@Repository
public interface PermissionurlMapper extends BaseMapper<Permissionurl> {

}




