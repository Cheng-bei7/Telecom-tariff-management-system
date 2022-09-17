package sale.ljw.electronictariffmanagementsystem.common.sercurity.intercept;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sale.ljw.electronictariffmanagementsystem.backend.dao.AdminRoleMapper;
import sale.ljw.electronictariffmanagementsystem.backend.dao.PermissionurlMapper;
import sale.ljw.electronictariffmanagementsystem.backend.dao.RoleModuleMapper;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.AdminRole;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.Permissionurl;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.RoleModule;
import sale.ljw.electronictariffmanagementsystem.common.sercurity.utils.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class JudgmentOfAuthority {
    @Autowired
    private PermissionurlMapper permissionurlMapper;
    @Autowired
    private RoleModuleMapper roleModuleMapper;
    @Autowired
    private AdminRoleMapper adminRoleMapper;

    public Boolean findPermission(String token, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //解析token中的管理员id
        Integer adminId = JwtUtils.parseJWT(token);
        //通过管理员id查找管理员权限id
        QueryWrapper<AdminRole> queryWrapper_adminRole=new QueryWrapper<>();
        queryWrapper_adminRole.eq("admin_id",adminId).select("role_id");
        List<AdminRole> adminRoles = adminRoleMapper.selectList(queryWrapper_adminRole);
        //查询当前的url的权限列表
        String substring = request.getRequestURI().substring(18);
        if (substring.split("/").length >= 4) {
            substring = substring.substring(0, substring.lastIndexOf("/"));
        }
        QueryWrapper<Permissionurl> queryWrapper_Permission = new QueryWrapper<>();
        queryWrapper_Permission.eq("url", substring).select("coding");
        List<Permissionurl> permissionUrls = permissionurlMapper.selectList(queryWrapper_Permission);
        QueryWrapper<RoleModule> queryWrapper = new QueryWrapper<>();
        for (Permissionurl permissionUrl : permissionUrls) {
            for (AdminRole adminRole : adminRoles) {
                queryWrapper.eq("module_id", permissionUrl.getCoding()).eq("role_id", adminRole.getRoleId());
                Long aLong = roleModuleMapper.selectCount(queryWrapper);
                if (aLong != 0) {
                    return true;
                }
            }
        }
        handleFalseResponse(response);
        return false;
    }


    private void handleFalseResponse(HttpServletResponse response) throws Exception {
        response.setStatus(503);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"insufficientPermissions\":\"false\"}");
        response.getWriter().flush();
    }
}
