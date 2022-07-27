package mvcServlet;

import Demo1Servlet.Fruit;
import biz.FruitService;
import biz.implement.FruitServiceImplement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class FruitController{
//    private ServletContext servletContext;
//
//    public void setServletContext(ServletContext servletContext) throws ServletException {
//        this.servletContext = servletContext;
//        super.init(servletContext);
//    }

    private FruitService fruitService=null;

    protected String index(HttpServletRequest req,String keyWord,Integer pageNo) throws ServletException, IOException, SQLException, ClassNotFoundException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        HttpSession session = req.getSession();
//        String pageNoStr = req.getParameter("pageNo");
//        int pageNo = 1;
//        if(pageNoStr!=null){
//            pageNo=Integer.parseInt(pageNoStr);
//        }66
        if(pageNo==null){
            pageNo=1;
        }
        session.setAttribute("pageNo",pageNo);
        List<Fruit> fruitList = fruitService.getFruitList(keyWord,pageNo);
        session.setAttribute("FruitList",fruitList);
        int pageCount=fruitService.getPageCount(keyWord);
        session.setAttribute("pageCount",pageCount);
//      super.processTemplate("index",req,resp);
        return "index";
    }
    protected String add(String fname,BigDecimal price,BigDecimal fcount,String remark) throws IOException, SQLException, ClassNotFoundException {
        Fruit fruit = new Fruit(fname, price, fcount, remark);
        fruitService.addFruit(fruit);
//      resp.sendRedirect("fruit.do");
        return "redirect:fruit.do";
    }
    protected String delete(String fname) throws IOException, SQLException, ClassNotFoundException {
        fruitService.deleteFruitByName(fname);
//      resp.sendRedirect("fruit.do");
        return "redirect:fruit.do";
    }
    protected String edit(String fname,HttpServletRequest req) throws IOException, SQLException, ClassNotFoundException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if(fname!=null&&!"".equals(fname)){
            Fruit fruit = fruitService.getFruitByName(fname);
            req.setAttribute("fruit",fruit);
            //super.processTemplate("edit",req,resp);
            return "edit";
        }
        return "error";
    }
    protected String update(String fnamePre,String fname,BigDecimal price,BigDecimal fcount,String remark) throws SQLException, IOException, ClassNotFoundException {
        Fruit fruit = new Fruit(fname,price,fcount,remark);
        fruitService.updateFruit(fruit,fnamePre);
//      resp.sendRedirect("fruit.do");
        return "redirect:fruit.do";
    }
}
