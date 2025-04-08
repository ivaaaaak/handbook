package com.example.handbook.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AWarmupMessage {

    private Headers headers;
    private MainInfo mainInfo;
    private List<ClientInfo1> ClientInfo1s;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Headers {

        public static final String WARMUP_CACHE_TOTAL_COUNT = "WARMUP_CACHE_TOTAL_COUNT";
        public static final String WARMUP_CACHE_CURRENT_NUMBER = "WARMUP_CACHE_CURRENT_NUMBER";

        private Integer warmUpCacheTotalCount;
        private Integer warmUpCacheCurrentNumber;

        public static Headers of(Map<String, byte[]> headers) {
            return Headers.builder()
                    .warmUpCacheTotalCount((headers.get(WARMUP_CACHE_TOTAL_COUNT) != null && headers.get(WARMUP_CACHE_TOTAL_COUNT).length > 0) ?
                            Integer.valueOf(new String(headers.get(WARMUP_CACHE_TOTAL_COUNT))) : null)
                    .warmUpCacheCurrentNumber((headers.get(WARMUP_CACHE_CURRENT_NUMBER) != null && headers.get(WARMUP_CACHE_CURRENT_NUMBER).length > 0) ?
                            Integer.valueOf(new String(headers.get(WARMUP_CACHE_CURRENT_NUMBER))) : null)
                    .build();
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MainInfo {
        private Long value1;
        private String value2;
        private Boolean value3;
        private String lastChangeTime;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ClientInfo1 {
        private String uniqueValue1;
        private List<ClientInfo2> ClientInfo2s;
        private List<ClientInfo5> ClientInfo5s;
        private List<ClientInfo3> ClientInfo3s;
        private ClientInfo6 ClientInfo6;
        private String lastChangeTime;

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class ClientInfo2 {
            private String uniqueValue2;
            private Boolean value1;
            private String value2;
            private List<ClientInfo4> ClientInfo4s;
            private String lastChangeTime;

            @Data
            @Builder
            @AllArgsConstructor
            @NoArgsConstructor
            @JsonInclude(JsonInclude.Include.NON_NULL)
            public static class ClientInfo4 {
                private Integer id;
                private String value1;
                private Integer value3;
                private String value2;
                private String lastChangeTime;
            }
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class ClientInfo5 {
            private Integer id;
            private String value1;
            private String lastChangeTime;
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class ClientInfo3 {
            private Integer id;
            private String uniqueValue1;
            private String lastChangeTime;
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class ClientInfo6 {
            private Boolean value1;
            private String value2;
            private Double value3;
            private String lastChangeTime;
        }
    }
}
