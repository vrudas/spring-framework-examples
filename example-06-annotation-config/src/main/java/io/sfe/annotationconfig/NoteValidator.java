package io.sfe.annotationconfig;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class NoteValidator {

    void validateNote(Note note) {
        if (note == null) {
            throw new IllegalArgumentException("Note is null");
        }

        String noteText = Objects.requireNonNullElse(note.text(), "");

        if (noteText.isBlank()) {
            throw new IllegalArgumentException("Note text is blank");
        }
    }
}
