package services;

import dao.TestDao;
import factory.BeanFactory;
import myAnn.Component;
import myAnn.Scope;

/**
 * Created by hzq on 2017/12/7.
 */
@Component("TestService")
@Scope("singleton")
public class Service {
     public void testService(){
         BeanFactory factory=new BeanFactory("dao.TestDao");
         TestDao testDao=factory.getBean("TestDao",TestDao.class);
         System.out.print("service");
     }


}
