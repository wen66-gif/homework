package com.fifth.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception{
        System.out.println("url："+request.getRequestURL());
        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        // 获取请求头token
        String token = request.getHeader("Authorization");
        System.out.println(token);
        if(token != null){
            // 验证token
            boolean result = TokenUtil.verify(token);
            if(result){
                // 将用户账号及用户名保存到CurrentUser
                CurrentUser.setCurrentUserId(TokenUtil.parseJWT(token).get("userId").toString());
                CurrentUser.setCurrentUserName(TokenUtil.parseJWT(token).get("userName").toString());
                CurrentUser.setCurrentUserRole(Integer.parseInt(TokenUtil.parseJWT(token).get("userRole").toString()) );
                System.out.println("通过拦截器");
                return true;
            }
            else {
                JSONObject json = new JSONObject();
                json.put("msg","token失效");
                json.put("code","401");
                response.getWriter().append(json.toJSONString());
                System.out.println("认证失败，未通过拦截器");
                return false;
            }
        }

        try{
            JSONObject json = new JSONObject();
            json.put("msg","token不存在");
            json.put("code","402");
            response.getWriter().append(json.toJSONString());
            System.out.println("认证失败，未通过拦截器");
        }catch (Exception e){
            e.printStackTrace();
            response.sendError(500);
            return false;
        }
        return false;
    }

}
