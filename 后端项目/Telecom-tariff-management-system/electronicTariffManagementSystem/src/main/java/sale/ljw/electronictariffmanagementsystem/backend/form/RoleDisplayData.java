package sale.ljw.electronictariffmanagementsystem.backend.form;

import lombok.Data;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.ModuleInfo;

import java.util.ArrayList;

@Data
public class RoleDisplayData {

    private Integer roleId;

    private String name;

    private ArrayList<ModuleInfo> moduleInfos;

}
