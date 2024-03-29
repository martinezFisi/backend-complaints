package com.martinez.complaints.repository.searchcriteria;

import com.martinez.complaints.exception.EmptySearchCriteriaListException;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.martinez.complaints.repository.searchcriteria.SearchCriteria.AND;

public class SpecificationBuilder<T> {

    private final String searchCriteriaExceptionMessage;
    private final List<SearchCriteria> searchCriterias;

    public SpecificationBuilder(ResourceBundle resourceBundle) {
        searchCriterias = new ArrayList<>();
        searchCriteriaExceptionMessage = resourceBundle.getString("exception.search.criterias.is.empty");
    }

    public SpecificationBuilder<T> with(SearchCriteria searchCriteria) {
        searchCriterias.add(searchCriteria);
        return this;
    }

    public Specification<T> build() {
        if (searchCriterias.isEmpty()) {
            throw new EmptySearchCriteriaListException(searchCriteriaExceptionMessage);
        }

        var firstSearchCriteria = searchCriterias.get(0);
        var specification = Specification.where(new DefaultSpecification<T>(firstSearchCriteria));

        for (int i = 1; i < searchCriterias.size(); i++) {
            var currentSearchCriteria = searchCriterias.get(i);
            var currentConnector = currentSearchCriteria.getConnector();
            var currentSpecification = new DefaultSpecification<T>(currentSearchCriteria);

            specification = currentConnector.equals(AND) ? specification.and(currentSpecification) : specification.or(currentSpecification);
        }

        return specification;
    }

}
