package com.martinez.complaints.repository.searchcriteria;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SearchCriteria {

    public static final String OR = "|";
    public static final String AND = ",";
    private final String key;
    private final String operation;
    private final Object value;
    private final String connector;

}
