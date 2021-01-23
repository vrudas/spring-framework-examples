package io.sfe.beandefinition;

import java.util.List;

public class NoteService {

    private List<Note> notes;

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public void init() {
        System.out.println("Lazy init");
    }

    @Override
    public String toString() {
        return "NoteService{" +
            "notes=" + notes +
            '}';
    }
}
