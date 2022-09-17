package sale.ljw.electronictariffmanagementsystem.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @TableName cost
 */
@TableName(value = "cost")
@Data
public class Cost implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableId(type = IdType.AUTO, value = "cost_id")
    private Integer costId;
    /**
     *
     */
    @TableField(value = "name")
    private String name;
    /**
     *
     */
    @TableField(value = "base_duration")
    private Integer baseDuration;
    /**
     *
     */
    @TableField(value = "base_cost")
    private BigDecimal baseCost;
    /**
     *
     */
    @TableField(value = "unit_cost")
    private BigDecimal unitCost;
    /**
     *
     */
    @TableField(value = "status")
    private String status;
    /**
     *
     */
    @TableField(value = "descr")
    private String descr;
    /**
     *
     */
    @TableField(value = "creatime")
    private Date creatime;
    /**
     *
     */
    @TableField(value = "startime")
    private Date startime;
    /**
     *
     */
    @TableField(value = "cost_type")
    private String costType;
}