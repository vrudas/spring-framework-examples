package io.sfe.notesapp.domain.note;


import io.sfe.notesapp.storage.note.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
    classes = {
        NoteService.class,
        NoteServiceContextConfigurationTest.TestContextConfig.class
    }
)
class NoteServiceContextConfigurationTest {

    @TestConfiguration
    static class TestContextConfig {
        @Bean
        public NoteRepository noteRepository() {
            return mock(NoteRepository.class);
        }
    }

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private NoteService noteService;

    @Test
    void delete_note_by_id() {
        noteService.delete(1);

        verify(noteRepository).deleteById(1);
    }
}
