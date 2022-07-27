package Demo1Servlet;

import dao.BaseDAO;
import dao.JDBCUtils;
import org.testng.annotations.Test;
import org.thymeleaf.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class FruitDAO extends BaseDAO {
    public List<Fruit> getFruitList(Connection connection) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String sql="select FruitName fruitName,Price price,Repertory repertory,Remark remark from fruit";
        List<Fruit> query = Query(connection, Fruit.class, sql);
        JDBCUtils.closeResource(connection,null,null);
        return query;
    }
    public Fruit getFruitByName(Connection connection,String fruitName) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String sql="select FruitName fruitName,Price price,Repertory repertory,Remark remark from fruit where FruitName=?";
        List<Fruit> query = Query(connection, Fruit.class, sql, fruitName);
        JDBCUtils.closeResource(connection,null,null);
        return query.get(0);
    }
    public void updateFruitByName(Connection connection,Fruit fruit,String fruitName) throws SQLException {
        String sql="update fruit set FruitName=?,Price=?,Repertory=?,Remark=? where FruitName=?";
        update(connection,sql,fruit.fruitName,fruit.price,fruit.repertory,fruit.remark,fruitName);
        JDBCUtils.closeResource(connection,null,null);
    }
    public void deleteFruitByFruitName(Connection connection,String fruitName) throws SQLException {
        String sql="delete from fruit where FruitName=?";
        update(connection,sql,fruitName);
        JDBCUtils.closeResource(connection,null,null);
    }
    public void addFruit(Connection connection,Fruit fruit) throws SQLException {
        String sql="insert into fruit(FruitName,Price,Repertory,Remark)values(?,?,?,?)";
        update(connection,sql,fruit.fruitName,fruit.price,fruit.repertory,fruit.remark);
        JDBCUtils.closeResource(connection,null,null);
    }
    public List<Fruit> getFruitList(Connection connection, Integer pageNo) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String sql="select FruitName fruitName,Price price,Repertory repertory,Remark remark from fruit limit ?,5";
        List<Fruit> query = Query(connection, Fruit.class, sql, (pageNo-1)*5);
        JDBCUtils.closeResource(connection,null,null);
        return query;
    }

    public int getFruitCount(Connection connection) throws SQLException {
        String sql="select count(*) from fruit";
        int i = countQuery(connection,sql);
        JDBCUtils.closeResource(connection,null,null);
        return i;
    }
    public List<Fruit> getFruitList(Connection connection, Integer pageNo,String keyWord) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<Fruit> query = null;
        if (StringUtils.isEmpty(keyWord)) {
            String sql = "select FruitName fruitName,Price price,Repertory repertory,Remark remark from fruit limit ?,5";
            query = Query(connection, Fruit.class, sql, (pageNo - 1) * 5);
        }else{
            String sql="select FruitName fruitName,Price price,Repertory repertory,Remark remark from fruit where FruitName like ? limit ?,5";
            query = Query(connection, Fruit.class, sql, "%" + keyWord + "%", (pageNo - 1) * 5);
        }
        JDBCUtils.closeResource(connection,null,null);
        return query;
    }
    public int getFruitCount(Connection connection,String keyWord) throws SQLException {
        if(StringUtils.isEmpty(keyWord)){
            String sql="select count(*) from fruit";
            int i = countQuery(connection,sql);
            JDBCUtils.closeResource(connection,null,null);
            return i;
        }else{
            String sql="select count(*) from fruit where FruitName like ?";
            int i = countQuery(connection, sql, "%" + keyWord + "%");
            JDBCUtils.closeResource(connection,null,null);
            return i;
        }
    }
}
