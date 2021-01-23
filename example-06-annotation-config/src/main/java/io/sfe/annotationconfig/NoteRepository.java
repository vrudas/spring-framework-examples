package io.sfe.annotationconfig;

import org.springframework.stereotype.Repository;

@Repository
public class NoteRepository {

    public void saveNote(Note note) {
        System.out.println("Note: " + note + " was saved");
    }
}
