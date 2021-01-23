package io.sfe.javaconfig.config;

import io.sfe.javaconfig.NoteRepository;
import io.sfe.javaconfig.NoteService;
import io.sfe.javaconfig.db.DbConnectionProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@Import(DbConfig.class)
public class AppConfig {

    @Bean
    public NoteRepository noteRepository(DbConnectionProvider dbConnectionProvider) {
        return new NoteRepository(dbConnectionProvider);
    }

    @Bean
    public NoteService noteService(NoteRepository noteRepository) {
        return new NoteService(noteRepository);
    }
}
