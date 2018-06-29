package br.edu.uniritter.monitors.repository;

import br.edu.uniritter.monitors.model.Timeout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeoutRepository extends JpaRepository<Timeout, Long> {
    List<Timeout> findByOriginAndMetric(String origin, String metric);
}
