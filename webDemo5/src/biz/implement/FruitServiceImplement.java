package biz.implement;

import Demo1Servlet.Fruit;
import Demo1Servlet.FruitDAO;
import biz.FruitService;
import dao.JDBCUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FruitServiceImplement implements FruitService {
    private FruitDAO fruitDAO=null;

    @Override
    public List<Fruit> getFruitList(String keyWord, Integer pageNo) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, ClassNotFoundException {
        Connection connection=JDBCUtils.getConnection("jdbc.properties");
        return fruitDAO.getFruitList(connection, pageNo, keyWord);
    }

    @Override
    public void addFruit(Fruit fruit) throws SQLException, IOException, ClassNotFoundException {
        Connection connection=JDBCUtils.getConnection("jdbc.properties");
        fruitDAO.addFruit(connection,fruit);
    }

    @Override
    public Fruit getFruitByName(String fname) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, ClassNotFoundException {
        Connection connection=JDBCUtils.getConnection("jdbc.properties");
        return fruitDAO.getFruitByName(connection,fname);
    }

    @Override
    public void deleteFruitByName(String fname) throws SQLException, IOException, ClassNotFoundException {
        Connection connection=JDBCUtils.getConnection("jdbc.properties");
        fruitDAO.deleteFruitByFruitName(connection,fname);
    }

    @Override
    public Integer getPageCount(String keyWord) throws SQLException, IOException, ClassNotFoundException {
        Connection connection=JDBCUtils.getConnection("jdbc.properties");
        int fruitCount = fruitDAO.getFruitCount(connection,keyWord);
        int pageCount=(fruitCount+5-1)/5;
        return pageCount;
    }

    @Override
    public void updateFruit(Fruit fruit,String fruitName) throws SQLException, IOException, ClassNotFoundException {
        Connection connection=JDBCUtils.getConnection("jdbc.properties");
        fruitDAO.updateFruitByName(connection,fruit,fruitName);
    }
}
