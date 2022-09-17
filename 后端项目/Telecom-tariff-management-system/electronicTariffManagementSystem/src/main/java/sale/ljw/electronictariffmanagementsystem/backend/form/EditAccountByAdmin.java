package sale.ljw.electronictariffmanagementsystem.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@ApiModel(value = "编辑账户")
public class EditAccountByAdmin {
    @ApiModelProperty(value = "账户id")
    @NotNull(message = "账户id不得为空")
    private Integer accountId;
    @ApiModelProperty(value = "推荐人id")
    private String recommenderId;
    @ApiModelProperty(value = "登录名")
    @NotBlank(message = "登录名不得为空")
    private String loginName;
    @ApiModelProperty(value = "登陆密码")
    @NotBlank(message = "登陆密码不得空")
    private String loginPasswd;
    @ApiModelProperty(value = "当前状态")
    @NotBlank(message = "当前状态不得为空")
    @Length(max = 1, min = 1)
    @Pattern(regexp = "0|1", message = "当前状态格式错误")
    private String status;
    @ApiModelProperty(value = "真实姓名")
    @NotBlank(message = "真实姓名不得为空")
    private String realName;
    @ApiModelProperty(value = "身份证号")
    @NotBlank(message = "身份证号不得为空")
    @Pattern(regexp = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)", message = "身份证格式错误")
    private String idcardNo;
    @ApiModelProperty(value = "出生日期")
    private Date birthdate;
    @ApiModelProperty(value = "性别")
    @Pattern(regexp = "0|1", message = "性别格式错误")
    private String gender;
    @ApiModelProperty(value = "职业")
    private String occupation;
    @ApiModelProperty(value = "电话号码")
    @NotBlank(message = "电话号码不得为空")
    @Pattern(regexp = "^[1][3-9][0-9]{9}$", message = "电话格式错误")
    private String telephone;
    @ApiModelProperty(value = "电子邮件")
    @Pattern(regexp = "^([-_A-Za-z0-9.]+)@([_A-Za-z0-9]+\\.)+[A-Za-z0-9]{2,3}$", message = "电子邮件编码格式错误")
    private String email;
    @ApiModelProperty(value = "邮编地址")
    private String mailaddress;
    @ApiModelProperty(value = "邮编编码")
    @Pattern(regexp = "^[1-9]\\\\d{5}$", message = "邮编编码格式不正确")
    private String zipcode;
    @ApiModelProperty(value = "qq号")
    private String qq;
}
