package com.template.chip.work.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URLEncoder;
import java.util.List;

/**
 * @author guozhenquan
 * @date 2022年04月13日 14:40
 */
@NoArgsConstructor
@Data
public class GaodePoiResultData {

    @JsonProperty("suggestion")
    private SuggestionDTO suggestion;
    @JsonProperty("count")
    private String count;
    @JsonProperty("infocode")
    private String infocode;
    @JsonProperty("pois")
    private List<PoisDTO> pois;
    @JsonProperty("status")
    private String status;
    @JsonProperty("info")
    private String info;

    @NoArgsConstructor
    @Data
    public static class SuggestionDTO {
        @JsonProperty("keywords")
        private List<?> keywords;
        @JsonProperty("cities")
        private List<?> cities;
    }

    @NoArgsConstructor
    @Data
    public static class PoisDTO {
        @JsonProperty("parent")
        private List<?> parent;
        @JsonProperty("distance")
        private List<?> distance;
        @JsonProperty("pcode")
        private String pcode;
        @JsonProperty("importance")
        private List<?> importance;
        @JsonProperty("biz_ext")
        private BizExtDTO bizExt;
        @JsonProperty("recommend")
        private String recommend;
        @JsonProperty("type")
        private String type;
        @JsonProperty("photos")
        private List<PhotosDTO> photos;
        @JsonProperty("discount_num")
        private String discountNum;
        @JsonProperty("gridcode")
        private String gridcode;
        @JsonProperty("typecode")
        private String typecode;
        @JsonProperty("shopinfo")
        private String shopinfo;
        @JsonProperty("poiweight")
        private List<?> poiweight;
        @JsonProperty("citycode")
        private String citycode;
        @JsonProperty("adname")
        private String adname;
        @JsonProperty("children")
        private List<ChildrenDTO> children;
        @JsonProperty("alias")
        private List<?> alias;
        @JsonProperty("tel")
        private List<?> tel;
        @JsonProperty("id")
        private String id;
        @JsonProperty("tag")
        private List<?> tag;
        @JsonProperty("event")
        private List<?> event;
        @JsonProperty("entr_location")
        private String entrLocation;
        @JsonProperty("indoor_map")
        private String indoorMap;
        @JsonProperty("email")
        private List<?> email;
        @JsonProperty("timestamp")
        private String timestamp;
        @JsonProperty("website")
        private List<?> website;
        @JsonProperty("address")
        private String address;
        @JsonProperty("adcode")
        private String adcode;
        @JsonProperty("pname")
        private String pname;
        @JsonProperty("biz_type")
        private List<?> bizType;
        @JsonProperty("cityname")
        private String cityname;
        @JsonProperty("postcode")
        private List<?> postcode;
        @JsonProperty("match")
        private String match;
        @JsonProperty("business_area")
        private String businessArea;
        @JsonProperty("indoor_data")
        private IndoorDataDTO indoorData;
        @JsonProperty("childtype")
        private List<?> childtype;
        @JsonProperty("exit_location")
        private List<?> exitLocation;
        @JsonProperty("name")
        private String name;
        @JsonProperty("location")
        private String location;
        @JsonProperty("shopid")
        private List<?> shopid;
        @JsonProperty("navi_poiid")
        private String naviPoiid;
        @JsonProperty("groupbuy_num")
        private String groupbuyNum;

        @NoArgsConstructor
        @Data
        public static class BizExtDTO {
            @JsonProperty("cost")
            private List<?> cost;
            @JsonProperty("rating")
            private List<?> rating;
        }

        @NoArgsConstructor
        @Data
        public static class IndoorDataDTO {
            @JsonProperty("cmsid")
            private List<?> cmsid;
            @JsonProperty("truefloor")
            private List<?> truefloor;
            @JsonProperty("cpid")
            private List<?> cpid;
            @JsonProperty("floor")
            private List<?> floor;
        }

        @NoArgsConstructor
        @Data
        public static class PhotosDTO {
            @JsonProperty("title")
            private String title;
            @JsonProperty("url")
            private String url;
        }

        @NoArgsConstructor
        @Data
        public static class ChildrenDTO {
            @JsonProperty("typecode")
            private String typecode;
            @JsonProperty("address")
            private String address;
            @JsonProperty("distance")
            private String distance;
            @JsonProperty("subtype")
            private String subtype;
            @JsonProperty("sname")
            private String sname;
            @JsonProperty("name")
            private String name;
            @JsonProperty("location")
            private String location;
            @JsonProperty("id")
            private String id;
        }
    }
    public Boolean isSuccess(){
       return this.getInfocode().equals("10000");
    }
}
