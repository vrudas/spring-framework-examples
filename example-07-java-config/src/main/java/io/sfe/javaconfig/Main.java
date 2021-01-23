package io.sfe.javaconfig;

import io.sfe.javaconfig.config.AppConfig;
import io.sfe.javaconfig.config.DbConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(DbConfig.class, AppConfig.class);
        applicationContext.refresh();

//        var applicationContext = new AnnotationConfigApplicationContext(DbConfig.class, AppConfig.class);

//        Import example
//        var applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        var noteService = applicationContext.getBean(NoteService.class);

        Note note = new Note("text");

        noteService.saveNote(note);
    }
}
