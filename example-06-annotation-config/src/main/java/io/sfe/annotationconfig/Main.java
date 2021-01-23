package io.sfe.annotationconfig;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext("io.sfe.annotationconfig");

//        Example with component scan annotation
//        var applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        var noteService = applicationContext.getBean(NoteService.class);
        var noteValidator = applicationContext.getBean(NoteValidator.class);

        Note note = new Note("text");

        noteValidator.validateNote(note);
        noteService.saveNote(note);

    }
}
