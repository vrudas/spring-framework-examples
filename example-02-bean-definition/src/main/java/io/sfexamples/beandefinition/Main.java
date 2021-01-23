package io.sfexamples.beandefinition;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        var applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Show example of lazy init case
        var noteService = applicationContext.getBean("noteService", NoteService.class);

        System.out.println("noteService = " + noteService);
    }
}
