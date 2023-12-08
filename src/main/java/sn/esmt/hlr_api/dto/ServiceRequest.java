package sn.esmt.hlr_api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceRequest {
    private boolean active;
    private String serviceType;
    private String targetNumber;
}
