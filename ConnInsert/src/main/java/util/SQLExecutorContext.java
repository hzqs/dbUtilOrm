package util;

/**
 * Created by hzq on 2017/12/2.
 */
public class SQLExecutorContext {
    private static ThreadLocal<SQLExecutor>local;
    //获取当前线程的SQLEcector
    public static SQLExecutor currentSQLExcector(){
        if(local.get()==null){
            local.set(new SQLExecutor(DBUtil.getConnection()));
        }
        return local.get();
    }

    //移除当前线程的SQLException
    public void close(){
        local.remove();
    }

}
