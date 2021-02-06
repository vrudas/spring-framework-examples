package io.sfe.notesapp.storage.note;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

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

        var notesCount = JdbcTestUtils.countRowsInTable(
            namedJdbcTemplate.getJdbcTemplate(),
            "note"
        );

        assertThat(notesCount).isEqualTo(2);
    }

}
