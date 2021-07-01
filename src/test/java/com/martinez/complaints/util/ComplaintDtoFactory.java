package com.martinez.complaints.util;

import com.martinez.complaints.dto.CitizenDto;
import com.martinez.complaints.dto.ComplaintDto;

import java.util.Map;
import java.util.Random;

import static net.andreinc.mockneat.unit.address.Addresses.addresses;
import static net.andreinc.mockneat.unit.text.Strings.strings;
import static net.andreinc.mockneat.unit.types.Doubles.doubles;
import static net.andreinc.mockneat.unit.types.Longs.longs;

public class ComplaintDtoFactory {

    public static final String ADDRESS = "address";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String COMPLAINT_TYPE = "complainttype";
    public static final String COMMENTARY = "commentary";
    public static final String CITIZEN_ID = "citizenid";
    public static final String CITIZEN_DTO = "citizendto";


    public static ComplaintDto createComplaintDto() {
        return ComplaintDto.builder()
                           .address(addresses().val())
                           .latitude(doubles().range(1.0, 90.0).val())
                           .longitude(doubles().range(1.0, 180.0).val())
                           .complaintType(ComplaintType.values()[new Random().nextInt(DocumentType.values().length)])
                           .commentary(strings().size(200).val())
                           .citizenId(longs().get())
                           .build();
    }

    public static ComplaintDto createComplaintDtoWithField(String field, Object value) {
        var complaintDto = ComplaintDto.builder()
                                       .address(addresses().val())
                                       .latitude(doubles().range(1.0, 90.0).val())
                                       .longitude(doubles().range(1.0, 180.0).val())
                                       .complaintType(ComplaintType.values()[new Random().nextInt(DocumentType.values().length)])
                                       .commentary(strings().size(200).val())
                                       .citizenId(longs().get())
                                       .build();

        switch (field.toLowerCase()) {
            case ADDRESS -> complaintDto.setAddress((String) value);
            case LATITUDE -> complaintDto.setLatitude((Double) value);
            case LONGITUDE -> complaintDto.setLongitude((Double) value);
            case COMPLAINT_TYPE -> complaintDto.setComplaintType(ComplaintType.valueOf((String) value));
            case COMMENTARY -> complaintDto.setCommentary((String) value);
            case CITIZEN_ID -> complaintDto.setCitizenId((Long) value);
            case CITIZEN_DTO -> complaintDto.setCitizenDto((CitizenDto) value);
            default -> throw new UnsupportedOperationException("[{" + field + "}] is not a valid field");
        }

        return complaintDto;
    }

    public static ComplaintDto createComplaintDtoWithFields(Map<String, Object> fields) {
        var complaintDto = ComplaintDto.builder()
                                       .address(addresses().val())
                                       .latitude(doubles().range(1.0, 90.0).val())
                                       .longitude(doubles().range(1.0, 180.0).val())
                                       .complaintType(ComplaintType.values()[new Random().nextInt(ComplaintType.values().length)])
                                       .commentary(strings().size(200).val())
                                       .citizenId(longs().get())
                                       .build();

        for ( Map.Entry<String, Object> entry : fields.entrySet()) {
            var key = entry.getKey();
            var value = entry.getValue();

            switch (key.toLowerCase()) {
                case ADDRESS -> complaintDto.setAddress((String) value);
                case LATITUDE -> complaintDto.setLatitude((Double) value);
                case LONGITUDE -> complaintDto.setLongitude((Double) value);
                case COMPLAINT_TYPE -> complaintDto.setComplaintType(ComplaintType.valueOf((String) value));
                case COMMENTARY -> complaintDto.setCommentary((String) value);
                case CITIZEN_ID -> complaintDto.setCitizenId((Long) value);
                case CITIZEN_DTO -> complaintDto.setCitizenDto((CitizenDto) value);
                default -> throw new UnsupportedOperationException("[{" + key + "}] is not a valid field");
            }
        }

        return complaintDto;
    }


}
