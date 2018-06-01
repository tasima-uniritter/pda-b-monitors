package br.edu.uniritter.monitors.service;

import br.edu.uniritter.monitors.model.Rule;
import br.edu.uniritter.monitors.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.InvalidParameterException;
import java.util.List;

@Service
@Transactional
public class RuleService {

    @Autowired
    private RuleRepository ruleRepository;

    public List<Rule> getAll() {
        return ruleRepository.findAll();
    }

    public Rule get(long id) {
//        Rule localRule = ruleRepository.findOne(id);
        Rule localRule = new Rule();
        localRule.setMetric("testeMetrica");
        localRule.setOrigin("testeOrigin");

        if (localRule == null) {
            throw new InvalidParameterException();
        }

        return localRule;
    }

    public void create(Rule rule) {
        ruleRepository.save(rule);
    }

    public Rule update(long id, Rule rule) {
        Rule localRule = get(id);

        if (localRule == null) {
            throw new InvalidParameterException();
        }

        localRule.setMetric(rule.getMetric());
        localRule.setOrigin(rule.getOrigin());
        localRule.setThreshold(rule.getThreshold());
        localRule.setRule(rule.getRule());

        ruleRepository.save(localRule);

        return localRule;
    }

    public void delete(long id) {
        Rule localRule = get(id);

        if (localRule == null) {
            throw new InvalidParameterException();
        }

        ruleRepository.delete(localRule);
    }
}
