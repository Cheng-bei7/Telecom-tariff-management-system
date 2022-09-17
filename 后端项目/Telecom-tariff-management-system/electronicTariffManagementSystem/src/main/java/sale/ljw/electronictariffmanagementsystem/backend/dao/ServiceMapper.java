package sale.ljw.electronictariffmanagementsystem.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.electronictariffmanagementsystem.backend.form.FindServiceByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.ServiceProp;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * @author 86155
 * @description 针对表【service】的数据库操作Mapper
 * @createDate 2022-09-08 10:32:14
 * @Entity sale.ljw.electronictariffmanagementsystem.backend.pojo.Service
 */
@Mapper
@Repository
public interface ServiceMapper extends BaseMapper<ServiceProp> {
    ArrayList<Map<String, Objects>> findService(FindServiceByAdmin service);

}




