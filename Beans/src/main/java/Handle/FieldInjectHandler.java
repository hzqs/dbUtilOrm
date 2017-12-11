package Handle;

import factory.BeanFactory;
import myAnn.Inject;

import java.lang.reflect.Field;

/**
 * Created by hzq on 2017/12/11.
 */
public class FieldInjectHandler implements InjectHandler {
    @Override
    public void execute(Object target, Class<?> targetClass, BeanFactory factory) {
        //遍历当前类中所有的属性，
        for(Field field:targetClass.getDeclaredFields()){
            //判断是否是Inject注解类
            if(field.isAnnotationPresent(Inject.class)){
                //获取注解
                Inject annotation=field.getAnnotation(Inject.class);
                //根据注解属性的值，从容器获取bean实例
                Object property=factory.getBean(annotation.name());
                //如果是私有的，先打开访问的
                field.setAccessible(true);
                //给当前的field属性赋值（注入）
                 injectField(field,target,property);
            }
        }
    }

    //注入
    private void injectField(Field field,Object target,Object property){
       field.setAccessible(true);
        try {
            field.set(target,property);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Inject property.",e);
        }
    }




}
