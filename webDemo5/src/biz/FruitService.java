package biz;

import Demo1Servlet.Fruit;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface FruitService {
    //获取指定页面的库存列表信息
    List<Fruit> getFruitList(String keyWord,Integer pageNo) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, ClassNotFoundException;
    //添加库存记录信息
    void addFruit(Fruit fruit) throws SQLException, IOException, ClassNotFoundException;
    //根据名字查看指定库存记录
    Fruit getFruitByName(String fname) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, ClassNotFoundException;
    //删除特定库存记录
    void deleteFruitByName(String fname) throws SQLException, IOException, ClassNotFoundException;
    //获取总页数
    Integer getPageCount(String keyWord) throws SQLException, IOException, ClassNotFoundException;
    void updateFruit(Fruit fruit,String fruitName) throws SQLException, IOException, ClassNotFoundException;
}
