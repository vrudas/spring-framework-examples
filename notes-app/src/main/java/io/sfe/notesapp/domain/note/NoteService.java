package io.sfe.notesapp.domain.note;

import java.util.List;

public interface NoteService {

    Note save(String noteText);

    List<Note> findAll();

    Note findById(int id);

    void delete(int id);

}
