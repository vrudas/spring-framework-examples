package io.sfe.properties.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DbConnectionProvider {
    private final String url;
    private final String user;
    private final String password;

    public DbConnectionProvider(
        @Value("${db.url}") String url,
        @Value("${db.user}") String user,
        @Value("${db.password}") String password
    ) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Object getDbConnection() {
        System.out.println("Connection obtained for properties:");
        System.out.println("url = " + url);
        System.out.println("user = " + user.replaceAll("\\w", "*"));
        System.out.println("password = " + password.replaceAll("\\w", "*"));
        return new Object();
    }
}
