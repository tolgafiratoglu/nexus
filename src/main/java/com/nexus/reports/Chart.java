package com.nexus.reports;
import lombok.Data;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

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

    @OneToMany(mappedBy = "chart")
    private List<ChartMeta> chartMetas;
}
