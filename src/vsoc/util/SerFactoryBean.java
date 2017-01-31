package vsoc.util;

import java.io.IOException;
import java.net.URL;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.FactoryBean;

/**
 * Reads a serealized file into an application context.
 * 
 */
public class SerFactoryBean implements FactoryBean {

    private String resName = null;

    private Object obj = null;

    public SerFactoryBean(String resName) {
        super();
        this.resName = resName;
    }

    public Object getObject() throws Exception {
        if (this.obj == null) {
            this.obj = deserealize();
        }
        return this.obj;
    }

    private Object deserealize() throws IOException {
        URL url = getClass().getClassLoader().getResource(this.resName);
        if (url == null) {
            throw new BeanCreationException("Could not find resource " +
                    this.resName);
        }
        return Serializer.current().deserialize(url.openStream());
    }

    public Class getObjectType() {
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
