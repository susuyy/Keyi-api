package com.ht.note.utils;

import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;

public class UploadBase64Util {

    /**
     * 移除 原生base64字符串前缀
     * @param dataBase64Str
     * @return
     */
    public static String removePrefix(String dataBase64Str){
        int index = dataBase64Str.indexOf("base64,")+7;
        return dataBase64Str.substring(index);
    }

    /**
     * 传入移除前缀后的base64字符串 获取byte字节数组
     * @param dataBase64RmPrefixStr
     * @return
     * @throws Exception
     */
    public static byte[] base64GetByteArray(String dataBase64RmPrefixStr) throws Exception {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] byteArray = decoder.decodeBuffer(dataBase64RmPrefixStr);
        for (int i = 0; i < byteArray.length; ++i) {
            if (byteArray[i] < 0) {// 调整异常数据
                byteArray[i] += 256;
            }
        }
        return byteArray;
    }

    /**
     * 字节数组 转化流
     * @param bytes
     * @return
     */
    public static ByteArrayInputStream getByteArrayInputStream(byte[] bytes){
        return new ByteArrayInputStream(bytes);
    }
}
