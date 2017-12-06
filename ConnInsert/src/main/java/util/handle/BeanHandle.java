package util.handle;

import util.ResultSetHandler;
import util.RowProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hzq on 2017/12/3.
 */
public class BeanHandle<T> implements ResultSetHandler<T>{

    private Class<T> beanClazz;

    public BeanHandle(Class<T> beanClazz){
        this.beanClazz=beanClazz;
    }

    @Override
    public T handle(ResultSet rs) throws SQLException, InstantiationException, IllegalAccessException {
        return rs.next()? RowProcessor.toBean(rs,beanClazz):null;
    }
}
