package com.nexus.dynamo;

import java.util.Date;

import lombok.Data;

@Data
public class DynamoDTO{
    int id;
    private String name;
    private Date date;
}
