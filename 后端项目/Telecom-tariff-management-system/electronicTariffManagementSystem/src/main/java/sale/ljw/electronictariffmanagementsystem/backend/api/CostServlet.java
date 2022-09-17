package sale.ljw.electronictariffmanagementsystem.backend.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.electronictariffmanagementsystem.backend.form.AddCostByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.form.EditCostFromByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.form.FindCostFrom;
import sale.ljw.electronictariffmanagementsystem.backend.form.Ids;
import sale.ljw.electronictariffmanagementsystem.backend.service.CostService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/costByAdmin")
public class CostServlet {
    @Autowired
    private CostService costService;

    @ApiOperation(value = "查找套餐数据")
    @PostMapping("/getAllCost")
    public String findAllCost(@RequestBody @Valid FindCostFrom costFrom) {
        return costService.findAllCost(costFrom);
    }

    @ApiOperation(value = "添加套餐")
    @PostMapping("/addCostByAdmin")
    public String addCostByAdmin(@RequestBody @Valid AddCostByAdmin cost) {
        return costService.addCostByAdmin(cost);
    }

    @ApiOperation(value = "删除基本信息")
    @PostMapping("/deleteCostByIds")
    public String deleteCostByIds(@RequestBody @Valid Ids ids) {
        return costService.deleteCostByIds(ids);
    }

    @ApiOperation(value = "修改cost信息")
    @PostMapping("/editCostById")
    public String editCostById(@RequestBody @Valid EditCostFromByAdmin editCostFrom) {
        return costService.editCostById(editCostFrom);
    }

    @ApiOperation(value = "cost下拉列表")
    @GetMapping("/getCostList")
    public String getCostList() {
        return costService.getCostList();
    }
}
