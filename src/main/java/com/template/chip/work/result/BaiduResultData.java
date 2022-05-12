package com.template.chip.work.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author guozhenquan
 * @date 2022年04月13日 14:40
 */
@NoArgsConstructor
@Data
public class BaiduResultData {


    @JsonProperty("status")
    private String status;
    @JsonProperty("result")
    private ResultDTO result;

    @NoArgsConstructor
    @Data
    public static class ResultDTO {
        @JsonProperty("location")
        private LocationDTO location;
        @JsonProperty("precise")
        private Integer precise;
        @JsonProperty("confidence")
        private Integer confidence;
        @JsonProperty("level")
        private String level;

        @NoArgsConstructor
        @Data
        public static class LocationDTO {
            @JsonProperty("lng")
            private Double lng;
            @JsonProperty("lat")
            private Double lat;
        }
    }
    public Boolean isSuccess(){
        return this.getStatus().equals("OK");
    }
}
