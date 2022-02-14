package io.sfe.notesapp.web.note;

import io.sfe.notesapp.domain.note.Note;
import io.sfe.notesapp.domain.note.NoteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Full assertion of HTML is hard to maintain so used only HTTP status code validation.
 * REST approach will be more flexible.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoteControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private NoteService noteService;

    @Test
    @DisplayName("Note CRUD integration test")
    void note_crud_integration_test() {
        int noteId = createNote().id();

        readNote(noteId);

        updateNote(noteId);

        deleteNote(noteId);
    }

    private Note createNote() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("text", "text");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameters, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
            "/notes",
            request,
            String.class
        );

        assertThat(response).extracting(ResponseEntity::getStatusCode)
            .withFailMessage("Note is not created")
            .isEqualTo(HttpStatus.FOUND);

        var noteIdOptional = noteService.findAll().stream().findFirst();
        assertThat(noteIdOptional).isNotEmpty();

        return noteIdOptional.get();
    }

    private void readNote(int noteId) {
        ResponseEntity<String> response = restTemplate.getForEntity(
            "/notes/{noteId}",
            String.class,
            noteId
        );

        assertThat(response).extracting(ResponseEntity::getStatusCode)
            .withFailMessage("Note not found")
            .isEqualTo(HttpStatus.OK);
    }

    private void updateNote(int noteId) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("text", "TEXT");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameters, HttpHeaders.EMPTY);

        ResponseEntity<String> response = restTemplate.exchange(
            "/notes/{noteId}",
            HttpMethod.PUT,
            request,
            String.class,
            noteId
        );

        assertThat(response).extracting(ResponseEntity::getStatusCode)
            .withFailMessage("Note is not updated")
            .isEqualTo(HttpStatus.OK);
    }

    private void deleteNote(int noteId) {
        ResponseEntity<String> response = restTemplate.exchange(
            "/notes/{noteId}",
            HttpMethod.DELETE,
            HttpEntity.EMPTY,
            String.class,
            noteId
        );

        assertThat(response).extracting(ResponseEntity::getStatusCode)
            .withFailMessage("Note is not deleted")
            .isEqualTo(HttpStatus.NO_CONTENT);
    }
}
