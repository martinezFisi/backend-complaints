package com.martinez.complaints.repository.searchcriteria;

import com.martinez.complaints.entity.Citizen;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Data
public class CitizenSpecification implements Specification<Citizen> {

    private final SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(Root<Citizen> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        var key = searchCriteria.getKey();
        var operation = searchCriteria.getOperation();
        var value = searchCriteria.getValue();

        if (operation.equalsIgnoreCase("=")) {
            return criteriaBuilder.equal(root.<String>get(key), value.toString());
        }

        if (operation.equalsIgnoreCase(":")) {
            return criteriaBuilder.like(root.get(key),"%"+value.toString()+"%");
        }

        if (operation.equalsIgnoreCase("<")) {
            return criteriaBuilder.lessThan(root.get(key), (Integer) value);
        }

        if (operation.equalsIgnoreCase("<=")) {
            return criteriaBuilder.lessThanOrEqualTo(root.get(key), (Integer) value);
        }

        if (operation.equalsIgnoreCase(">")) {
            return criteriaBuilder.greaterThan(root.get(key), (Integer) value);
        }

        if (operation.equalsIgnoreCase(">=")) {
            return criteriaBuilder.greaterThanOrEqualTo(root.get(key), (Integer) value);
        }

        return null;
    }
}
