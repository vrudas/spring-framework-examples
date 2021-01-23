package io.sfe.beanscope;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        var applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        var noteSingleton = applicationContext.getBean("noteSingleton", Note.class);
        noteSingleton.setText("Modified");

        var anotherNoteSingleton = applicationContext.getBean("noteSingleton", Note.class);

        System.out.println("noteSingleton = " + noteSingleton);
        System.out.println("anotherNoteSingleton = " + anotherNoteSingleton);
        System.out.println();


        var notePrototype = applicationContext.getBean("notePrototype", Note.class);
        notePrototype.setText("Modified");

        var anotherNotePrototype = applicationContext.getBean("notePrototype", Note.class);

        System.out.println("notePrototype = " + notePrototype);
        System.out.println("anotherNotePrototype = " + anotherNotePrototype);
    }
}
