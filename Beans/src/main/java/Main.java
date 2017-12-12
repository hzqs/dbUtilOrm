import factory.BeanFactory;
import services.UserService;

/**
 * Created by hzq on 2017/12/8.
 */
public class Main {

    public static void main(String[] args) {
        BeanFactory factory = new BeanFactory("org.framework.test");
        UserService service=factory.getBean("service");
        service.saveUser();
        factory.close();



    }





}
