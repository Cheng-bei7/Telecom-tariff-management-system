package sale.ljw.electronictariffmanagementsystem.common.sercurity.intercept;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import sale.ljw.electronictariffmanagementsystem.common.sercurity.utils.JwtUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class PermissionCheck implements HandlerInterceptor {

    private static final String OPTIONS = "OPTIONS";
    @Autowired
    JudgmentOfAuthority judgmentOfAuthority;
    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        if (OPTIONS.equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        if(request.getHeader("token")==null){
            handleFalseResponse(response);
            return false;
        }
        //第一重验证token
        if(JwtUtils.verify(request.getHeader("token"))){
            //验证url
            return judgmentOfAuthority.findPermission(request.getHeader("token"),request,response);
        }else {
            handleFalseResponse(response);
            return false;
        }
    }

    private void handleFalseResponse(HttpServletResponse response) throws Exception {
        response.setStatus(403);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"insufficientPermissions\":\"false\"}");
        response.getWriter().flush();
    }
}
