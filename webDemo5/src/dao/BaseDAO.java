package dao;

import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseDAO {
    public void update(Connection connection,String sql,Object...args) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for(int i=0;i< args.length;i++){
            preparedStatement.setObject(i+1,args[i]);
        }
        preparedStatement.execute();
    }
    public void add(Connection connection,String sql,Object object) throws SQLException, IllegalAccessException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            Object o = fields[i].get(object);
            preparedStatement.setObject(i+1,o);
        }
        preparedStatement.execute();
    }
    public int countQuery(Connection connection,String sql,Object...args) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int count = 0;
        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i+1,args[i]);
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        if (resultSet.next()){
            count=resultSet.getInt(1);
        }
        JDBCUtils.closeResource(null,preparedStatement,resultSet);
        return count;
    }
    public <T> List<T> Query(Connection connection,Class<T> clazz,String sql,Object...args) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        List<T> list=new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i+1,args[i]);
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        while(resultSet.next()) {
            T t = clazz.getDeclaredConstructor().newInstance();
            for (int i = 0; i < columnCount; i++) {
                Object object = resultSet.getObject(i + 1);
                Field declaredField = t.getClass().getDeclaredField(metaData.getColumnLabel(i + 1));
                declaredField.setAccessible(true);
                declaredField.set(t, object);
            }
            list.add(t);
        }
        JDBCUtils.closeResource(null,preparedStatement,resultSet);
        return list;
    }

    @Test
    public void insertTest() throws SQLException, IOException, ClassNotFoundException {
        Connection connection = JDBCUtils.getConnection();
        String sql="insert into fruit(FruitName,Price,Repertory,Remark)value(?,?,?,?)";
//        update(connection,sql,"apple",5,20,"nothing");
        update(connection,sql,"watermelon",10,200,"sweet");
        update(connection,sql,"orange",6,21,"sour");
        update(connection,sql,"strawberry",15,200,"best");
        JDBCUtils.closeResource(connection,null,null);
    }
    @Test
    public void updateTest() throws SQLException, IOException, ClassNotFoundException {
        Connection connection = JDBCUtils.getConnection();
        String sql="update fruit set Repertory=? where FruitName=?";
        update(connection,sql,25,"watermelon");
        JDBCUtils.closeResource(connection,null,null);
    }
    @Test
    public void deleteTest() throws SQLException, IOException, ClassNotFoundException {
        Connection connection = JDBCUtils.getConnection();
        String sql1="insert into fruit(FruitName,Price,Repertory,Remark)value(?,?,?,?)";
        String sql2="delete from fruit where FruitName=?";
//        update(connection,sql1,"testFruit",10,10,"none");
        update(connection,sql2,"testFruit");
        JDBCUtils.closeResource(connection,null,null);
    }
    @Test
    public void queryTest() throws SQLException, IOException, ClassNotFoundException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Connection connection = JDBCUtils.getConnection();
        String sql="select FruitName fruitName,Price price,Repertory repertory,Remark remark from fruit where Price<?";
        for (Fruit fruit : Query(connection, Fruit.class, sql, 15)) {
            System.out.println(fruit);
        }
    }
    @Test
    public void addTest() throws SQLException, IOException, ClassNotFoundException, IllegalAccessException {
        Connection connection = JDBCUtils.getConnection();
        String sql="insert into fruit(FruitName,Price,Repertory,Remark)value(?,?,?,?)";
        Fruit fruit = new Fruit("banana", new BigDecimal(12), new BigDecimal(24), "I love it");
        add(connection,sql,fruit);
        JDBCUtils.closeResource(connection,null,null);
    }
    @Test
    public void complexQueryTest() throws SQLException, IOException, ClassNotFoundException {
        Connection connection = JDBCUtils.getConnection("jdbc.properties");
        String sql="select count(*) from fruit";
        int i = countQuery(connection, sql);
        System.out.println(i);
        JDBCUtils.closeResource(connection,null,null);
    }
}
class Fruit{
    private String fruitName;
    private BigDecimal price;
    private BigDecimal repertory;
    private String remark;

    public Fruit() {
    }

    public Fruit(String fruitName, BigDecimal price, BigDecimal repertory, String remark) {
        this.fruitName = fruitName;
        this.price = price;
        this.repertory = repertory;
        this.remark = remark;
    }

    public String getFruitName() {
        return fruitName;
    }

    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getRepertory() {
        return repertory;
    }

    public void setRepertory(BigDecimal repertory) {
        this.repertory = repertory;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "fruitName='" + fruitName + '\'' +
                ", price=" + price +
                ", repertory=" + repertory +
                ", remark='" + remark + '\'' +
                '}';
    }
}