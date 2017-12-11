package Handle;

import factory.BeanFactory;
import myAnn.Inject;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * Created by hzq on 2017/12/11.
 */
public class MethodInjectHandler implements InjectHandler {
    @Override
    public void execute(Object target, Class<?> targetClass, BeanFactory factory) {
        try {
            BeanInfo beanInfo= Introspector.getBeanInfo(targetClass,Object.class);
            PropertyDescriptor[] propertyDescriptors=beanInfo.getPropertyDescriptors();
            for(PropertyDescriptor propertyDescriptor:propertyDescriptors){
            targetClass.getDeclaredField(propertyDescriptor.getName());
            Method setMethod=propertyDescriptor.getWriteMethod();
                if(setMethod!=null&&setMethod.isAnnotationPresent(Inject.class)){
                    //获取注解
                    Inject annotation=setMethod.getAnnotation(Inject.class);
                    //根据注解获取bean实例
                    Object property=factory.getBean(annotation.name());
                    setMethod.invoke(target,property);
            }
            }
       } catch (Exception e) {
           new RuntimeException("method inject fail.",e);
        }
    }
}
