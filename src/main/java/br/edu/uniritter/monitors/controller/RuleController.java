package br.edu.uniritter.monitors.controller;

import br.edu.uniritter.monitors.model.Rule;
import br.edu.uniritter.monitors.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(method = RequestMethod.GET, path = "/rule")
    public ResponseEntity<List<Rule>> getAll() {
        return ResponseEntity.ok(ruleService.getAll());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/rule/{id}")
    public ResponseEntity<Rule> getById(@PathVariable("id") long id) {
        Rule rule = ruleService.get(id);
        return ResponseEntity.ok(rule);
    }
}
