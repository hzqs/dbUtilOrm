package dao;

import myAnn.Component;
import myAnn.Scope;

/**
 * Created by hzq on 2017/12/7.
 */
@Component("TestDao")
@Scope("singleton")
public class TestDao {

    public void testDao(){
        System.out.print("dao");
    }








}
