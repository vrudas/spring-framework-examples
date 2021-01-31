package io.sfe.notesapp.storage.note;

import io.sfe.notesapp.domain.note.Note;
import io.sfe.notesapp.domain.note.NoteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toUnmodifiableList;

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

    @Override
    public List<Note> findAll() {
        Iterable<NoteEntity> allNotes = noteRepository.findAll();

        return StreamSupport.stream(allNotes.spliterator(), false)
            .map(noteEntity -> Note.of(noteEntity.getId(), noteEntity.getText()))
            .collect(toUnmodifiableList());
    }

    @Override
    public Optional<Note> findById(int id) {
        return noteRepository.findById(id)
            .map(noteEntity -> Note.of(noteEntity.getId(), noteEntity.getText()));
    }
}
