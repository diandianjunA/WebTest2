package dao;

import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtils {
    public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
        String user = "root";
        String url = "jdbc:mysql://localhost:3306/fruit";
        String password = "Xjs02220012";
        String driverClass = "com.mysql.cj.jdbc.Driver";
        Class.forName(driverClass);
        return DriverManager.getConnection(url, user, password);
    }
    public static Connection getConnection(String propertiesUrl) throws IOException, ClassNotFoundException, SQLException {
        InputStream resourceAsStream = JDBCUtils.class.getClassLoader().getResourceAsStream(propertiesUrl);
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        String user = properties.getProperty("user");
        String url = properties.getProperty("url");
        String password = properties.getProperty("password");
        String driverClass = properties.getProperty("DriverClass");
        Class.forName(driverClass);
        return DriverManager.getConnection(url, user, password);
    }
    public static void closeResource(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet){
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Test
    public void testUtils() throws SQLException, IOException, ClassNotFoundException {
        Connection connection=getConnection();
        System.out.println(connection);
        closeResource(connection,null,null);
    }
}
