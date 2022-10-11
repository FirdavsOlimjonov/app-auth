package uz.pdp.payload.filterPayload.enums;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchEmployeeDTO {


    private String searchingField;
    private String employeeType;
    private Integer size;
    private Integer page;


}
