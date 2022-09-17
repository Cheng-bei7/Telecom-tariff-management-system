package sale.ljw.electronictariffmanagementsystem.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName admin_info
 */
@TableName(value = "admin_info")
@Data
public class AdminInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer adminId;
    /**
     *
     */
    private String adminCode;
    /**
     *
     */
    private String password;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private String telephone;
    /**
     *
     */
    private String email;
    /**
     *
     */
    private Date enrolldate;
}