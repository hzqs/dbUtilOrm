package util;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hzq on 2017/12/2.
 */
public interface ResultSetHandler<T> {
    /**
     *结果集转换，将查询的结果封装成不同的对象类型
     */

    T hadle(ResultSet rs) throws SQLException;




}
