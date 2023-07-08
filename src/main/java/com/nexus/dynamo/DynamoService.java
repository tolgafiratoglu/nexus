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
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.waiters.Waiter;
import com.amazonaws.waiters.WaiterParameters;

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

        dynamoDB = new DynamoDB(dynamoDbClient);
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
        List<AttributeDefinition> attributeDefinitions= new ArrayList<AttributeDefinition>();
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("Id").withAttributeType("N"));

        List<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
        keySchema.add(new KeySchemaElement().withAttributeName("Id").withKeyType(KeyType.HASH));              

        CreateTableRequest createRequest = new CreateTableRequest()
                .withAttributeDefinitions(attributeDefinitions)
                .withKeySchema(keySchema)
                .withProvisionedThroughput(new ProvisionedThroughput()
                .withReadCapacityUnits(5L)
                .withWriteCapacityUnits(6L))
                .withTableName(tableName);
  
        dynamoDbClient.createTable(createRequest);

        // Wait for the table to be created
        Waiter<DescribeTableRequest> waiter = dynamoDbClient.waiters().tableExists();
        waiter.run(new WaiterParameters<>(new DescribeTableRequest(tableName)));

        // Get the newly created table
        return dynamoDB.getTable(tableName);
    }
}
