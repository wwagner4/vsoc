package vsoc.util;

import java.io.IOException;
import java.net.URL;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.FactoryBean;

/**
 * Reads a serealized file into an application context.
 * 
 */
public class SerFactoryBean<T extends Object> implements FactoryBean<T> {

    private String resName = null;

    private T obj = null;

    public SerFactoryBean(String resName) {
        super();
        this.resName = resName;
    }

    public T getObject() throws Exception {
        if (this.obj == null) {
            this.obj = deserealize();
        }
        return this.obj;
    }

    @SuppressWarnings("unchecked")
		private T deserealize() throws IOException {
        URL url = getClass().getClassLoader().getResource(this.resName);
        if (url == null) {
            throw new BeanCreationException("Could not find resource " +
                    this.resName);
        }
        return (T) Serializer.current().deserialize(url.openStream());
    }

    public Class<?> getObjectType() {
        try {
            return getObject().getClass();
        } catch (Exception e) {
            throw new BeanCreationException("Could not get type of resource "
                    + this.resName);
        }
    }

    public boolean isSingleton() {
        return false;
    }

}
