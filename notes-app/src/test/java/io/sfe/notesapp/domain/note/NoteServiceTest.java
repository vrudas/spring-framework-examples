package io.sfe.notesapp.domain.note;

import io.sfe.notesapp.storage.note.NoteEntity;
import io.sfe.notesapp.storage.note.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    private NoteService noteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        noteService = new NoteService(noteRepository);
    }

    @Test
    @DisplayName("Note was saved for with 'text' input")
    void note_was_saved_with_correct_input() {
        when(noteRepository.save(NoteEntity.of("text")))
            .thenReturn(NoteEntity.of("text").withId(1));

        Note savedNote = noteService.save("text");

        assertThat(savedNote).isEqualTo(Note.of(1, "text"));
    }

    @Test
    @DisplayName("Empty list of notes is returned in case when no notes in storage")
    void empty_list_of_notes_is_returned_in_case_when_no_notes_in_storage() {
        when(noteRepository.findAll()).thenReturn(emptyList());

        List<Note> notes = noteService.findAll();

        assertThat(notes).isEmpty();
    }

    @Test
    @DisplayName("List of notes is returned in case when notes are exists in storage")
    void list_of_notes_is_returned_in_case_when_notes_are_exists_in_storage() {
        when(noteRepository.findAll()).thenReturn(
            List.of(
                NoteEntity.of("text").withId(1)
            )
        );

        List<Note> notes = noteService.findAll();

        assertThat(notes).isEqualTo(List.of(Note.of(1, "text")));
    }

    @Test
    @DisplayName("Note was not found by id")
    void note_was_not_found_by_id() {
        when(noteRepository.findById(1))
            .thenReturn(Optional.empty());

        assertThatCode(() -> noteService.findById(1))
            .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Note was found by id")
    void note_was_found_by_id() {
        when(noteRepository.findById(1)).thenReturn(
            Optional.of(NoteEntity.of("text").withId(1))
        );

        Note note = noteService.findById(1);

        assertThat(note).isEqualTo(Note.of(1, "text"));
    }

    @Test
    @DisplayName("Note was deleted")
    void note_was_deleted() {
        noteService.delete(1);
        verify(noteRepository).deleteById(1);
    }

    @Test
    @DisplayName("Note was updated")
    void note_was_updated() {
        when(noteRepository.save(NoteEntity.of("TEXT").withId(1)))
            .thenReturn(NoteEntity.of("TEXT").withId(1));

        Note updatedNote = noteService.updateNote(1, "TEXT");

        assertThat(updatedNote).isEqualTo(Note.of(1, "TEXT"));
    }
}
