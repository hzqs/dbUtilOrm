package factory;


import myAnn.Component;
import myAnn.Scope;
import util.ScanUtil;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanFactory {
    public BeanFactory(String pathName) {
        initPrototype(pathName);
        initSingleton();
    }

    //单例的容器
    private static Map<String, Object> singleton = new HashMap<String, Object>();
    //原型的容器
    private static Map<String, BeanDefinition> prototype = new HashMap<String, BeanDefinition>();

    /**
     * 初始化原型工厂
     *
     * @param pathName
     * @throws Exception
     */
    private void initPrototype(String pathName) {
        List<String> classList = (List<String>) ScanUtil.scan(pathName);
        for (String cl : classList) {
            Class<?> clazz = null;
            try {
                clazz = Class.forName(cl);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            setPrototype(clazz);
        }
    }
    //put进原型容器
    private static void setPrototype(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Component.class)) {
            BeanDefinition df = setDefinition(clazz);
            prototype.put(clazz.getAnnotation(Component.class).value(), df);
        }
    }

    //设置bean描述
    private static BeanDefinition setDefinition(Class<?> clazz) {
        BeanDefinition definition = new BeanDefinition();
        definition.setClazz(clazz);
        definition.setScope(clazz.getAnnotation(Scope.class).value());
        definition.setInitMethod(setInitMethod(clazz));
        definition.setDestroyMethod(setDestroyMethod(clazz));
        return definition;
    }
    public static Method setInitMethod(Class<?> clazz){
        Method[] methods = clazz.getMethods();
        Method m = null;
        for(Method ms:methods){
            if(ms.isAnnotationPresent(PostConstruct.class)){
                m = ms;
            }
        }
        return m;
    }
    public static Method setDestroyMethod(Class<?> clazz){
        Method[] methods = clazz.getMethods();
        Method m = null;
        for(Method ms:methods){
            if(ms.isAnnotationPresent(PreDestroy.class)){
                m=ms;
            }
        }
        return m;
    }


    /**
     * 初始化单例的容器
     */
    private void initSingleton() {
        for (String key : prototype.keySet()) {
            BeanDefinition def = prototype.get(key);
            if ("singleton".equals(def.getScope())) {
                try {
                    singleton.put(key, createBean(def));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public void close(){
        executeDestroyMethods();
        singleton.clear();
        prototype.clear();
    }
    private void executeDestroyMethods(){
        for(String p:prototype.keySet()){
            BeanDefinition definition = prototype.get(p);
            Object s=singleton.get(p);
            if(s!=null){
                try {
                    definition.getDestroyMethod().invoke(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }

        }
    }

    public <T> T getBean(String name) {
        return (T) getContainerBean(name);
    }

    public <T> T getBean(String name, Class<T> clazz) {
        return (T) getContainerBean(name);
    }
    /**
     * 根据id获取实例对象
     * @param id
     * @return
     */
    private Object getContainerBean(String id){
        Object bean = singleton.get(id);
        if(bean == null){
            bean = getPrototype(id);
        }

        return bean;
    }

    /**
     * 获取原型实例
     * @param id
     * @return
     */
    private Object getPrototype(String id){
        //判断prototype集合中是否存在id键
        if(prototype.containsKey(id)){
            return createBean(prototype.get(id));
        }
        throw new RuntimeException();
    }


    /**
     *  创建实例并执行注入
     * @param
     * @return
     */
    private Object createBean(BeanDefinition definition){
        try {
            Object instance = definition.getClazz().newInstance();
            //初始化执行init
            exectorInit(definition,instance);
            //执行注入
            setinject(instance, definition.getClazz());
            return instance;
        } catch (InstantiationException e) {
            throw new RuntimeException("Create bean fail.");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Create bean fail.");
        }
    }
    //初始化执行init
    private void exectorInit(BeanDefinition definition,Object instance){
        Method method = definition.getInitMethod();
        try {
            if(method!=null){
                method.invoke(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //通过迭代器执行方法和字段的注入
    private void setinject(Object bean, Class<?> clazz){
        InjectionExector.exector(this,clazz,bean);
    }

}
