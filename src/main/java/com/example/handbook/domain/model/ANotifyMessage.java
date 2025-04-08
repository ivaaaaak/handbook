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
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ANotifyMessage {

    private Notify notify;
    private Profile profile;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Notify {
        private String value2;
        private String lastChangeTime;
        private String profileOperation;
        private List<Attribute> attributes;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class Attribute {
            private String attribute;
            private String changeType;
            private String key;
            private List<Field> fields;

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            @JsonInclude(JsonInclude.Include.NON_NULL)
            public static class Field {
                private String fieldName;
                private String oldValue;
                private String newValue;
            }
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Profile {
        private MainInfo mainInfo;
        private List<ClientInfo1> ClientInfo1s;
        private String lastChangeTime;

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
            private List<ClientInfo1.ClientInfo2> ClientInfo2s;
            private List<ClientInfo1.ClientInfo5> ClientInfo5s;
            private List<ClientInfo1.ClientInfo3> ClientInfo3s;
            private ClientInfo1.ClientInfo6 ClientInfo6;
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
                private List<ClientInfo1.ClientInfo2.ClientInfo4> ClientInfo4s;
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
}
