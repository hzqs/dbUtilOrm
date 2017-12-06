package util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by hzq on 2017/12/2.
 */
public class ColumnUtil {
    /*
    * 将结果集中的一列数据转换成与bean对象属性一致的类型。
    * */
    public static Object typeConvert(Class<?> propType,ResultSet resultSet,String columnName) throws SQLException {
        Object object = null;
        if(propType.equals(String.class)) {
            object = resultSet.getString(columnName);
        } else if(propType.equals(Integer.TYPE) || propType.equals(Integer.class)) {
            object = resultSet.getInt(columnName);
        } else if(propType.equals(Boolean.TYPE) || propType.equals(Boolean.class)) {
            object = resultSet.getBoolean(columnName);
        } else if(propType.equals(Long.TYPE) || propType.equals(Boolean.class)) {
            object = resultSet.getLong(columnName);
        } else if(propType.equals(Long.TYPE) || propType.equals(Long.class)) {
            object = resultSet.getDouble(columnName);
        } else if(propType.equals(Float.TYPE) || propType.equals(Long.class)) {
            object = resultSet.getFloat(columnName);
        } else if(propType.equals(Short.TYPE) || propType.equals(Short.class)) {
            object = resultSet.getShort(columnName);
        } else if(propType.equals(Byte.TYPE) || propType.equals(Byte.class)) {
            object = resultSet.getByte(columnName);

    }else{
        //如果最后都不成立，就检查是不是日期类型
        object = resultSet.getObject(columnName);
        object = checkDateType(object , propType);
    }
    return object;
}

/*
* 处理日期类型
* */

private static Object checkDateType(Object object,Class type){
    if(object instanceof java.util.Date){
        if(type.equals(java.sql.Date.class)){
            long time=((java.util.Date)object).getTime();
            object=new java.sql.Date(time);
        }else if(type.equals(java.sql.Time.class)){
            object=new java.sql.Time(((java.util.Date) object).getTime());
        }else if(type.equals(java.sql.Timestamp.class)){
            Timestamp ts=(Timestamp) object;
            int nanos=ts.getNanos();
            object=new java.sql.Timestamp(ts.getTime());
            ((Timestamp) object).setNanos(nanos);
        }
    }

    return object;
}



}
