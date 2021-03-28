package io.sfe.notesapp.storage.author;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table("AUTHOR")
public class AuthorEntity {

    @Id
    private final Integer id;
    private final String name;

    AuthorEntity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public AuthorEntity withId(Integer id) {
        return new AuthorEntity(id, this.name);
    }

    public static AuthorEntity of(String name) {
        return new AuthorEntity(null, name);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorEntity that = (AuthorEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "AuthorEntity{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
