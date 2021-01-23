package io.sfe.beandefinition;

import java.util.Objects;

public class Note {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void init() {
        System.out.println("Init on start");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(text, note.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return "Note{" +
            "text='" + text + '\'' +
            '}';
    }
}
