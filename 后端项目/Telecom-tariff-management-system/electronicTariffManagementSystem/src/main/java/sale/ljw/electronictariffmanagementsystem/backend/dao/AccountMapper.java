package sale.ljw.electronictariffmanagementsystem.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.electronictariffmanagementsystem.backend.form.FindAccountByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.Account;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 86155
 * @description 针对表【account】的数据库操作Mapper
 * @createDate 2022-09-06 08:59:52
 * @Entity sale.ljw.electronictariffmanagementsystem.backend.pojo.Account
 */
@Mapper
@Repository
public interface AccountMapper extends BaseMapper<Account> {

    List<Map<String, Objects>> findAllAccount(FindAccountByAdmin account);

    Integer findReferrerPolicy(String idCard);
}




