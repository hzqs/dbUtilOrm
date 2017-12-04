package util.handle;

import util.ResultSetHandler;
import util.RowProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by hzq on 2017/12/4.
 */
public class MapHandle implements ResultSetHandler{

    @Override
    public Map hadle(ResultSet rs) throws SQLException {
        return rs.next()? RowProcessor.toMap(rs):null;
    }

}
