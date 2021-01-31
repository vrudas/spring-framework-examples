package io.sfe.notesapp.storage.note;

import io.sfe.notesapp.domain.note.Note;
import io.sfe.notesapp.domain.note.NoteService;
import org.springframework.stereotype.Service;

@Service
public class DbNoteService implements NoteService {

    private final NoteRepository noteRepository;

    public DbNoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public Note save(SaveNoteCommand saveNoteCommand) {
        NoteEntity noteToSave = NoteEntity.of(saveNoteCommand.getText());
        NoteEntity savedNote = noteRepository.save(noteToSave);

        return Note.of(savedNote.getId(), savedNote.getText());
    }
}
