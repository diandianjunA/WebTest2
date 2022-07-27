package mvcServlet;

import Demo1Servlet.ViewBaseServlet;
import io.BeanFactory;
import io.ClassPathXmlApplicationContext;
import org.thymeleaf.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {
    private BeanFactory beanFactory;

    @Override
    public void init() throws ServletException {
        super.init();
        beanFactory=new ClassPathXmlApplicationContext();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        servletPath = servletPath.substring(1);
        int i = servletPath.lastIndexOf(".do");
        servletPath=servletPath.substring(0,i);
        Object o = beanFactory.getBean(servletPath);
        String operator = req.getParameter("operator");
        if(StringUtils.isEmpty(operator)){
            operator="index";
        }
        try {
            Method[] declaredMethods = o.getClass().getDeclaredMethods();
            for (int j = 0; j < declaredMethods.length; j++) {
                if(operator.equals(declaredMethods[j].getName())){
                    //获取当前方法参数列表
                    Parameter[] parameters = declaredMethods[j].getParameters();
                    Object[] parameterValues = new Object[parameters.length];
                    for (int k = 0; k < parameterValues.length; k++) {
                        String parameterName = parameters[k].getName();
                        if("req".equals(parameterName)){
                            parameterValues[k]=req;
                        }else if("resp".equals(parameterName)){
                            parameterValues[k]=resp;
                        }else if("session".equals(parameterName)){
                            parameterValues[k]=req.getSession();
                        }else {
                            String Value = req.getParameter(parameterName);
                            if(Value !=null){
                                String typeName = parameters[k].getType().getName();
                                Class typeClass = Class.forName(typeName);
                                Object parameterValue = typeClass.getDeclaredConstructor(String.class).newInstance(Value);
                                parameterValues[k] = parameterValue;
                            }else {
                                parameterValues[k]=null;
                            }
                        }
                    }
                    declaredMethods[j].setAccessible(true);
                    Object returnObj = declaredMethods[j].invoke(o, parameterValues);
                    String returnStr=(String) returnObj;
                    if(returnStr.startsWith("redirect:")){
                        String redirectStr = returnStr.substring("redirect:".length());
                        resp.sendRedirect(redirectStr);
                    }else {
                        super.processTemplate(returnStr,req,resp);
                    }
                }
            }
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
