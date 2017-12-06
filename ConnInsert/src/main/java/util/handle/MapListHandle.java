package util.handle;

import util.RowProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by hzq on 2017/12/4.
 */
public class MapListHandle extends AbstractListHandler<Map<String,Object>> {


    @Override
    protected Map<String, Object> getRow(ResultSet resultSet) throws SQLException, InstantiationException, IllegalAccessException {
        return RowProcessor.toMap(resultSet);
    }
}
