package com.foogui.foo.common.core.constant;

/**
 * 网关过滤器顺序约定
 *
 * @author Foogui
 * @date 2023/05/05
 */
public class FilterOrderConstant {


    public static final int PREPROCESS_FILTER_ORDER=-100;
    public static final int BLACKLIST_FILTER_ORDER=-50;

    public static final int PASSWORD_FILTER_ORDER=-30;
    public static final int AUTHORIZE_FILTER_ORDER=0;
}
