package Handle;

import factory.BeanFactory;

/**
 * Created by hzq on 2017/12/11.
 */
public interface InjectHandler {
    //抽象注入行为，便于不同的注入实现，例如属性注入或方法注入
    void execute(Object target, Class<?> targetClass, BeanFactory factory);
}
