package com.nexus.reports;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChartMetaRepository extends JpaRepository<ChartMeta, Long> {
}
