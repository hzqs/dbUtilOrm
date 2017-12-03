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
public static Object columnConvert(String columnName, ResultSet rs,Class propType) throws SQLException {
    if(!propType.isPrimitive() && rs.getObject(columnName) == null){
        return null;
    }

    Object value = null;

    if(propType.equals(String.class)){

        value = rs.getString(columnName);

    }else if(propType.equals(Character.TYPE)
            || propType.equals(Character.class)){

        value = rs.getString(columnName).charAt(0);

    } else if(propType.equals(Double.TYPE)
            || propType.equals(Double.class)){

        value = rs.getDouble(columnName);

    }else if(propType.equals(Float.TYPE)
            || propType.equals(Float.class)){

        value = rs.getFloat(columnName);

    }else if(propType.equals(Long.TYPE)
            || propType.equals(Long.class)){

        value = rs.getLong(columnName);

    }else if(propType.equals(Integer.TYPE)
            || propType.equals(Integer.class)){

        value = rs.getInt(columnName);

    }else if(propType.equals(Short.TYPE)
            ||propType.equals(Short.class)){

        value = rs.getShort(columnName);

    }else if(propType.equals(Byte.TYPE)
            ||propType.equals(Byte.class)){

        value = rs.getByte(columnName);

    }else{
        //如果最后都不成立，就检查是不是日期类型
        value = rs.getObject(columnName);
        value = checkDateType(value , propType);
    }

    return value;
}

/*
* 处理日期类型
* */

private static Object checkDateType(Object value,Class type){
    if(value instanceof java.util.Date){
        if(type.equals(java.sql.Date.class)){
            long time=((java.util.Date)value).getTime();
            value=new java.sql.Date(time);
        }else if(type.equals(java.sql.Time.class)){
            value=new java.sql.Time(((java.util.Date) value).getTime());
        }else if(type.equals(java.sql.Timestamp.class)){
            Timestamp ts=(Timestamp) value;
            int nanos=ts.getNanos();
            value=new java.sql.Timestamp(ts.getTime());
            ((Timestamp) value).setNanos(nanos);
        }
    }

    return value;
}



}
