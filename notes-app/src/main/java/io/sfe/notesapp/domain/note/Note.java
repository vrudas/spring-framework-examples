package io.sfe.notesapp.domain.note;

import java.util.Objects;

public class Note {

    private final int id;
    private final String text;

    public static Note of(int id, String text) {
        return new Note(id, text);
    }

    private Note(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return id == note.id && Objects.equals(text, note.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }

    @Override
    public String toString() {
        return "Note{" +
            "id=" + id +
            ", text='" + text + '\'' +
            '}';
    }
}
