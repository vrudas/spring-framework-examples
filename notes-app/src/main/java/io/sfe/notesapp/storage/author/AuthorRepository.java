package io.sfe.notesapp.storage.author;

import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<AuthorEntity, Integer> {
}
