package io.sfe.dependencyinjection;

import java.util.Objects;

public class Note {

    private Author author;
    private String text;


    public Note() {

    }

    public Note(Author author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
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
            "author=" + author +
            ", text='" + text + '\'' +
            '}';
    }
}
