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

@WebServlet("/add.do")
public class AddServlet extends ViewBaseServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String fname = req.getParameter("fname");
        String priceStr = req.getParameter("price");
        BigDecimal price = new BigDecimal(priceStr);
        String fcountStr = req.getParameter("fcount");
        BigDecimal fcount = new BigDecimal(fcountStr);
        String remark = req.getParameter("remark");
        Fruit fruit = new Fruit(fname, price, price, remark);
        FruitDAO fruitDAO = new FruitDAO();
        try {
            Connection connection = JDBCUtils.getConnection("jdbc.properties");
            fruitDAO.addFruit(connection,fruit);
            JDBCUtils.closeResource(connection,null,null);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect("index");
    }
}
