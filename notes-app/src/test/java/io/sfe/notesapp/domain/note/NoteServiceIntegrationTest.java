package io.sfe.notesapp.domain.note;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(statements = "TRUNCATE TABLE note")
class NoteServiceIntegrationTest {

    @Autowired
    private NoteService noteService;

    @Test
    @DisplayName("Note was saved for with 'text' input")
    void note_was_saved_with_correct_input() {
        Note savedNote = noteService.save("text");

        assertThat(savedNote).isEqualTo(new Note(savedNote.id(), "text"));
    }

    @Test
    @DisplayName("Empty list of notes is returned in case when no notes in storage")
    void empty_list_of_notes_is_returned_in_case_when_no_notes_in_storage() {
        List<Note> notes = noteService.findAll();

        assertThat(notes).isEmpty();
    }

    @Test
    @DisplayName("List of notes is returned in case when notes are exists in storage")
    void list_of_notes_is_returned_in_case_when_notes_are_exists_in_storage() {
        noteService.save("text1");
        noteService.save("text2");

        List<Note> notes = noteService.findAll();

        assertThat(notes).isNotEmpty();
    }

    @Test
    @DisplayName("Note was not found by id")
    void note_was_not_found_by_id() {
        assertThatCode(() -> noteService.findById(Integer.MAX_VALUE))
            .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Note was found by id")
    void note_was_found_by_id() {
        Note savedNote = noteService.save("text");

        Note note = noteService.findById(savedNote.id());

        assertThat(note).isEqualTo(new Note(savedNote.id(), "text"));
    }

    @Test
    @DisplayName("Note was deleted")
    void note_was_deleted() {
        Note savedNote = noteService.save("text");
        int savedNoteId = savedNote.id();

        noteService.delete(savedNoteId);

        assertThatCode(() -> noteService.findById(savedNoteId))
            .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Note was updated")
    void note_was_updated() {
        Note savedNote = noteService.save("text");
        int savedNoteId = savedNote.id();

        Note updatedNote = noteService.updateNote(savedNoteId, "TEXT");

        assertThat(updatedNote).isEqualTo(new Note(savedNoteId, "TEXT"));
    }

}
