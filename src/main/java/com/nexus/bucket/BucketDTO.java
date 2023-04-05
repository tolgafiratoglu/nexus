package com.nexus.bucket;

import java.util.Date;

import lombok.Data;

@Data
public class BucketDTO{
    int id;
    private String name;
    private Date date;
}
