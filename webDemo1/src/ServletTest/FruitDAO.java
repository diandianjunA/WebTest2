package ServletTest;

import dao.BaseDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class FruitDAO extends BaseDAO {
    public void addFruit(Connection connection, Fruit fruit) throws SQLException, IllegalAccessException {
        String sql="insert into fruit(FruitName,Price,Repertory,Remark)value(?,?,?,?)";
        add(connection,sql,fruit);
    }
}
