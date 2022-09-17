package sale.ljw.electronictariffmanagementsystem.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.electronictariffmanagementsystem.backend.form.FindAdminInformationByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.AdminInfo;

import java.util.ArrayList;
import java.util.Map;


/**
 * @author 86155
 * @description 针对表【admin_info】的数据库操作Mapper
 * @createDate 2022-09-03 15:20:36
 * @Entity sale.ljw.electronictariffmanagementsystem.backend.pojo.AdminInfo
 */
@Mapper
@Repository
public interface AdminInfoMapper extends BaseMapper<AdminInfo> {


    ArrayList<Map<String, Object>> findAllAdmin(FindAdminInformationByAdmin adminInformation);

    Integer passwordReset(ArrayList<Integer> ids);
}
