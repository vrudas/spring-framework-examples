package io.sfe.annotationconfig;

import org.springframework.stereotype.Service;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

//    @Autowired // Not required after Spring 5
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    void saveNote(Note note) {
        noteRepository.saveNote(note);
    }

}
