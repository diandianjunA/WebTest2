package Demo2;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/demo01")
public class Demo01Servlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = req.getServletContext();
        servletContext.setAttribute("uname","lili");
//        req.getSession().setAttribute("uname","lili");
//        req.setAttribute("uname","lili");
        resp.sendRedirect("demo02");
//        req.getRequestDispatcher("demo02").forward(req,resp);
    }
}
