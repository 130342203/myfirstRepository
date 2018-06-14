package com.ck.framework.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ron
 *         2016/5/26.
 */

public class CookieUtil {

    public static void addCookie(HttpServletResponse response, String name, String value, int age) {
        Cookie cookies = new Cookie(name, value);
        cookies.setPath("/");
        cookies.setMaxAge(age);
        response.addCookie(cookies);
    }

    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        if (cookieName != null) {
            Cookie cookie = getCookie(request, cookieName);
            if(cookie!=null){
                return cookie.getValue();
            }
        }
        return "";
    }

    public static Cookie getCookie(HttpServletRequest request, String cookieName){
        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;
        try {
            if (cookies != null && cookies.length > 0) {
                for (int i = 0; i < cookies.length; i++) {
                    cookie = cookies[i];
                    if (cookie.getName().equals(cookieName)) {
                        return cookie;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        if (cookieName != null) {
            Cookie cookie = getCookie(request, cookieName);
            if(cookie!=null){
                cookie.setMaxAge(0);//如果0，就说明立即删除
                cookie.setPath("/");//不要漏掉
                response.addCookie(cookie);
                return true;
            }
        }
        return false;
    }

}

