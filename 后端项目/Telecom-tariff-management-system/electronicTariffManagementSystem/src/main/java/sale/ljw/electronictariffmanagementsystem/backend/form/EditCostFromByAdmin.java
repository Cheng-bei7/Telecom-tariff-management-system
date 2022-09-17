package sale.ljw.electronictariffmanagementsystem.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(value = "修改cost信息")
public class EditCostFromByAdmin {
    @ApiModelProperty(value = "资费id")
    @NotNull
    private Integer costId;
    @ApiModelProperty(value = "套餐名称")
    @NotBlank(message = "套餐名称不得为空")
    private String name;

    @ApiModelProperty(value = "基本时长")
    private Integer baseDuration;

    @ApiModelProperty(value = "基本费用")
    private BigDecimal baseCost;

    @ApiModelProperty(value = "单位费用")
    private BigDecimal unitCost;

    @ApiModelProperty(value = "状态")
    @NotBlank(message = "状态不得为空")
    @Length(max = 1)
    private String status;

    @ApiModelProperty(value = "基本信息")
    @NotBlank(message = "基本信息不得为空")
    private String descr;

    @ApiModelProperty(value = "开通时间")
    @NotNull(message = "开通时间不得为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startime;

    @ApiModelProperty(value = "套餐类型")
    @NotBlank(message = "costType不得为空")
    private String costType;
}
