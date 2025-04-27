package com.gymapp.gymapp.specification;

import com.gymapp.gymapp.domain.Assinatura;
import com.gymapp.gymapp.model.inputs.AssinaturaFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AssinaturaSpecifications {

    public static Specification<Assinatura> comFiltro(AssinaturaFilter filter) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getNomeAluno() != null && !filter.getNomeAluno().isBlank()) {
                predicates.add(builder.like(builder.lower(root.get("aluno").get("nome")), "%" + filter.getNomeAluno().toLowerCase() + "%"));
            }

            if (filter.getPeriodicidade() != null) {
                predicates.add(builder.equal(root.get("plano").get("periodicidade"), filter.getPeriodicidade()));
            }

            if (filter.getDataInicio() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("dataInicio"), filter.getDataInicio()));
            }

            if (filter.getDataFim() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("dataFim"), filter.getDataFim()));
            }

            if (filter.getAtiva() != null) {
                predicates.add(builder.equal(root.get("ativa"), filter.getAtiva()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

