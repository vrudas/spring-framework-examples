package io.sfe.beanfactory;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class Main {
    public static void main(String[] args) {
        var applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        DefaultListableBeanFactory listableBeanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(listableBeanFactory);
        beanDefinitionReader.loadBeanDefinitions(new ClassPathResource("applicationContext.xml"));

        var noteFromApplicationContext = applicationContext.getBean("helloWorldNote", Note.class);
        var noteFromCustomBeanFactory = listableBeanFactory.getBean("helloWorldNote", Note.class);

        System.out.println("noteFromApplicationContext = " + noteFromApplicationContext);
        System.out.println("noteFromCustomBeanFactory = " + noteFromCustomBeanFactory);

        System.out.println("Beans are not the same: " + (noteFromApplicationContext != noteFromCustomBeanFactory));
    }
}
