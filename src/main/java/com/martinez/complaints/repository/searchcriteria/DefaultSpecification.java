package com.martinez.complaints.repository.searchcriteria;

import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Data
public class DefaultSpecification<T> implements Specification<T> {

    private final SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

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
            return criteriaBuilder.lessThan(root.get(key), Double.valueOf((String)value));
        }

        if (operation.equalsIgnoreCase("<=")) {
            return criteriaBuilder.lessThanOrEqualTo(root.get(key), Double.valueOf((String)value));
        }

        if (operation.equalsIgnoreCase(">")) {
            return criteriaBuilder.greaterThan(root.get(key), Double.valueOf((String)value));
        }

        if (operation.equalsIgnoreCase(">=")) {
            return criteriaBuilder.greaterThanOrEqualTo(root.get(key), Double.valueOf((String)value));
        }

        return null;
    }
}
