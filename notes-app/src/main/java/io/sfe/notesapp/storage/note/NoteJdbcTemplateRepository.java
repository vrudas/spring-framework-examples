package io.sfe.notesapp.storage.note;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyMap;

@Component
class NoteJdbcTemplateRepository {

    private static final RowMapper<NoteEntity> NOTE_ENTITY_ROW_MAPPER = (rs, rowNum) -> {
        var id = rs.getInt("id");
        var text = rs.getString("text");
        return NoteEntity.of(text).withId(id);
    };

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    NoteJdbcTemplateRepository(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    int save(String noteText) {
        return new SimpleJdbcInsert(namedJdbcTemplate.getJdbcTemplate())
            .withTableName("note")
            .usingGeneratedKeyColumns("id")
            .usingColumns("text")
            .executeAndReturnKey(Map.of("text", noteText))
            .intValue();
    }

    Optional<NoteEntity> findById(int id) {
        return namedJdbcTemplate.queryForStream(
            "SELECT id, text FROM note WHERE id = :id",
            Map.of("id", id),
            NOTE_ENTITY_ROW_MAPPER
        ).findFirst();
    }

    List<NoteEntity> findAll() {
        return namedJdbcTemplate.query(
            "SELECT id, text FROM note",
            emptyMap(),
            NOTE_ENTITY_ROW_MAPPER
        );
    }

    void delete(int id) {
        namedJdbcTemplate.update(
            "DELETE FROM note WHERE id = :id",
            Map.of("id", id)
        );
    }

}
