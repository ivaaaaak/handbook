package com.example.handbook.infra.tarantool.common;

import java.util.List;

public class TTConstant {
    public static final String MODIFY_ADD = "ADD";
    public static final String MODIFY_DELETE = "DELETE";

    public static final String MAIN_INFO = "MAIN_INFO";
    public static final String CLIENT_INFO1 = "CLIENT_INFO1";
    public static final String CLIENT_INFO2 = "CLIENT_INFO2";
    public static final String CLIENT_INFO3 = "CLIENT_INFO3";
    public static final String CLIENT_INFO4 = "CLIENT_INFO4";
    public static final String CLIENT_INFO5 = "CLIENT_INFO5";
    public static final String CLIENT_INFO6 = "CLIENT_INFO6";

    public static final String PROFILE_OPERATION_NEW = "NEW";
    public static final String PROFILE_OPERATION_MODIFY = "MODIFY";
    public static final String PROFILE_OPERATION_DELETE = "DELETE";

    public static final List<String> SUPPORTED_TABLES = List.of(
            MAIN_INFO,
            CLIENT_INFO1,
            CLIENT_INFO2,
            CLIENT_INFO3,
            CLIENT_INFO4,
            CLIENT_INFO5,
            CLIENT_INFO6
    );
}
