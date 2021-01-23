package io.sfe.beanlifecycle;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        var applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        var lifecycleBean = applicationContext.getBean("lifecycleBean", LifecycleBean.class);
        System.out.println("lifecycleBean = " + lifecycleBean);

        applicationContext.registerShutdownHook();
    }
}
