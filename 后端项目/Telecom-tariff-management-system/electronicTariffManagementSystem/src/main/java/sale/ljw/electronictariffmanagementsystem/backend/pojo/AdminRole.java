package sale.ljw.electronictariffmanagementsystem.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName admin_role
 */
@TableName(value = "admin_role")
@Data
public class AdminRole implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableId
    private Integer adminId;
    /**
     *
     */
    @TableField(value = "role_id")
    private Integer roleId;
}