package Demo1Servlet;

import dao.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/edit.do")
public class EditServlet extends ViewBaseServlet{
    FruitDAO fruitDAO = new FruitDAO();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection=null;
        try {
            String fname = req.getParameter("fname");
            connection = JDBCUtils.getConnection("jdbc.properties");
            if(fname!=null&&!"".equals(fname)){
                Fruit fruit = fruitDAO.getFruitByName(connection, fname);
                req.setAttribute("fruit",fruit);
                super.processTemplate("edit",req,resp);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.closeResource(connection,null,null);
        }
    }
}
