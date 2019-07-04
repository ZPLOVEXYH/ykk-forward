package cn.samples.datareceiver.utils;

import java.io.UnsupportedEncodingException;

public class StringUtils {

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    /**
     * 16进制字符串转成字符串
     *
     * @param hexString
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String fromHexString(String hexString) throws UnsupportedEncodingException {
        // 用于接收转换结果
        String result = "";
        // 16进制字符
        String hexDigital = "0123456789ABCDEF";
        // 将16进制字符串转换成char数组
        char[] hexs = hexString.toCharArray();
        // 能被16整除，肯定可以被2整除
        byte[] bytes = new byte[hexString.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = hexDigital.indexOf(hexs[2 * i]) * 16 + hexDigital.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        result = new String(bytes, "UTF-8");

        return result;
    }
}
