package io.sfe.properties;

import io.sfe.properties.db.DbConnectionProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext("io.sfe.properties");

        var dbConnectionProvider = applicationContext.getBean(DbConnectionProvider.class);

        dbConnectionProvider.getDbConnection();
    }
}
