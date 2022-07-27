package ServletTest;

import dao.JDBCUtils;
import org.testng.annotations.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class AddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String fname = req.getParameter("fname");
        String price = req.getParameter("price");
        String fcount = req.getParameter("fcount");
        String remark = req.getParameter("remark");

        FruitDAO fruitDAO = new FruitDAO();
        Connection connection = null;
        try {
//            connection = JDBCUtils.getConnection();
            connection = JDBCUtils.getConnection("jdbc.properties");
            fruitDAO.addFruit(connection, new Fruit(fname, new BigDecimal(price), new BigDecimal(fcount), remark));
            System.out.println("添加成功");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.closeResource(connection,null,null);
        }
    }
    @Test
    public void addTest(){
        FruitDAO fruitDAO = new FruitDAO();
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            fruitDAO.addFruit(connection, new Fruit("fname", new BigDecimal(10), new BigDecimal(20), "remark"));
            System.out.println("添加成功");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.closeResource(connection,null,null);
        }
    }
}
