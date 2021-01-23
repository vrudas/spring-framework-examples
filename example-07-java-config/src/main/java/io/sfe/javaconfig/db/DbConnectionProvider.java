package io.sfe.javaconfig.db;

public class DbConnectionProvider {

    public Object getDbConnection() {
        System.out.println("Connection obtained");
        return new Object();
    }
}
