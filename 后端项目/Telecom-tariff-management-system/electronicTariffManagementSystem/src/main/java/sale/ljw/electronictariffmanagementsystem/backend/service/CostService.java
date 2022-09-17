package sale.ljw.electronictariffmanagementsystem.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sale.ljw.electronictariffmanagementsystem.backend.form.AddCostByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.form.EditCostFromByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.form.FindCostFrom;
import sale.ljw.electronictariffmanagementsystem.backend.form.Ids;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.Cost;

/**
 * @author 86155
 * @description 针对表【cost】的数据库操作Service
 * @createDate 2022-09-03 15:28:00
 */
public interface CostService extends IService<Cost> {

    String findAllCost(FindCostFrom costFrom);

    String addCostByAdmin(AddCostByAdmin cost);

    String deleteCostByIds(Ids ids);

    String editCostById(EditCostFromByAdmin editCostFrom);

    String getCostList();

}
