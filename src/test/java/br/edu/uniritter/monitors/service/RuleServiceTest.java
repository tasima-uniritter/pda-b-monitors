package br.edu.uniritter.monitors.service;

import br.edu.uniritter.monitors.model.Rule;
import br.edu.uniritter.monitors.repository.RuleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class RuleServiceTest {
    private RuleService ruleService;

    @Mock
    private RuleRepository ruleRepository;
    private static final long FIRST_ID = 1;

    @org.junit.Rule
    public ExpectedException eReunião {0} fora do CAS em local onde não havia ponto para registroxpectedEx = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.ruleService = new RuleService(ruleRepository);
    }

    @Test
    public void shouldReturnAllRules() {
        List<Rule> expectedRules = Arrays.asList(createRule(1), createRule(2));

        Mockito.doReturn(expectedRules).when(ruleRepository).findAll();

        List<Rule> rules = new ArrayList<>(ruleService.getAll());
        assertThat(rules.size()).isGreaterThan(10);
    }

    @Test
    public void shouldReturnRuleById() throws InvalidParameterException {
        Optional<Rule> expectedRule = createRuleOptional(FIRST_ID);

        Mockito.doReturn(expectedRule).when(ruleRepository).findById(FIRST_ID);

        Rule actualRule = ruleService.get(1);
        assertThat(actualRule).isEqualToComparingFieldByField(expectedRule.get());
    }

    @Test
    public void shouldCreateRule() throws Exception {
        Rule rule = createRule(FIRST_ID);

        Mockito.doReturn(rule).when(ruleRepository).save(rule);

        Rule actualRule = ruleService.create(rule);

        assertThat(actualRule).isEqualToComparingFieldByField(rule);
    }

    @Test
    public void shouldUpdateRule() throws Exception {
        Optional<Rule> expectedRule = createRuleOptional(FIRST_ID);

        Mockito.doReturn(expectedRule).when(ruleRepository).findById(FIRST_ID);
        Mockito.doReturn(expectedRule.get()).when(ruleRepository).save(expectedRule.get());

        Rule actualRule = ruleService.update(FIRST_ID, expectedRule.get());

        assertThat(actualRule).isEqualToComparingFieldByField(expectedRule.get());
    }

    private Rule createRule(long id) {
        Rule rule = new Rule();
        rule.setId(id);
        rule.setMetric("myMetric" + id);
        rule.setOrigin("myOrigin" + id);
        rule.setRule(">");
        rule.setThreshold(500);

        return rule;
    }

    private Optional<Rule> createRuleOptional(long id) {
        return Optional.of(createRule(id));
    }
}
