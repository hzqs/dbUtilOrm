import controller.UserController;
import factory.BeanFactory;
import model.Users;

/**
 * Created by hzq on 2017/12/8.
 */
public class Main {

    public static void main(String[] args) {
        Users users=new Users();
        //创建工厂，从容器工厂获取Controller
        //根据包名进行扫描
        BeanFactory factory=new BeanFactory("dao.TestDao");
        UserController controller=factory.getBean("controller",UserController.class);
        System.out.print(controller);
        controller.addUser(users);


    }





}
