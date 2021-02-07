package io.sfe.notesapp.web.note;

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
public class NoteControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Note CRUD integration test")
    void note_crud_integration_test() {
        int noteId = 1;

        createNote();

        readNote(noteId);

        updateNote(noteId);

        deleteNote(noteId);
    }

    private void createNote() {
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
