package com.czw.springboot.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.czw.springboot.entity.sysUser;
import com.czw.springboot.service.sysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;

@Component
public class JwtUtils {
    /**
     * @param userId
     * @param sign
     * @return 以userId作为载荷，获取一个token
     */
    public static String getToken(Integer userId,String sign){
        return JWT.create()
                .withClaim("sub",userId)
                .sign(Algorithm.HMAC256(sign));
    }

    @Autowired
    private sysUserService sysUserService;

    private static sysUserService STATIC_USER_SERVICE;

    @PostConstruct
    public void initLoad(){
        STATIC_USER_SERVICE = sysUserService;
    }


    public static sysUser getCurrentUser(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        String token = requestAttributes.getRequest().getHeader("token");
        Integer sub = JWT.decode(token).getClaim("sub").asInt();
        try {
            sysUser user = STATIC_USER_SERVICE.getById(sub);
            return user;
        }catch (Exception e){
            return null;
        }
    }
}
