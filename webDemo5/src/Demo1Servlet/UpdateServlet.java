package Demo1Servlet;

import dao.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/update.do")
public class UpdateServlet extends ViewBaseServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String fname = req.getParameter("fname");
        String fnamePre = req.getParameter("fnamePre");
        String priceStr = req.getParameter("price");
        String fcountStr = req.getParameter("fcount");
        String remark = req.getParameter("remark");
        BigDecimal price = new BigDecimal(priceStr);
        BigDecimal fcount = new BigDecimal(fcountStr);
        FruitDAO fruitDAO = new FruitDAO();
        Fruit fruit = new Fruit(fname,price,fcount,remark);
        try {
            Connection connection = JDBCUtils.getConnection("jdbc.properties");
            fruitDAO.updateFruitByName(connection,fruit,fnamePre);
            JDBCUtils.closeResource(connection,null,null);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect("index");
    }
}
