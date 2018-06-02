package br.edu.uniritter.monitors.service;

import br.edu.uniritter.monitors.model.Reading;
import br.edu.uniritter.monitors.model.Rule;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReadingService {

    @Autowired
    private RuleService ruleService;

    public boolean checkIfReadingMatchesRule(Reading reading) {
        List<Rule> rules = ruleService.getRulesForReading(reading);

        // TODO: check if reading matches with any rule?

        // IF IS WAS C#:
        // return rules.Where(x => x.metric == reading.metric) // No need if getRulesForReading already filters this
        //             .Where(x => x.origin == reading.origin) // No need if getRulesForReading already filters this
        //             .Where(x => x.threshold > reading.value) //TODO: check the rule instead
        //             .Any();

        // TODO: this shouldn't be here
        reading.setRule(rules.get(0).getRule());
        reading.setThreshold(rules.get(0).getThreshold());

        return false;
    }
}
