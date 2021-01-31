package io.sfe.notesapp.web.note;

import java.util.Objects;

public class NoteDto {

    private final int id;
    private final String text;

    public static NoteDto of(int id, String text) {
        return new NoteDto(id, text);
    }

    private NoteDto(int id, String text) {
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
        NoteDto noteDto = (NoteDto) o;
        return id == noteDto.id && Objects.equals(text, noteDto.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }

    @Override
    public String toString() {
        return "NoteDto{" +
            "id=" + id +
            ", text='" + text + '\'' +
            '}';
    }
}
