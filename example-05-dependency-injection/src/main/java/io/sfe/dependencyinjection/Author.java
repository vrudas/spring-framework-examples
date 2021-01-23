package io.sfe.dependencyinjection;

import java.util.Objects;

public class Author {

    private final int authorId;

    public Author(int authorId) {
        this.authorId = authorId;
    }

    public int getAuthorId() {
        return authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return authorId == author.authorId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId);
    }

    @Override
    public String toString() {
        return "Author{" +
            "authorId=" + authorId +
            '}';
    }
}
