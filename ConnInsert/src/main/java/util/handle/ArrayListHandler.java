package util.handle;

import util.RowProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArrayListHandler extends AbstractListHandler<Object[]> {
    @Override
    protected Object[] getRow(ResultSet resultSet) throws SQLException, InstantiationException, IllegalAccessException {
        return RowProcessor.toArray(resultSet);
    }
}
