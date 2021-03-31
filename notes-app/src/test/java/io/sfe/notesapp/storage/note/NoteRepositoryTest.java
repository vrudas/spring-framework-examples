package io.sfe.notesapp.storage.note;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DataJdbcTest
public class NoteRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NoteRepository noteRepository;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("TRUNCATE TABLE note");
    }

    @Test
    @DisplayName("No notes in database")
    public void no_history_records_in_db() {
        long notesCount = noteRepository.count();
        assertThat(notesCount).isZero();
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

        assertThat(noteRepository.count()).isZero();
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
    void note_was_found() {
        var noteToSave = NoteEntity.of("text");
        noteRepository.save(noteToSave);

        Optional<NoteEntity> noteEntity = noteRepository.findById(1);

        assertThat(noteEntity).get().isEqualTo(NoteEntity.of("text").withId(1));
    }

    @Test
    @DisplayName("Find all notes")
    @DirtiesContext
    void find_all_notes() {
        var firstNote = NoteEntity.of("text1");
        var secondNote = NoteEntity.of("text2");

        noteRepository.save(firstNote);
        noteRepository.save(secondNote);

        var noteEntities = noteRepository.findAll();

        assertThat(noteEntities).isEqualTo(
            List.of(
                NoteEntity.of("text1").withId(1),
                NoteEntity.of("text2").withId(2)
            )
        );
    }
}
