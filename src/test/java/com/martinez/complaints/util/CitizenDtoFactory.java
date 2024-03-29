package com.martinez.complaints.util;

import com.martinez.complaints.dto.CitizenDto;

import java.util.Map;
import java.util.Random;

import static net.andreinc.mockneat.unit.text.Strings.strings;
import static net.andreinc.mockneat.unit.types.Ints.ints;
import static net.andreinc.mockneat.unit.user.Emails.emails;
import static net.andreinc.mockneat.unit.user.Names.names;
import static net.andreinc.mockneat.unit.user.Passwords.passwords;

public class CitizenDtoFactory {

    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String DOCUMENT_TYPE = "documenttype";
    public static final String DOCUMENT_NUMBER = "documentnumber";
    public static final String FIRST_NAME = "firstname";
    public static final String LAST_NAME = "lastname";
    public static final String AGE = "age";


    public static CitizenDto createCitizenDto() {
        return CitizenDto.builder()
                         .email(emails().val())
                         .phoneNumber(strings().size(11).val())
                         .password(passwords().val())
                         .documentType(DocumentType.values()[new Random().nextInt(DocumentType.values().length-1)])
                         .documentNumber(strings().size(8).val())
                         .firstName(names().first().val())
                         .lastName(names().last().val())
                         .age(ints().range(18, 200).val())
                         .build();
    }

    public static CitizenDto createCitizenDtoWithField(String field, Object value) {
        var citizenDto = createCitizenDto();

        switch (field.toLowerCase()) {
            case EMAIL -> citizenDto.setEmail((String) value);
            case PASSWORD -> citizenDto.setPassword((String) value);
            case DOCUMENT_TYPE -> citizenDto.setDocumentType(DocumentType.valueOf((String) value));
            case DOCUMENT_NUMBER -> citizenDto.setDocumentNumber((String) value);
            case FIRST_NAME -> citizenDto.setFirstName((String) value);
            case LAST_NAME -> citizenDto.setLastName((String) value);
            case AGE -> citizenDto.setAge((Integer) value);
            default -> throw new UnsupportedOperationException("[{" + field + "}] is not a valid field");
        }

        return citizenDto;
    }

    public static CitizenDto createCitizenDtoWithFields(Map<String, Object> fields) {
        var citizenDto = createCitizenDto();

        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            var key = entry.getKey();
            var value = entry.getValue();

            switch (key.toLowerCase()) {
                case EMAIL -> citizenDto.setEmail((String) value);
                case PASSWORD -> citizenDto.setPassword((String) value);
                case DOCUMENT_TYPE -> citizenDto.setDocumentType(DocumentType.valueOf((String) value));
                case DOCUMENT_NUMBER -> citizenDto.setDocumentNumber((String) value);
                case FIRST_NAME -> citizenDto.setFirstName((String) value);
                case LAST_NAME -> citizenDto.setLastName((String) value);
                case AGE -> citizenDto.setAge((Integer) value);
                default -> throw new UnsupportedOperationException("[{" + key + "}] is not a valid field");
            }
        }

        return citizenDto;
    }

}
