package br.edu.uniritter.monitors.controller;

import br.edu.uniritter.monitors.model.Rule;
import br.edu.uniritter.monitors.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class RuleController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private RuleService ruleService;

    @GetMapping(path = "/rule")
    public ResponseEntity<List<Rule>> getAll() {
        try {
            return ResponseEntity.ok(ruleService.getAll());
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/rule/{id}")
    public ResponseEntity<Rule> getById(@PathVariable("id") long id) {
        try {
            Rule rule = ruleService.get(id);
            return ResponseEntity.ok(rule);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/rule")
    public ResponseEntity<Rule> create(@RequestBody Rule rule) {
        try {
            return ResponseEntity.ok(ruleService.create(rule));
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/rule/{organizationId}")
    public ResponseEntity<Rule> update(@PathVariable("organizationId") long id, @RequestBody Rule rule) throws Exception {
        try {
            return new ResponseEntity<>(ruleService.update(id, rule), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/rule/{id}")
    public ResponseEntity delete(@PathVariable("id") long id) {
        try {
            ruleService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
