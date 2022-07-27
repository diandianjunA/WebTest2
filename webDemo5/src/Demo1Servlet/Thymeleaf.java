package Demo1Servlet;

import dao.JDBCUtils;
import org.thymeleaf.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/index")
public class Thymeleaf extends ViewBaseServlet {
    Integer pageNo=1;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        String oper = req.getParameter("oper");
        String keyWord=null;
        if(!StringUtils.isEmpty(oper)&&"search".equals(oper)){
            //说明是单击表查询发送过来的请求
            keyWord = req.getParameter("keyWord");
        }
        String pageNoStr = req.getParameter("pageNo");
        if(pageNoStr!=null){
            pageNo=Integer.parseInt(pageNoStr);
        }
        session.setAttribute("pageNo",pageNo);
        FruitDAO fruitDAO = new FruitDAO();
        try {
            Connection connection = JDBCUtils.getConnection("jdbc.properties");
            List<Fruit> fruitList = fruitDAO.getFruitList(connection,pageNo,keyWord);
            session.setAttribute("FruitList",fruitList);
            int fruitCount = fruitDAO.getFruitCount(connection,keyWord);
            int pageCount=(fruitCount+5-1)/5;
            session.setAttribute("pageCount",pageCount);
            super.processTemplate("index",req,resp);
        } catch (ClassNotFoundException e) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
