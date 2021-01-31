package io.sfe.notesapp.storage.note;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface NoteRepository extends CrudRepository<NoteEntity, Integer> {

}
