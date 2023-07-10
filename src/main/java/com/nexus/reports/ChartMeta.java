package com.nexus.reports;
import lombok.Data;

import jakarta.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "chart_metas")
public class ChartMeta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, insertable = false, updatable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name="chart_id")
    private Chart chart;

    @Column(nullable = false)
    private String metaKey;

    @Column(length = 65535,columnDefinition="Text")
    private String metaValue;
}
