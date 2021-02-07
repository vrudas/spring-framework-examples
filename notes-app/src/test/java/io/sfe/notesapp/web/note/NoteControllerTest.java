package io.sfe.notesapp.web.note;

import io.sfe.notesapp.domain.note.Note;
import io.sfe.notesapp.domain.note.NoteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class NoteControllerTest {

    @Autowired
    @MockBean
    private NoteService noteService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("All notes request")
    void all_notes_request() throws Exception {
        when(noteService.findAll()).thenReturn(
            List.of(Note.of(1, "1"))
        );

        mockMvc.perform(get("/notes"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("note/notes"))
            .andExpect(model().attribute("notes", List.of(NoteDto.of(1, "1"))));
    }

    @Test
    @DisplayName("Show create note page")
    void show_create_note_page() throws Exception {
        mockMvc.perform(get("/notes/create-note"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("note/create-note"))
            .andExpect(model().size(0));
    }

    @Test
    @DisplayName("Save note failed for missing 'text' argument")
    void save_note_failed_for_missing_text_argument() throws Exception {
        mockMvc.perform(post("/notes"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Note saved")
    void note_saved() throws Exception {
        mockMvc.perform(
            post("/notes")
            .param("text", "text")
        ).andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/notes"));

        verify(noteService).save("text");
    }

    @Test
    @DisplayName("Note not found because of incorrect id")
    void note_not_found_because_of_incorrect_id() throws Exception {
        mockMvc.perform(get("/notes/noteId"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Note not found")
    void note_not_found() throws Exception {
        when(noteService.findById(anyInt()))
            .thenThrow(NoSuchElementException.class);

        mockMvc.perform(get("/notes/1"))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Note found")
    void note_found() throws Exception {
        when(noteService.findById(1))
            .thenReturn(Note.of(1, "text"));

        mockMvc.perform(get("/notes/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("note/note"))
            .andExpect(model().attribute("note", NoteDto.of(1, "text")));
    }

    @Test
    @DisplayName("Note not deleted because of incorrect id")
    void note_not_deleted_because_of_incorrect_id() throws Exception {
        mockMvc.perform(delete("/notes/noteId"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Note deleted")
    void note_deleted() throws Exception {
        mockMvc.perform(delete("/notes/1"))
            .andExpect(status().isNoContent())
            .andExpect(redirectedUrl("/notes"));

        verify(noteService).delete(1);
    }

    @Test
    @DisplayName("Show update note page failed for incorrect note id")
    void show_update_note_page_failed_for_incorrect_id() throws Exception {
        mockMvc.perform(get("/notes/noteId/update-note"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Show update note page failed for not existing note")
    void show_update_note_page_failed_for_not_existing_note() throws Exception {
        when(noteService.findById(1))
            .thenThrow(NoSuchElementException.class);

        mockMvc.perform(get("/notes/1/update-note"))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Show update note page")
    void show_update_note_page() throws Exception {
        when(noteService.findById(1))
            .thenReturn(Note.of(1, "text"));

        mockMvc.perform(get("/notes/1/update-note"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("note/update-note"))
            .andExpect(model().attribute("note", NoteDto.of(1, "text")));
    }

}
