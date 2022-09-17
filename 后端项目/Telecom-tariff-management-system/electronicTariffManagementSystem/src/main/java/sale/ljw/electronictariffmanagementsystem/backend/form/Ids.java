package sale.ljw.electronictariffmanagementsystem.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.ArrayList;

@Data
@ApiModel(value = "删除id列表")
public class Ids {
    @ApiModelProperty(value = "id列表")
    @Size(min = 1)
    private ArrayList<String> ids;
}
