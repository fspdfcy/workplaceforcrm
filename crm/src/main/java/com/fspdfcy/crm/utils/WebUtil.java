package com.fspdfcy.crm.utils;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;

/**
 * Web工具类
 * @author fsp
 * @version v1.0
 * 创建时间: 2021/8/29
 */
public class WebUtil {
    private WebUtil() {}


    /**
     * 使用反射机制将业务逻辑代码封装
     * @param request HttpServletRequest请求,获得数据
     * @param obj 接收数据的参数
     */
    public static void makeRequestToObject(HttpServletRequest request, Object obj) {
        Class<?> aClass = obj.getClass();
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String filedName = names.nextElement();
            String methodName = "set" + filedName.toUpperCase().charAt(0) + filedName.substring(1);
            try {
                Method setMethod = aClass.getDeclaredMethod(methodName, String.class);
                setMethod.invoke(obj, request.getParameter(filedName));
            } catch (Exception e) {
                //e.printStackTrace(); 正常异常,无需打印
            }
        }
    }

    public static void makeRequestToMap(HttpServletRequest request, Map<String, Object> map) {
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String value = request.getParameter(name);
            map.put(name, value);
        }
    }
}

