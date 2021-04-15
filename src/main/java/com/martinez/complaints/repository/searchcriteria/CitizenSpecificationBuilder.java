package com.martinez.complaints.repository.searchcriteria;

import com.martinez.complaints.entity.Citizen;
import com.martinez.complaints.exception.EmptySearchCriteriaListException;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static com.martinez.complaints.repository.searchcriteria.SearchCriteria.AND;

public class CitizenSpecificationBuilder {

    private final List<SearchCriteria> searchCriterias;

    public CitizenSpecificationBuilder() {
        searchCriterias = new ArrayList<>();
    }

    public CitizenSpecificationBuilder with(SearchCriteria searchCriteria) {
        searchCriterias.add(searchCriteria);
        return this;
    }

    public Specification<Citizen> build() {
        if (searchCriterias.isEmpty()) {
            throw new EmptySearchCriteriaListException("List of search criterias is empty!");
        }

        var firstSearchCriteria = searchCriterias.get(0);
        var specification = Specification.where(new CitizenSpecification(firstSearchCriteria));

        for (int i = 1; i < searchCriterias.size(); i++) {
            var currentSearchCriteria = searchCriterias.get(i);
            var currentConnector = currentSearchCriteria.getConnector();
            var currentSpecification = new CitizenSpecification(currentSearchCriteria);

            specification = currentConnector.equals(AND) ? specification.and(currentSpecification) : specification.or(currentSpecification);
        }

        return specification;
    }

}
