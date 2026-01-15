package com.ht.note.webauthconfig;

/**
 * <h1>通用模块常量定义</h1>
 * */
public final class CommonConstant {

    /** 默认的 Token 超时时间, 天 */
    public static final Integer DEFAULT_EXPIRE_DAY = 30;

    /**
     * 用于签名的密钥，需要保密! 可以随意修改此值
     */
    public static final String JWT_DATA_MAP_KEY = "user-data";


    /** RSA 公钥 */
    public static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiteN7Knubw4bVERcQF9jeZb5NAgRgcQHiovOOIbkHYpIU9jArSgxQ1XH9v4FxpZXR/" +
            "xEQcHLVlxVr8GoIVoKgNZF5AIVfk5uVFEHpb/a+Zfc3tA8DixLIZfw6gMEzx5KcmMpSIb25KYUSYCK8wvJ/gbiovvG7zKPeZGhSDcR8EOJQktXGdIu7mfjfKwi2O2A9goxVI9Wj33Vy/" +
            "bD8BFKNafgsmsDBv0KzbtaaQgnMR59rgJu93qC2wHQHYV/zIDBCdoAU93Qt5C9QJ9Y6Mm674qqKFh4WismwIsK2OeTs1i9u1+zPicDQT/8HwEytA6FVnznAh/ViZTPyR3oG5xhBQIDAQAB";

    /** RSA 私钥, 除了授权中心以外, 不暴露给任何客户端 */
    public static final String PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCK143sqe5vDhtURFxAX2N5lvk0CBGBxAeKi844huQdikhT2MCtKDFDVcf2/" +
            "gXGlldH/ERBwctWXFWvwaghWgqA1kXkAhV+Tm5UUQelv9r5l9ze0DwOLEshl/DqAwTPHkpyYylIhvbkphRJgIrzC8n+BuKi+8bvMo95kaFINxHwQ4lCS1cZ0i7uZ+N8rCLY7YD2CjFUj1aPfdXL9sPwEUo1p+CyawMG/" +
            "QrNu1ppCCcxHn2uAm73eoLbAdAdhX/MgMEJ2gBT3dC3kL1An1joybrviqooWHhaKybAiwrY55OzWL27X7M+JwNBP/wfATK0DoVWfOcCH9WJlM/JHegbnGEFAgMBAAECggEAdXY5vxBhBRmFK3yOI3PP/" +
            "60ndcqhrQxAaQkwVYhhc1BrMmx9TcFkNBhdjWuJTWIhw2f8AznGInEJmSEYcebwXeFpzaWps2r9Ul4fPy4aPvEozmSTelYk1e3qxr/5EJUK3M1hvtdYB0cugKS8aTT/FeeTU9n4iqNW2L3JGgRlK8NoP3R/26wKNNaaJYnuJx6s43GiIuPj3afluJw1rsf9XMv/" +
            "lyAmjATii5iWsv0C//Nvfstfp5c+gtWvV+SMmdfC33xfkLQaXPS2p7DNSInDzEYnyZXeQCCrcSWGV4ZKIn8I6iVozAU8ciMSybMl4dZplnbCuN0OtVTv8Zyy3mrenQKBgQDBfbT7bhz7MLvJZjHKsKQxzkgktmxH9kP46Wzoe/to1nmgnf5SIY7Si6GCTqZfC/" +
            "6/cz01+sGHibVKjUYEYTaDh0tBikRIFGU7NdwsamSNMkvdqHai54iJBqCHE6O5ao+ZxbwtzjfmJlY9t+TKDosy5ZOKXJSXvYRpYLm3Au274wKBgQC3sjUdMdxWhKRDEb60CNlF6UCyMmiY/dqnmHl5Xt3Lch003ASARGdz9foMgDhRzCO4lhgoucKNe8kOQwt" +
            "iU9Zu6tl5ZmkEGckAhy3+GUFoYvc0VQIv6sBx9MWCpMx2hr4zUMHpbgD/n2agDOjt1rB1LBAlOnCkzNiTsyns3iDT9wKBgCf6djuBC+HmttYtFFqh1rsnuQZE3uibfH3glQ0QGOkXb3f9TP" +
            "/hSSY9jXhkEcIPs2oRQHN3xSbhECFAQVhEUcQpXyZCkEsoU6ZBQaW3seDupHg/B4wvNKnDQcXHDAwSdWs7TN9V/XzDLQyBg3cwPBifELYE3PrhQIuZXaXvp/krAoGBAJxPAa8j/E+KcpO3h8R+OxOoFSsMhcCZUfOGZOcH+EbjhUKzbQkQK8+" +
            "aoPj+Ke5AfKzW2xvcZVYq3LxMBGXY3QBoykDpCbc5kbXnURWEvAltHNjk9QV9BeBx3Zb7UBgTM8lKkJZmyma5ZoQOOC4ZnvE1W0+yWKOCw097BtPNd7WlAoGAQV00N+CVDGtPhx+xqryJAckh6+xUhNPwPawIKkN7m+gLW8bouC1jA/C5tvUtzbz" +
            "ibBwl8bDGEaJ6bnilaUmiPSlbKfsdbi7exTNh9ewt9SBS+pQf7hX2yJtsN4PjmisXXonDu0EqHoEsTbg7Xf/cCZhKer6ctZ5j6V0ARaqTOtI=";

}