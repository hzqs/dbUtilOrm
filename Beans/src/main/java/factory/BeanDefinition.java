package factory;

/**
 * Created by hzq on 2017/12/7.
 */
public class BeanDefinition {
    //bean的唯一标识
    private String id;
    //bean的完整类名
    private Class<?> clazz;
    //bean的创建方式
    private String scope = "singleton";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}

