package com.martinez.complaints.repository.searchcriteria;

import com.martinez.complaints.entity.Citizen;
import com.martinez.complaints.exception.EmptySearchCriteriaListException;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CitizenSpecificationBuilder {

    private final List<SearchCriteria> searchCriterias;

    public CitizenSpecificationBuilder() {
        searchCriterias = new ArrayList<>();
    }

    public CitizenSpecificationBuilder with(String key, String operation, Object value) {
        searchCriterias.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Citizen> build() {
        return searchCriterias.stream()
                              .map(CitizenSpecification::new)
                              .map(citizenSpecification -> (Specification<Citizen>) citizenSpecification)
                              .reduce((sp1, sp2) -> Specification.where(sp1).and(sp2))
                              .orElseThrow(() -> new EmptySearchCriteriaListException("List of search criterias is empty!"));

    }

}
