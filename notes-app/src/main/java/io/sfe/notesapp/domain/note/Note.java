package io.sfe.notesapp.domain.note;

import java.util.Objects;

public class Note {

    private final Integer id;
    private final String text;

    public static Note of(int id, String text) {
        return new Note(id, text);
    }

    private Note(Integer id, String text) {
        this.id = id;
        this.text = text;
    }

    public Integer getId() {
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
        return Objects.equals(id, note.id) && Objects.equals(text, note.text);
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
