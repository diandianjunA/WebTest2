package Demo2;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/demo02")
public class Demo02Servlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Object uname = req.getAttribute("uname");
//        Object uname = req.getSession().getAttribute("uname");
        ServletContext servletContext = req.getServletContext();
        Object uname = servletContext.getAttribute("uname");
        System.out.println(uname);
    }
}
