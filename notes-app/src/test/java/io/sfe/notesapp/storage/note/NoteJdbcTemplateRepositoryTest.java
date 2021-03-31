package io.sfe.notesapp.storage.note;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@JdbcTest
@Sql(scripts = "classpath:schema.sql")
class NoteJdbcTemplateRepositoryTest {

    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    private NoteJdbcTemplateRepository noteJdbcTemplateRepository;

    @BeforeEach
    void setUp() {
        noteJdbcTemplateRepository = new NoteJdbcTemplateRepository(namedJdbcTemplate);
    }

    @AfterEach
    void tearDown() {
        JdbcTestUtils.dropTables(
            namedJdbcTemplate.getJdbcTemplate(),
            "note"
        );
    }

    private int fetchNotesCount() {
        return JdbcTestUtils.countRowsInTable(
            namedJdbcTemplate.getJdbcTemplate(),
            "note"
        );
    }

    @Test
    @DisplayName("Save note and check note data")
    void save_note_and_check_note_data() {
        int savedNoteId = noteJdbcTemplateRepository.save("text");

        var savedNoteText = namedJdbcTemplate.queryForObject(
            "SELECT text FROM note WHERE id = :id",
            Map.of("id", savedNoteId),
            String.class
        );

        assertThat(savedNoteId).isEqualTo(1);
        assertThat(savedNoteText).isEqualTo("text");
    }

    @Test
    @DisplayName("Save multiple notes")
    void save_multiple_notes() {
        noteJdbcTemplateRepository.save("1");
        noteJdbcTemplateRepository.save("2");

        var notesCount = fetchNotesCount();

        assertThat(notesCount).isEqualTo(2);
    }

    @Test
    @DisplayName("Note was not found")
    void note_was_not_found() {
        Optional<NoteEntity> noteEntity = noteJdbcTemplateRepository.findById(1);

        assertThat(noteEntity).isEmpty();
    }

    @Test
    @DisplayName("Note was found")
    void note_was_found() {
        new SimpleJdbcInsert(namedJdbcTemplate.getJdbcTemplate())
            .withTableName("note")
            .usingGeneratedKeyColumns("id")
            .usingColumns("text")
            .execute(Map.of("text", "text"));

        Optional<NoteEntity> noteEntity = noteJdbcTemplateRepository.findById(1);

        assertThat(noteEntity).get().isEqualTo(NoteEntity.of("text").withId(1));
    }

    @Test
    @DisplayName("Find all notes")
    void find_all_notes() {
        var batchInsertParameters = SqlParameterSourceUtils.createBatch(
            Map.of("text", "text1"),
            Map.of("text", "text2")
        );

        new SimpleJdbcInsert(namedJdbcTemplate.getJdbcTemplate())
            .withTableName("note")
            .usingGeneratedKeyColumns("id")
            .usingColumns("text")
            .executeBatch(batchInsertParameters);

        List<NoteEntity> noteEntities = noteJdbcTemplateRepository.findAll();

        assertThat(noteEntities).isEqualTo(
            List.of(
                NoteEntity.of("text1").withId(1),
                NoteEntity.of("text2").withId(2)
            )
        );
    }

    @Test
    @DisplayName("Note not deleted in case when not exists")
    void note_not_deleted_in_case_when_not_exists() {
        assertThatCode(() -> noteJdbcTemplateRepository.delete(1)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Note deleted")
    void note_deleted() {
        new SimpleJdbcInsert(namedJdbcTemplate.getJdbcTemplate())
            .withTableName("note")
            .usingGeneratedKeyColumns("id")
            .usingColumns("text")
            .execute(Map.of("text", "text"));

        var notesCountBeforeDeletion = fetchNotesCount();
        assertThat(notesCountBeforeDeletion).isEqualTo(1);


        noteJdbcTemplateRepository.delete(1);

        var notesCount = fetchNotesCount();

        assertThat(notesCount).isZero();
    }

    @Test
    @DisplayName("Note updated")
    void note_updated() {
        new SimpleJdbcInsert(namedJdbcTemplate.getJdbcTemplate())
            .withTableName("note")
            .usingGeneratedKeyColumns("id")
            .usingColumns("text")
            .execute(Map.of("text", "text"));

        noteJdbcTemplateRepository.updateNote(1, "TEXT");

        var updatedNoteText = namedJdbcTemplate.queryForObject(
            "SELECT text FROM note WHERE id = :id",
            Map.of("id", 1),
            String.class
        );

        assertThat(updatedNoteText).isEqualTo("TEXT");
    }
}
