package util;

import java.sql.ResultSet;

/**
 * Created by hzq on 2017/12/3.
 */
public class RowProcessor {
    //数据行转换处理
    public static <T> T toBean(ResultSet rs,Class<T> type){
        return BeanUtil.createBean(rs,type);
    }
}
