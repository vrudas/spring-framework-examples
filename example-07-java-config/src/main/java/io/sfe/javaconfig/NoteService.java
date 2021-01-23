package io.sfe.javaconfig;

public class NoteService {

    //    Example of field injection
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    void saveNote(Note note) {
        noteRepository.saveNote(note);
    }

}
