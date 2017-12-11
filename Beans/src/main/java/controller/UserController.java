package controller;

import model.Users;
import myAnn.Component;
import services.UserService;

/**
 * Created by hzq on 2017/12/8.
 */

//@Component注解没有指定value时，默认的容器id就为类名,将首字母大写
@Component("controller")
public class UserController {

    //字段注入
    private UserService service;

    public void setService(UserService service){
        this.service=service;
    }

    public void addUser(Users user){
        System.out.print(service);
        service.saveUser(user);
    }



}
