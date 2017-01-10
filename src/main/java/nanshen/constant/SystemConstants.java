/*
 * @(#)SystemConstants.java, 2015-08-01.
 *
 * Copyright 2014 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package nanshen.constant;

/**
 * SystemConstants
 *
 * @author WANG Minghao
 */
public class SystemConstants {

    /** base encode type */
    public static final String SYS_ENC = "utf-8";

    /** base domain */
    public static final String BASE_URL = "http://www.itiaopin.com";
    public static final String SHORT_BASE_URL = "http://itiaopin.com";
    public static final String IMAGE_URL = "http://static.itiaopin.com";
    public static final String CDN_URL = "http://static.itiaopin.com";

    /** default page length for lists */
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int DEFAULT_CACHED_LOOK_SIZE = 100;
    public static final int DEFAULT_CACHED_SKU_SIZE = 3 * DEFAULT_CACHED_LOOK_SIZE;

    /** OSS related */
    public static String OSS_BASE_HTTP = "http://static.itiaopin.com";
    public static String BUCKET_NAME = "tiaopin";

    /** hello Message */
    public static String HELLO_MSG_MORNING = "早上好";
    public static String HELLO_MSG_AFTERNOON = "下午好";
    public static String HELLO_MSG_EVENING = "晚上好";

    /** ShowId Offset */
    public static final int SHOWID_ANSWER = 2;
    public static final int SHOWID_QUESTION = 3;
    public static final int SHOWID_TOPIC = 4;
    public static final int SHOWID_USER = 5;

    /** weixin */
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=wxcf2c4aceba18cbd5" +
            "&secret=f6bba308f87a33c6429baa08eb8b6ed2&js_code=JSCODE&grant_type=authorization_code";

}
