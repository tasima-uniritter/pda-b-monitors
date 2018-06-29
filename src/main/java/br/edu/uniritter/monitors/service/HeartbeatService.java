package br.edu.uniritter.monitors.service;

import br.edu.uniritter.monitors.model.Heartbeat;
import br.edu.uniritter.monitors.repository.HeartbeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class HeartbeatService {

    @Autowired
    private HeartbeatRepository heartbeatRepository;

    private Heartbeat create(Heartbeat heartbeat) {
        return heartbeatRepository.save(heartbeat);
    }

    private Heartbeat update(Heartbeat oldHeartbeat, long lastReading) {
        oldHeartbeat.setLastReading(lastReading);
        return heartbeatRepository.save(oldHeartbeat);
    }

    public Heartbeat createOrUpdate(Heartbeat newHeartbeat) {
        List<Heartbeat> heartbeats = heartbeatRepository
                .findByOriginAndMetric(newHeartbeat.getOrigin(), newHeartbeat.getMetric());

        if (heartbeats.isEmpty()) {
            return create(newHeartbeat);
        } else {
            return update(heartbeats.get(0), newHeartbeat.getLastReading());
        }
    }

    public Heartbeat get(String origin, String metric) {
        List<Heartbeat> heartbeats = heartbeatRepository.findByOriginAndMetric(origin, metric);

        return heartbeats.isEmpty() ? null : heartbeats.get(0);
    }

    public List<Heartbeat> getAll() {
        return heartbeatRepository.findAll();
    }
}
