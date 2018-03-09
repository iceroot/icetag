package com.icexxx.icetag;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class IcePathTag extends SimpleTagSupport {
    private String name;

    @Override
    public void doTag() throws JspException, IOException {
        if (this.name == null || "".equals(this.name.trim())) {
            this.name = "basePath";
        }
        JspContext jspContext = getJspContext();
        initBasePath(jspContext, this.name);
    }

    private void initBasePath(JspContext jspContext, String name) {
        if (jspContext != null) {
            Class<? extends JspContext> class1 = jspContext.getClass();
            Field declaredField = IceReflectUtil.getField(class1, "request");
            Object request = null;
            try {
                declaredField.setAccessible(true);
                request = declaredField.get(jspContext);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            Class<? extends Object> class2 = request.getClass();
            if ("org.apache.catalina.core.ApplicationHttpRequest".equals(class2.getName())) {
                Field declaredFieldApp = null;
                declaredFieldApp = IceReflectUtil.getField(class2, "request");
                try {
                    declaredFieldApp.setAccessible(true);
                    request = declaredFieldApp.get(request);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                class2 = request.getClass();
            }
            Method method = null;
            try {
                method = class2.getMethod("setAttribute", String.class, Object.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
            method.setAccessible(true);
            String scheme = null;
            String serverName = null;
            String serverPort = null;
            String contextPath = null;
            String localAddr = null;
            try {
                scheme = IceReflectUtil.invokeString(request, "getScheme");
                serverName = IceReflectUtil.invokeString(request, "getServerName");
                serverPort = IceReflectUtil.invokeString(request, "getServerPort");
                contextPath = IceReflectUtil.invokeString(request, "getContextPath");
                localAddr = IceReflectUtil.invokeString(request, "getLocalAddr");
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            } catch (SecurityException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (IllegalArgumentException e1) {
                e1.printStackTrace();
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            }
            StringBuilder url = new StringBuilder();
            url.append(scheme);
            url.append("://");
            if (serverName == null || !serverName.contains(".") && !"localhost".equals(serverName)) {
                url.append(localAddr);
            } else {
                url.append(serverName);
            }
            if (!"https".equals(scheme)) {
                url.append(":");
                url.append(serverPort);
            }
            url.append(contextPath);
            try {
                IceReflectUtil.invoke(request, "setAttribute", name, url);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
