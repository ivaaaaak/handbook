package com.example.handbook.domain.common;

import com.example.handbook.domain.model.ANotifyMessage;
import com.example.handbook.domain.model.BNotifyMessage;

import java.util.Map;

public class Constants {
    public final static String A = "A";
    public final static String B = "B";

    public static final Map<String, Class<?>> TYPE_TO_OBJECT_MAP = Map.of(
            A, ANotifyMessage.class,
            B, BNotifyMessage.class
    );
}
