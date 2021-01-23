package io.sfe.javaconfig;

import io.sfe.javaconfig.db.DbConnectionProvider;

public class NoteRepository {

    private final DbConnectionProvider dbConnectionProvider;

    public NoteRepository(DbConnectionProvider dbConnectionProvider) {
        this.dbConnectionProvider = dbConnectionProvider;
    }

    public void saveNote(Note note) {
        dbConnectionProvider.getDbConnection();
        System.out.println("Note: " + note + " was saved");
    }
}
