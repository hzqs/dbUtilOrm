package factory;

import myAnn.Component;
import myAnn.Scope;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by hzq on 2017/12/7.
 */
public class BeanFactory {
    /**
     * 将构造方法传入的包名传递给ScanUtil进行扫描，并返回一个集合，循环遍历集合，
     将里面的完整类名进行类加载，并通过反射解析类上面定义的@Compoment和@Scope注解。（如果类上面没有使用@Scope注解，默认就为单例）然后构建BeanDefinition实例，并将这个实例存入prototype的map集合中，
     key为@Component的value属性的值，value为BeanDefinition实例。至此原型的map初始化完成。
     */
    //单例的容器（Singleton）
    private static Map<String,Object> singleton=new HashMap<String,Object>();
    //原始容器(prototype)
    private static Map<String,BeanDefinition> prototype=new HashMap<String,BeanDefinition>();

    public BeanFactory(String resoucePath){
        //scanUtil.scan(path)
        //先初始化原型容器
       // initPrototype(resoucePath);
        //初始化单例容器
        initSingleton();
    }
/*
    private void initPrototype(String pathName){
        //
        //List<String> classList = ScanUtil.scan(pathName);
        for(String cl:classList){
            Class<?> clazz = null;
            try {
                clazz = Class.forName(cl);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            setPrototype(clazz);
        }
    }
    */
    private static void setPrototype(Class<?> clazz){
        if(clazz.isAnnotationPresent(Component.class)){
            BeanDefinition df = setDefinition(clazz);
            prototype.put(clazz.getAnnotation(Component.class).value(),df);
        }
    }
    private static BeanDefinition setDefinition(Class<?> clazz){
        BeanDefinition definition = new BeanDefinition();
        definition.setId(clazz.getAnnotation(Component.class).value());
        definition.setClazz(clazz);
        definition.setScope(clazz.getAnnotation(Scope.class).value());
        return definition;
    }

    //初始化单例容器
    private void initSingleton(){
        for(String key:prototype.keySet()){
            BeanDefinition bdef=prototype.get(key);
            if("singleton".equals(bdef.getScope())){
                try {
                    singleton.put(key, bdef.getClazz().newInstance());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public Object getBean(String name){
        return getContainerBean(name);
    }

    public <T> T getBean(String name,Class<T> clazz){
        return (T)getContainerBean(name);
    }

    private Object getContainerBean(String name) {
        //获取作用域属性
        String scope = prototype.get(name).getScope();
        try {
            return ("singleton".equals(scope)) ? singleton.get(name) :
                   (prototype.get(name).getClazz()).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }



}


