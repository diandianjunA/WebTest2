package Demo1Servlet;

import dao.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/del.do")
public class DeleteServlet extends ViewBaseServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fname = req.getParameter("fname");
        try {
            Connection connection = JDBCUtils.getConnection("jdbc.properties");
            FruitDAO fruitDAO = new FruitDAO();
            fruitDAO.deleteFruitByFruitName(connection,fname);
            JDBCUtils.closeResource(connection,null,null);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect("index");
    }
}
