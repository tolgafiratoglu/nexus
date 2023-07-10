package com.nexus.dynamo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nexus.dynamo.DynamoDTO;
import com.nexus.dynamo.DynamoService;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class DynamoDBTests {
    @Autowired
    DynamoService dynamoService;
    
    @Test
    public void testConvertIntoDTOList() {
        List<String> tableNames = Arrays.asList("Table1", "Table2", "Table3");
        List<DynamoDTO> result = dynamoService.convertIntoDTOList(tableNames);

        Assertions.assertEquals(tableNames.size(), result.size());

        for (int i = 0; i < tableNames.size(); i++) {
            String expectedName = tableNames.get(i);
            DynamoDTO actualDTO = result.get(i);

            Assertions.assertEquals(expectedName, actualDTO.getName());
        }
    }
}