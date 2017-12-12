package services;

import myAnn.Component;
import myAnn.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by hzq on 2017/12/8.
 */
@Component("userService")
@Scope("singleton")
public class UserServiceimpi implements UserService {

    @Override
    public void saveUser() {
        System.out.print("save users");
    }
    //初始化
    @PostConstruct
    public void init(){
        System.out.print("init ....");
    }

    //销毁
    @PreDestroy
    public void destroy(){
        System.out.print("destroy....");
    }




}
