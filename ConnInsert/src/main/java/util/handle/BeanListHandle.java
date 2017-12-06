package util.handle;

import util.RowProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hzq on 2017/12/4.
 */
public class BeanListHandle extends AbstractListHandler{

    private Class<?> clazz;
    public BeanListHandle(Class<?> clazz){
        this.clazz = clazz;
    }

    @Override
    protected Object getRow(ResultSet resultSet) throws SQLException, InstantiationException, IllegalAccessException {
        return RowProcessor.toBean(resultSet,clazz);
    }
}
