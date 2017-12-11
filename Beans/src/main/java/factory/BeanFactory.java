package factory;

import myAnn.Component;
import myAnn.Scope;
import util.BeanNameUtil;
import util.ScanUtil;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BeanFactory {
    /**
     * 存放bean的描述
     */
    final Map<String, BeanDefinition> definitionMap = new ConcurrentHashMap<>();

    /**
     * 存放单例bean的实例
     */
    final Map<String, Object> singletonMap = new ConcurrentHashMap<>();

    /**
     * 在构造方法中初始化并构建所有bean描述
     * 以及单例的bean
     *
     * @param path 扫描路径
     */
    public BeanFactory(String path) {
        Set<String> classNames = ScanUtil.scan(path);
        //初始化原型
        initDefinitionMap(classNames);
        //初始化单例
        initSingleton();
    }

    /**
     * 解析所有的类名并并检查容器中是否存在当前的Bean描述,如果不存在,构建Bean描述放入容器
     */
    private void initDefinitionMap(Set<String> classNames) {
        for (String className : classNames) {
            Class<?> beanClass = getClass(className);
            if (beanClass.isAnnotationPresent(Component.class)) {
                String beanName = createBeanName(beanClass);
                if (definitionMap.containsKey(beanName)) {
                    throw new RuntimeException(
                            "conflicts with existing, non-compatible bean definition of same name and class ["
                                    + beanClass + "]");
                } else {
                    definitionMap.put(beanName,
                            createBeanDefinition(beanClass));
                }
            }
        }
    }

    /**
     * 根据权限顶类名获取Class对象
     *
     * @param className
     * @return
     */
    private Class<?> getClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not find the class name " + className + " to build the description.");
        }
    }

    /**
     * beanName作为容器的key,当Component没有指定名字的时候,默认使用当前的类名作为bean的名字 并将类名的首字母转换成小写
     *
     * @param beanClass
     * @return
     */
    private String createBeanName(Class<?> beanClass) {
        Component annotation = beanClass.getAnnotation(Component.class);
        return ("".equals(annotation.value())) ? BeanNameUtil
                .toLowerBeanName(beanClass.getSimpleName()) : annotation
                .value();
    }

    /**
     * 构建bean描述定义,将bean的scope以及类名封装到BeanDefinition中
     * 创建的Bean描述会放入definitionMap的集合中保存
     * Bean的类名作为集合的key,而整个BeanDefinition对象作为value
     *
     * @param beanClass
     */
    private BeanDefinition createBeanDefinition(Class<?> beanClass) {
        // 创建BeanDefinition
        BeanDefinition definition = new BeanDefinition();
        //设置Bean的Class对象
        setBeanClass(definition, beanClass);
        //设置Bean的作用域
        setBeanScope(definition, beanClass);
        return definition;
    }

    /**
     * 为definition设置BeanClass 将当前Bean的class对象保存到描述对象中
     */
    private void setBeanClass(BeanDefinition definition, Class<?> beanClass) {
        definition.setBeanClass(beanClass);
    }

    /**
     * 为definition设置Bean的作用域
     * 如果bean的class上指定了Scope注解(也就是容器创建Bean的方式),一并保存Bean的作用域 否则Bean的作用默认创建方式将使用单例
     */
    private void setBeanScope(BeanDefinition definition, Class<?> beanClass) {
        String scope = (beanClass.isAnnotationPresent(Scope.class)) ? beanClass
                .getAnnotation(Scope.class).value() : "singleton";
        definition.setScope(scope);
    }

    /**
     * 初始化SINGLETON实例放入bean容器中 立即加载的方式会先初始化所有单例的Bean 并放入容器中
     */
    private void initSingleton() {
        for (String beanName : definitionMap.keySet()) {
            BeanDefinition definition = definitionMap.get(beanName);
            if ("singleton".equals(definition.getScope())) {
                Object bean = createBean(definition);
                // 将bean实例放入bean容器中
                singletonMap.put(beanName, bean);
            }
        }
    }

    /**
     * 构建bean实例,并初始化依赖关系（注入）
     *
     * @param definition
     * @return
     */
    private Object createBean(BeanDefinition definition) {
        // 构建Bean实例
        Object bean = newInstance(definition);
        // 为Bean对象执行依赖注入
        return DependencyInvoker.inject(bean,definition.getBeanClass(),this);
    }

    /**
     * 根据描述定义创建Bean实例
     * @param definition
     * @return
     */
    private Object newInstance(BeanDefinition definition) {
        try {
            return definition.getBeanClass().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Create bean instance fail.", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Create bean instance fail.", e);
        }
    }

    /**
     * 获取Bean描述容器
     *
     * @return
     */
    Map<String, BeanDefinition> getDefinitionMap() {
        return definitionMap;
    }

    /**
     * 获取bean实例
     *
     * @param beanName
     * @return
     */
    public Object getBean(String beanName) {
        return doGetBean(beanName);
    }

    /**
     * 获取bean实例(泛型)
     *
     * @param beanName
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getBean(String beanName, Class<T> clazz) {
        return (T) doGetBean(beanName);
    }

    /**
     * 从容器中获取Bean实例
     * 如果容器存在单例的Bean,则从容器中直接返回
     * 否则以原型的方式创建并返回
     */
    private Object doGetBean(String beanName) {
        // 如果容器存在单例的Bean,不为空则从容器中直接返回,否则以原型创建实力并返回
        Object bean = singletonMap.get(beanName);
        if (bean == null) {
            BeanDefinition definition = definitionMap.get(beanName);
            bean = createBean(definition);
        }
        return bean;
    }
}
