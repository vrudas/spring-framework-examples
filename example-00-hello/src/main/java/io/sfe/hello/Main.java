package io.sfe.hello;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        var applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        var noteByName = (Note) applicationContext.getBean("helloWorldNote");
        System.out.println("noteByName = " + noteByName);

        var noteByType = applicationContext.getBean(Note.class);
        System.out.println("noteByType = " + noteByType);

        var noteByNameAndType = applicationContext.getBean("helloWorldNote", Note.class);
        System.out.println("noteByNameAndType = " + noteByNameAndType);
    }
}
