package vsoc.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import vsoc.camps.Camp;

import junit.framework.TestCase;

public class SerFactoryBeanTestCase extends TestCase {
    
    public void testLoadCamp() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("serFactoryBeanTestCaseAppContext.xml");
        Camp camp = (Camp) ctx.getBean("camp");
        assertNotNull(camp);
    }
 
}
