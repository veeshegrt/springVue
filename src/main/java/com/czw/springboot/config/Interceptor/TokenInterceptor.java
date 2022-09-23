package com.czw.springboot.config.Interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.czw.springboot.common.Constants;
import com.czw.springboot.entity.sysUser;
import com.czw.springboot.exception.BaseException;
import com.czw.springboot.service.sysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private sysUserService sysUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if(!(handler instanceof HandlerMethod)){
            //所有非controller方法，都放行
            return true;
        }
        if(!StringUtils.hasLength(token)){
            throw new BaseException(Constants.CODE_400,"未授权");
        }

        Integer userId = JWT.decode(token).getClaim("sub").asInt();
        //验证token
        sysUser user = sysUserService.getById(userId);
        if(user == null){
            throw new BaseException(Constants.CODE_400,"token授权用户不存在");
        }
        //用户密码加签验证
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            verifier.verify(token);
        }catch (JWTVerificationException e){
            throw new BaseException(Constants.CODE_400,"token无效！");
        }
        //验证成功
        return true;
    }
}
