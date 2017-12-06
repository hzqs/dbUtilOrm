package util.handle;

import util.ResultSetHandler;
import util.RowProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by hzq on 2017/12/4.
 */
public class MapHandle implements ResultSetHandler<Map<String,Object>>{


    @Override
    public Map<String, Object> handle(ResultSet rs) throws SQLException, InstantiationException, IllegalAccessException {
        return rs.next()? RowProcessor.toMap(rs):null;
    }
}
