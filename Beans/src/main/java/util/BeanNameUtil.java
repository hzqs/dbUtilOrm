package util;

/**
 * Created by hzq on 2017/12/11.
 */
public class BeanNameUtil {

    public static String toLowerBeanName(String beanName){
      String[] cname=beanName.split("\\.");
      beanName=cname[cname.length-1];
      beanName=beanName.substring(0,1).toLowerCase()
              +beanName.substring(1);
        return beanName;
    }




}
