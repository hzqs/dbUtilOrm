package services;

import dao.UserDao;
import model.Users;
import myAnn.Component;
import myAnn.Scope;

/**
 * Created by hzq on 2017/12/8.
 */
@Component("userService")
@Scope("prototype")
public class UserServiceimpi implements UserService {

    private UserDao dao;


    //set方法注入

    public void setDao(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public void saveUser(Users user) {
        dao.save(user);
    }


}
