package io.sfe.notesapp.storage.note;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table("NOTE")
public
class NoteEntity {

    @Id
    private final Integer id;
    private final String text;

    public static NoteEntity of(String text) {
        return new NoteEntity(null, text);
    }

    NoteEntity(Integer id, String text) {
        this.id = id;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    NoteEntity withId(Integer id) {
        return new NoteEntity(id, this.text);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteEntity that = (NoteEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }

    @Override
    public String toString() {
        return "NoteEntity{" +
            "id=" + id +
            ", text='" + text + '\'' +
            '}';
    }
}
