package cn.syl.intercepter;


import cn.syl.anno.PostRequest;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class ValidatePostInterceptor implements Interceptor {
    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        String methodName = invocation.getProxy().getMethod();
        Method currentMethod = invocation.getAction().getClass().getMethod(methodName);
        if (currentMethod != null && currentMethod.isAnnotationPresent(PostRequest.class)) {
            PostRequest annotation = currentMethod.getAnnotation(PostRequest.class);
            if (annotation.value()) {
                try {
                    HttpServletRequest request = ServletActionContext.getRequest();
                    HttpServletResponse response = ServletActionContext.getResponse();
                    String method = request.getMethod();
                    if (!"POST".equalsIgnoreCase(method)) {
                        response.setContentType("text/html;charset=utf-8");
                        response.getWriter().print("不支持的请求");
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return invocation.invoke();

    }

}
