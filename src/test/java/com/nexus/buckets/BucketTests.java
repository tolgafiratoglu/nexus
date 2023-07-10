package com.nexus.buckets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BucketTests {
    
    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testMyPathEndpoint() {
        String url = "http://localhost:8080/bucket/list";

        ResponseEntity<Void> response = restTemplate.getForEntity(url, Void.class);
        HttpStatusCode statusCode = response.getStatusCode();

        Assertions.assertEquals(HttpStatus.OK, statusCode);
    }
}
