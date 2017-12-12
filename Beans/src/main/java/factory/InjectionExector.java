package factory;

import Handle.InjectHandler;

import java.util.Iterator;
import java.util.ServiceLoader;

public class InjectionExector {
    //创建一个迭代器用于存放injectinFactory的实现类
    private Iterator<InjectHandler> it;
    public InjectionExector() {
        it = ServiceLoader.load(InjectHandler.class).iterator();

    }
    //循环执行迭代器里的类
    public static void exector(BeanFactory factory, Class<?> clazz,Object bean){
        Iterator<InjectHandler> it=new InjectionExector().it;
        while (it.hasNext()){
            it.next().execute(bean,clazz,factory);
        }
    }


}
