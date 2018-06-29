package br.edu.uniritter.monitors.controller;

import br.edu.uniritter.monitors.model.Timeout;
import br.edu.uniritter.monitors.service.TimeoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TimeoutController {

    @Autowired
    private TimeoutService timeoutService;

    @GetMapping(path = "/timeout")
    public ResponseEntity<List<Timeout>> getAll() {
        try {
            return ResponseEntity.ok(timeoutService.getAll());
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/timeout")
    public ResponseEntity<Timeout> create(@RequestBody Timeout rule) {
        try {
            return ResponseEntity.ok(timeoutService.createOrUpdate(rule));
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
