package br.edu.uniritter.monitors.repository;

import br.edu.uniritter.monitors.model.Heartbeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeartbeatRepository extends JpaRepository<Heartbeat, Long> {
    List<Heartbeat> findByOriginAndMetric(String origin, String metric);
}
