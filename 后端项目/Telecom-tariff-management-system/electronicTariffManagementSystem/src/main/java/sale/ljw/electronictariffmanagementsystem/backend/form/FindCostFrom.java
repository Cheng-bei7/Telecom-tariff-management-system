package sale.ljw.electronictariffmanagementsystem.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "获取cost数据")
public class FindCostFrom {
    @ApiModelProperty(value = "当前页码")
    @NotNull(message = "页码不得为空")
    private Integer page;

    @ApiModelProperty(value = "套餐名称")
    private String packageName;
}
