package io.sfe.notesapp.storage.note;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DataJdbcTest
public class NoteRepositoryTest {

    @Autowired
    private NoteRepository noteRepository;

    @BeforeEach
    void setUp() {
        noteRepository.deleteAll();
    }

    @Test
    @DisplayName("No notes in database")
    public void no_history_records_in_db() {
        long notesCount = noteRepository.count();
        assertThat(notesCount).isEqualTo(0);
    }

    @Test
    @DisplayName("Nothing happened when trying to delete not existing note")
    public void nothing_happened_when_trying_to_delete_not_existing_note() {
        assertThatCode(() -> noteRepository.deleteById(1))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Note was deleted")
    public void note_was_deleted() {
        var noteToSave = NoteEntity.of("text");

        var savedNoteEntity = noteRepository.save(noteToSave);

        assertThat(noteRepository.count()).isEqualTo(1);

        noteRepository.delete(savedNoteEntity);

        assertThat(noteRepository.count()).isEqualTo(0);
    }

    @Test
    @DisplayName("Save note and check note data")
    @DirtiesContext
    void save_note_and_check_note_data() {
        var noteToSave = NoteEntity.of("text");
        var savedNote = noteRepository.save(noteToSave);

        assertThat(savedNote).extracting(NoteEntity::getId).isEqualTo(1);
        assertThat(savedNote).extracting(NoteEntity::getText).isEqualTo("text");
    }

    @Test
    @DisplayName("Save multiple notes")
    @DirtiesContext
    void save_multiple_notes() {
        noteRepository.save(NoteEntity.of("1"));
        noteRepository.save(NoteEntity.of("2"));

        assertThat(noteRepository.count()).isEqualTo(2);
    }

    @Test
    @DisplayName("Note was not found")
    void note_was_not_found() {
        Optional<NoteEntity> noteEntity = noteRepository.findById(1);

        assertThat(noteEntity).isEmpty();
    }

    @Test
    @DisplayName("Note was found")
    @DirtiesContext
    void note_was_found() {
        var noteToSave = NoteEntity.of("text");
        NoteEntity savedNote = noteRepository.save(noteToSave);

        Optional<NoteEntity> noteEntity = noteRepository.findById(1);

        assertThat(noteEntity).get().isEqualTo(NoteEntity.of("text").withId(1));
    }

}
