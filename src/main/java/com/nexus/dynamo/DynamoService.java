package com.nexus.dynamo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.sns.model.Topic;

import java.io.InputStream;

@Service
public class DynamoService {
    
    AmazonDynamoDB dynamoDbClient;
    DynamoDB dynamoDB;

    DynamoService() {
        AWSCredentials credentials = new ProfileCredentialsProvider().getCredentials();

        dynamoDbClient = AmazonDynamoDBClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.US_EAST_1)
            .build();
    }

    protected List<DynamoDTO> convertIntoDTOList(List<String> tableNames) {
        List<DynamoDTO> dynamoDTOList = new ArrayList<DynamoDTO>();

        for(String tableName : tableNames) {
            DynamoDTO dynamoDTO = new DynamoDTO();
            dynamoDTO.setName(tableName);
            dynamoDTOList.add(dynamoDTO);
        }

        return dynamoDTOList;
    }

    protected List<DynamoDTO> getList() {
        ListTablesResult tables = dynamoDbClient.listTables();
        List<String> tableNames = tables.getTableNames();
        return convertIntoDTOList(tableNames);
    }

    protected Table create(String tableName) {
        CreateTableRequest createRequest = new CreateTableRequest()
                .withTableName(tableName);
        
        CreateTableResult createResult = dynamoDbClient.createTable(createRequest);

        // Wait for the table to be created
        dynamoDbClient.waiters().tableExists().run(() -> new DescribeTableRequest(tableName));

        // Get the newly created table
        return dynamoDB.getTable(tableName);
    }
}
