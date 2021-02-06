package io.sfe.notesapp.storage.note;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.assertj.core.api.Assertions.assertThatCode;

@JdbcTest
class NoteTableSchemaTest {

    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @BeforeEach
    void setUp() {
        JdbcTestUtils.deleteFromTables(
            namedJdbcTemplate.getJdbcTemplate(),
            "note"
        );
    }

    @Test
    void failed_to_insert_null_text_value() {
        var params = new MapSqlParameterSource();
        params.addValue("text", null);

        assertThatCode(() -> namedJdbcTemplate.update("INSERT INTO note(text) VALUES (:text)", params))
            .isInstanceOf(DataIntegrityViolationException.class);
    }
}
