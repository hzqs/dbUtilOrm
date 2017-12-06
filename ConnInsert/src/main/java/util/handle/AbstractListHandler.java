package util.handle;

import util.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractListHandler<T> implements ResultSetHandler<List<T>> {

    @Override
    public List<T> handle(ResultSet rs) throws SQLException, InstantiationException, IllegalAccessException {
        List<T> rows = new ArrayList<T>();
            while(rs.next()){
                rows.add(getRow(rs));
            }
            return rows;
    }
    protected abstract T getRow(ResultSet resultSet) throws SQLException, InstantiationException, IllegalAccessException;

}
