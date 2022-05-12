package com.template.chip.work.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author guozhenquan
 * @date 2022年04月13日 14:40
 */
@NoArgsConstructor
@Data
public class GaodeResultData {


    @JsonProperty("status")
    private String status;
    @JsonProperty("info")
    private String info;
    @JsonProperty("infocode")
    private String infocode;
    @JsonProperty("count")
    private String count;
    @JsonProperty("geocodes")
    private List<GeocodesDTO> geocodes;

    @NoArgsConstructor
    @Data
    public static class GeocodesDTO {
        @JsonProperty("formatted_address")
        private String formattedAddress;
        @JsonProperty("country")
        private String country;
        @JsonProperty("province")
        private String province;
        @JsonProperty("citycode")
        private String citycode;
        @JsonProperty("city")
        private String city;
        @JsonProperty("district")
        private String district;
        @JsonProperty("township")
        private List<?> township;
        @JsonProperty("neighborhood")
        private NeighborhoodDTO neighborhood;
        @JsonProperty("building")
        private BuildingDTO building;
        @JsonProperty("adcode")
        private String adcode;
        @JsonProperty("street")
        private List<?> street;
        @JsonProperty("number")
        private List<?> number;
        @JsonProperty("location")
        private String location;
        @JsonProperty("level")
        private String level;

        @NoArgsConstructor
        @Data
        public static class NeighborhoodDTO {
            @JsonProperty("name")
            private List<?> name;
            @JsonProperty("type")
            private List<?> type;
        }

        @NoArgsConstructor
        @Data
        public static class BuildingDTO {
            @JsonProperty("name")
            private List<?> name;
            @JsonProperty("type")
            private List<?> type;
        }
    }
    public Boolean isSuccess(){
        return this.getInfocode().equals("10000");
    }
}
