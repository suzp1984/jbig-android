package io.github.suzp1984.jbigandroid.utils;

/**
 * Created by moses on 8/27/15.
 */
public class ByteUtils {

    public static String byteArray2HexString(byte[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }

        StringBuilder sbd = new StringBuilder();
        for (byte b : arr) {
            String tmp = Integer.toHexString(0xFF & b);
            if (tmp.length() < 2) {
                tmp = "0" + tmp;
            }

            sbd.append(tmp);
        }

        return sbd.toString();
    }

    public static byte[] hexString2ByteArray(String hexStr) {
        if (hexStr == null) {
            return null;
        }

        if (hexStr.length() % 2 != 0) {
            return null;
        }
        byte[] data = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            char hc = hexStr.charAt(2 * i);
            char lc = hexStr.charAt(2 * i + 1);
            byte hb = hexChar2Byte(hc);
            byte lb = hexChar2Byte(lc);

            if (hb < 0 || lb < 0) {
                return null;
            }

            int n = hb << 4;
            data[i] = (byte) (n + lb);
        }

        return data;
    }

    public static byte hexChar2Byte(char c) {
        if (c >= '0' && c <= '9') {
            return (byte) (c - '0');
        } else if (c >= 'a' && c <= 'f') {
            return (byte) (c - 'a' + 10);
        } else if (c >= 'A' && c <= 'F') {
            return (byte) (c - 'A' + 10);
        } else {
            return -1;
        }
    }
}
