package controller;

import factory.BeanFactory;
import services.Service;

/**
 * Created by hzq on 2017/12/7.
 */
public class Controller {
    public void controller(){
        BeanFactory factory=new BeanFactory("services.service");
        Service service=factory.getBean("TestService",Service.class);
         service.testService();
    }

    public static void main(String[] args) {
        new Controller().controller();
    }

}
