package br.edu.uniritter.monitors.service;

import br.edu.uniritter.monitors.model.Heartbeat;
import br.edu.uniritter.monitors.model.Timeout;
import br.edu.uniritter.monitors.repository.TimeoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class TimeoutService {

    @Autowired
    private TimeoutRepository timeoutRepository;

    @Autowired
    private HeartbeatService heartbeatService;

    private Timeout create(Timeout timeout) {
        return timeoutRepository.save(timeout);
    }

    private Timeout update(Timeout oldTimeout, long time) {
        oldTimeout.setValue(time);
        return timeoutRepository.save(oldTimeout);
    }

    public Timeout createOrUpdate(Timeout newTimeout) {
        List<Timeout> timeouts = timeoutRepository
                .findByOriginAndMetric(newTimeout.getOrigin(), newTimeout.getMetric());

        Timeout savedTimeout;
        if (timeouts.isEmpty()) {
            savedTimeout = create(newTimeout);
        } else {
            savedTimeout = update(timeouts.get(0), newTimeout.getValue());
        }

        // ---- this could be decoupled (use events?)
        Heartbeat heartbeat = new Heartbeat();
        heartbeat.setOrigin(savedTimeout.getOrigin());
        heartbeat.setMetric(savedTimeout.getMetric());
        heartbeat.setLastReading(Calendar.getInstance().getTimeInMillis());

        heartbeatService.createOrUpdate(heartbeat);
        // ----

        return savedTimeout;
    }

    public Timeout get(String origin, String metric) {
        List<Timeout> timeouts = timeoutRepository.findByOriginAndMetric(origin, metric);
        return timeouts.isEmpty() ? null : timeouts.get(0);
    }

    public List<Timeout> getAll() {
        return timeoutRepository.findAll();
    }
}
