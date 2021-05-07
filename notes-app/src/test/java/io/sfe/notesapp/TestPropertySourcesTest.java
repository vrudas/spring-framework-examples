package io.sfe.notesapp;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@TestPropertySource(
    locations = "/test.properties",
    properties = {"application.port: 4242"}
)
class TestPropertySourcesTest {

    @Value("${application.name}")
    private String applicationName;

    @Value("${application.port}")
    private int applicationPort;

    @Test
    void verify_properties() {
        assertThat(applicationName).isEqualTo("notes-app-test");
        assertThat(applicationPort).isEqualTo(4242);
    }
}
