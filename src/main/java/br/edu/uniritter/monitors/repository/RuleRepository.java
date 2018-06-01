package br.edu.uniritter.monitors.repository;

import br.edu.uniritter.monitors.model.Rule;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {
}
