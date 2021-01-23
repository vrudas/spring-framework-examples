package io.sfe.dependencyinjection;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        var applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        Author author = applicationContext.getBean(Author.class);
        System.out.println("author = " + author);

        Note setterNote = applicationContext.getBean("setterNote", Note.class);
        System.out.println("setterNote = " + setterNote);

        Note constructorNote = applicationContext.getBean("constructorNote", Note.class);
        System.out.println("constructorNote = " + constructorNote);
    }
}
