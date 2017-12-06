package dao;

import entity.Users;
import util.DBUtil;
import util.ResultSetHandler;
import util.SQLExecutor;
import util.handle.BeanHandle;

import java.sql.SQLException;

/**
 * Created by hzq on 2017/12/2.
 */
public class UserDao {

    //添加
    public int save(Users user){
        int row=0;
        String sql="insert into TbUser(u_name,u_account,u_pwd) values(?,?,?)";
        SQLExecutor se=new SQLExecutor(DBUtil.getConnection());
         se.beginTransaction();
        try {
            row=se.executeUpdate(sql,user.getUname(),user.getAccount(),user.getUpwd());
            se.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            se.rollback();
        }

        return row;

    }

    //根据用户Id查询出用户信息
    public Users findUserById(int id){
        String sql="select * from TbUser where u_no=?";
        Users user=null;
        SQLExecutor se=new SQLExecutor(DBUtil.getConnection());
        ResultSetHandler<Users> handler=new BeanHandle<>(Users.class);
        try {
            user=se.executeQuery(sql,handler,id);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
           e.printStackTrace();
        }
        return user;
    }

    //批量删除
    public int deleteList(int[] ids){
        int row=0;
        for(int i=0;i<ids.length;i++){
         delete(ids[i]);
         row++;
        }
        return row;
    }

    //删除
    public int delete(int id){
        int row=0;
        String sql="delete * from TbUser where u_no=?";
        SQLExecutor se=new SQLExecutor(DBUtil.getConnection());
        se.beginTransaction();
        try {
            row=se.executeUpdate(sql,id);
            se.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            se.rollback();
        }

        return row;
    }






    public static void main(String[] args) {

       /*
        Users user=new Users();
        user.setAccount("4633");
        user.setUname("jack");
        user.setUpwd("123456");
        int row=new UserDao().save(user);
        System.out.print(row);
*/
     Users user=new UserDao().findUserById(1);
     System.out.print(user.getUname());


    }


}
