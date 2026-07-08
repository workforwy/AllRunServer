package com.workforwy.allrun.util;

public final class LegacyJson {

    private LegacyJson() {
    }

    public static String escape(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
