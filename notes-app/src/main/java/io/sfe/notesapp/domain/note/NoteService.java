package io.sfe.notesapp.domain.note;

import io.sfe.notesapp.storage.note.NoteEntity;
import io.sfe.notesapp.storage.note.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toUnmodifiableList;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note save(String noteText) {
        NoteEntity noteToSave = NoteEntity.of(noteText);
        NoteEntity savedNote = noteRepository.save(noteToSave);

        return Note.of(savedNote.getId(), savedNote.getText());
    }

    public List<Note> findAll() {
        Iterable<NoteEntity> allNotes = noteRepository.findAll();

        return StreamSupport.stream(allNotes.spliterator(), false)
            .map(noteEntity -> Note.of(noteEntity.getId(), noteEntity.getText()))
            .collect(toUnmodifiableList());
    }

    public Note findById(int id) {
        return noteRepository.findById(id)
            .map(noteEntity -> Note.of(noteEntity.getId(), noteEntity.getText()))
            .orElseThrow();
    }

    public void delete(int id) {
        noteRepository.deleteById(id);
    }
}
