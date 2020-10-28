package com.huatec.hiot_cloud.core.authorization.interceptor;

import com.huatec.hiot_cloud.core.authorization.annotation.Authorization;
import com.huatec.hiot_cloud.core.authorization.annotation.Permissions;
import com.huatec.hiot_cloud.core.authorization.manager.PermissionManager;
import com.huatec.hiot_cloud.core.authorization.manager.TokenManager;
import com.huatec.hiot_cloud.core.authorization.model.Role;
import com.huatec.hiot_cloud.core.authorization.model.TokenModel;
import com.huatec.hiot_cloud.core.autogenerator.entity.User;
import com.huatec.hiot_cloud.core.autogenerator.mapper.UserMapper;
import com.huatec.hiot_cloud.core.config.Constants;
import com.huatec.hiot_cloud.core.config.Result;
import com.huatec.hiot_cloud.core.config.ResultStatus;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * @ Created by liwenqiang on 2017/5/10 0010.
 * @ Description:自定义拦截器，判断此次请求是否有权限
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private TokenManager manager;

    @Autowired
    private UserMapper userDao;

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //从header中得到token
        String authorization = request.getHeader(Constants.AUTHORIZATION);

        //用户token验证
        TokenModel model = manager.getToken(authorization);
        //设备token验证
        Result result = manager.checkToken(model);

        //如果接口不需要token，直接返回
        if (method.getAnnotation(Authorization.class) == null) {
            return true;
        }

        //权限角色注解
        Permissions annotation = method.getAnnotation(Permissions.class);
        User user = null;
        if (model != null) {
            user = userDao.findById(model.getUUId());
        }

        //token验证成功且用户存在，验证用户角色权限
        if (result.getStatus() == ResultStatus.SUCCESS.getStatus() && model != null && user != null) {

            // 用户身份
            if (model.getTypeCode().equalsIgnoreCase(Constants.AUTHORIZATION_USER)) {
                // 判断用户角色权限
                // 权限规则:start
                // 没有加Permissions注解。默认不需要权限
                if (annotation == null) {
                    //如果token验证成功，将用户token对应的用户id存在request中，便于之后注入
                    request.setAttribute(Constants.CURRENT_USER_ID, model.getUUId());
                    return true;
                }

                /** admin:超级管理员权限：可以有所有权限*/
                if (PermissionManager.roleAdmin(user)) {
                    //如果token验证成功，将用户token对应的用户id存在request中，便于之后注入
                    request.setAttribute(Constants.CURRENT_USER_ID, model.getUUId());
                    return true;
                }

                /** developer:开发人员权限*/
                if (PermissionManager.roleDeveloper(user)
                        && annotation != null
                        && annotation.role().contains(Role.DEVELOPER)
                ) {
                    //如果token验证成功，将用户token对应的用户id存在request中，便于之后注入
                    request.setAttribute(Constants.CURRENT_USER_ID, model.getUUId());
                    return true;
                }

                /** user：普通用户权限*/
                if (PermissionManager.roleStaff(user)
                        && annotation != null
                        && annotation.role().contains(Role.STAFF)
                ) {

                    //如果token验证成功，将用户token对应的用户id存在request中，便于之后注入
                    request.setAttribute(Constants.CURRENT_USER_ID, model.getUUId());
                    return true;
                }

                /** 权限规则:end */
                /**设备身份*/
            } else if (model.getTypeCode().equalsIgnoreCase(Constants.AUTHORIZATION_DEVICE)) {
                // 预留
            }

            //用户的身份认证通过，但用户角色权限不符,返回403错误
            response.setCharacterEncoding("UTF-8");

            //设置返回格式
            response.setHeader("Content-Type", "application/json;charset=UTF-8");//这句话是解决乱码的
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "无权限访问此接口");
            return false;
        }

        //如果验证token失败，并且方法注明了Authorization，返回401错误
        response.setCharacterEncoding("UTF-8");

        //设置返回格式
        response.setHeader("Content-Type", "application/json;charset=UTF-8");//这句话是解决乱码的
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "身份验证失败，请求无效");


        // 和Result一样格式的错误信息
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(JSONObject.fromObject(result).toString());
        } catch (IOException ie) {
            ie.printStackTrace();
            out.write(JSONObject.fromObject(Result.error(ResultStatus.SERVER_ERROR)).toString());
        } finally {
            if (out != null) {
                out.close();
            }
        }

        //立即返回
        return false;
    }
}
