package util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by hzq on 2017/12/3.
 */
public class RowProcessor {
    //数据行转换处理
    public static <T> T toBean(ResultSet rs,Class<T> type){
        return BeanUtil.createBean(rs,type);
    }

    //转换成map类型
    public static Map<String , Object> toMap(ResultSet rs) throws SQLException {
       Map map=null;
        ResultSetMetaData md=rs.getMetaData();
        for(int i=0;i<md.getColumnCount();i++){
            String name=md.getColumnLabel(i);
            map.put(i,name);
        }
        return map;

    }

    //转换成MapList类型。

   public static List<Map>  ListMap(ResultSet rs) {
       List list=null;





        return list;
    }







}
