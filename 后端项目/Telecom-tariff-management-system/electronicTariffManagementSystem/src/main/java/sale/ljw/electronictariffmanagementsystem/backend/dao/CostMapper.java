package sale.ljw.electronictariffmanagementsystem.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.Cost;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * @author 86155
 * @description 针对表【cost】的数据库操作Mapper
 * @createDate 2022-09-03 15:28:00
 * @Entity sale.ljw.electronictariffmanagementsystem.backend.pojo.Cost
 */
@Mapper
@Repository
public interface CostMapper extends BaseMapper<Cost> {
    ArrayList<Map<String, Objects>> findAllByName(String name);
}




