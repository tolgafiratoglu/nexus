package com.nexus.reports;
import lombok.Data;

import jakarta.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "charts")
public class Chart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, insertable = false, updatable = false)
    private long id;

    @Column(nullable = false)
    private boolean deleted = true;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String metric;

    @Column(nullable = false)
    private String namespace; // s3, dynamodb, cloudwatch ...

    @Column(length = 65535,columnDefinition="Text")
    private String items;
}
