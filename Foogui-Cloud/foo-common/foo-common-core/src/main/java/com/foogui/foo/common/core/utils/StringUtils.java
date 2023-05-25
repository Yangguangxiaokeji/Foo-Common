package com.foogui.foo.common.core.utils;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
    /**
     * 驼峰形式转下划线
     *
     * @param input 输入
     * @return {@link String}
     */
    public static String camelToUnderscore(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("_").append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
