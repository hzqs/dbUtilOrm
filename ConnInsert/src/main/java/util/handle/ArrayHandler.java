package util.handle;

import util.ResultSetHandler;
import util.RowProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArrayHandler implements ResultSetHandler<Object[]> {
    @Override
    public Object[] handle(ResultSet rs) throws SQLException, InstantiationException, IllegalAccessException {
        return rs.next()? RowProcessor.toArray(rs):null;
    }
}
