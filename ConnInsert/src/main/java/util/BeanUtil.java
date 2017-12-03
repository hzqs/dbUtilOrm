package util;

import Ann.Column;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hzq on 2017/12/2.
 */
//将结果集转换成Bean对象

public class BeanUtil {
    private static Map<Class, Object> primitiveDefaults = new HashMap<Class, Object>();

    static{
        //各种类型的转换
     primitiveDefaults.put(Integer.TYPE,Integer.valueOf(0));
     primitiveDefaults.put(Short.TYPE,Short.valueOf((short) 0));
     primitiveDefaults.put(Byte.TYPE,Byte.valueOf((byte) 0));
     primitiveDefaults.put(Float.TYPE,Float.valueOf(0f));
     primitiveDefaults.put(Long.TYPE, Long.valueOf(0L));
     primitiveDefaults.put(Boolean.TYPE, Boolean.FALSE);
     primitiveDefaults.put(Character.TYPE, Character.valueOf((char) 0));

    }

    /**
     * 根据Class对象创建bean对象，并将结果集中的数据赋值给bean
     *
     * @param rs
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */

    public static<T> T createBean(ResultSet rs,Class<T> clazz){
        T t=null;
        try {
            t=clazz.newInstance();
            ResultSetMetaData md=rs.getMetaData();
            for(int i=1;i<=md.getColumnCount();i++){
                String columnName=md.getColumnLabel(i);
                setField(t,columnName,clazz,rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;

    }

    /*
    * 为bean对象赋值
    * */
public static void setField(Object bean,String columnName,Class clazz,ResultSet rs)throws Exception{
    Field[] fields=clazz.getDeclaredFields();

    for(Field f:fields){
        f.setAccessible(true);
      String tagName=f.isAnnotationPresent(Column.class)?
              f.getAnnotation(Column.class).value() : f.getName();
           if(tagName.equalsIgnoreCase(columnName)){


           }

    }


}





}
