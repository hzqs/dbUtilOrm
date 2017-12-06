package util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
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
        Map<String,Object>map=new HashMap<String,Object>();
        ResultSetMetaData md=rs.getMetaData();
        for(int i=0;i<md.getColumnCount();i++){
            map.put(md.getColumnLabel(i),rs.getObject(i));
        }
        return map;

    }

    //转换成MapList类型。
/**
 * 将结果集存放Object数组
 *
 */

  public static Object[] toArray(ResultSet resultSet) throws SQLException {
      ResultSetMetaData metaData=resultSet.getMetaData();
      Object[] objects=new Object[metaData.getColumnCount()];
      for(int i=0;i<metaData.getColumnCount();i++){
          objects[i]=resultSet.getObject(i+1);
      }
      return objects;
  }



}
