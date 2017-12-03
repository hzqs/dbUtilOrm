package util;

import java.sql.*;

/**
 * Created by hzq on 2017/12/2.
 */
public class SQLExecutor {
    /*
    * connection连接管理类
    * */
    private Connection connection;//连接类
    private boolean autoClose=true;//是否自动关闭
    //单例模式

    public SQLExecutor(Connection connection){
        this.connection=connection;
    }

    /*
    * 开启服务
    * */
    public void beginTransaction(){
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        autoClose=false;
    }

    //提交事务
    public void commit() throws SQLException {
        try {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
        }finally {
            {
             close();
            }
        }
    }

    //回滚事务
    public void rollback(){
        try {
            connection.rollback();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //连接异常处理
    public <T> T executeQuery(String sql, ResultSetHandler<T> handler , Object... args) throws SQLException {
        if(connection==null){
            throw new SQLException("Null connection");
        }
        if(sql==null){
            close();
            throw  new SQLException("Null SQL statement");
        }


        PreparedStatement ps=connection.prepareStatement(sql);
        setParams(args,ps);
        ResultSet rs=ps.executeQuery();
        T t=handler.hadle(rs);
        return t;
    }

   /*
   * 执行DML语句,返回影响行数
   * */

   public int executeUpdate(String sql,Object...args) throws SQLException {
       if(connection==null){
           throw  new SQLException("Null sql.");
       }
       if(sql==null) {
           throw new SQLException("Null sql to statement");
       }
       int row=0;
       PreparedStatement ps=null;
       ps=connection.prepareStatement(sql);
       setParams(args,ps);
       row=ps.executeUpdate();
       close(ps);
       if(autoClose){
          close();
       }
       return row;
   }

/*
替换参数
* */
private void setParams(Object[] params, PreparedStatement ps) {
    try {
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
    } catch (SQLException e) {
        throw new RuntimeException("Exception in setParams.");
    }
}



//关闭结果集
  private void close(ResultSet rs)throws  SQLException{
       rs.close();
  }

  //关闭连接
  private void close(Statement st)throws SQLException{
       st.close();
  }
  //关闭连接
  private void close()throws SQLException{
       if(connection!=null&&!connection.isClosed()){
           connection.close();
       }
  }

}
