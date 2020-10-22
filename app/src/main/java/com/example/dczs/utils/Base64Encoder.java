package com.example.dczs.utils;

import java.io.IOException;

public class Base64Encoder {
    private static final char last2byte = (char) Integer
            .parseInt("00000011", 2);
    private static final char last4byte = (char) Integer
            .parseInt("00001111", 2);
    private static final char last6byte = (char) Integer
            .parseInt("00111111", 2);
    private static final char lead6byte = (char) Integer
            .parseInt("11111100", 2);
    private static final char lead4byte = (char) Integer
            .parseInt("11110000", 2);
    private static final char lead2byte = (char) Integer
            .parseInt("11000000", 2);
    private static final char[] encodeTable = new char[] { 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
            'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
            'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/' };

    /**
     * Decodes a 7 bit Base64 character into its binary value.
     */
    private static int valueDecoding[] = new int[128];


    /**
     * Base64 encoding.
     *
     * @param from
     *            The src data.
     *  数据编码
     * @return
     */
    public static String encode(byte[] from) {
        StringBuffer to = new StringBuffer((int) (from.length * 1.34) + 3);
        int num = 0;
        char currentByte = 0;
        for (int i = 0; i < from.length; i++) {
            num = num % 8;
            while (num < 8) {
                switch (num) {
                    case 0:
                        currentByte = (char) (from[i] & lead6byte);
                        currentByte = (char) (currentByte >>> 2);
                        break;
                    case 2:
                        currentByte = (char) (from[i] & last6byte);
                        break;
                    case 4:
                        currentByte = (char) (from[i] & last4byte);
                        currentByte = (char) (currentByte << 2);
                        if ((i + 1) < from.length) {
                            currentByte |= (from[i + 1] & lead2byte) >>> 6;
                        }
                        break;
                    case 6:
                        currentByte = (char) (from[i] & last2byte);
                        currentByte = (char) (currentByte << 4);
                        if ((i + 1) < from.length) {
                            currentByte |= (from[i + 1] & lead4byte) >>> 4;
                        }
                        break;
                }
                to.append(encodeTable[currentByte]);
                num += 6;
            }
        }
        if (to.length() % 4 != 0) {
            for (int i = 4 - to.length() % 4; i > 0; i--) {
                to.append("=");
            }
        }
        return to.toString();
    }


    /**
     * Converts a Base64 encoded string to a byte array.
     *
     * @param encoded
     *            Base64 encoded data
     * @return decode binary data; 3 bytes for every 4 chars - minus padding
     * @exception IOException
     *                is thrown, if an I/O error occurs reading the data
     *                数据解码
     */
    public static byte[] decode(String encoded) throws IOException {
        return decode(encoded, 0, encoded.length());
    }

    /**
     * Converts an embedded Base64 encoded string to a byte array.
     *
     * @param encoded
     *            a String with Base64 data embedded in it
     * @param offset
     *            which char of the String to start at
     * @param length
     *            how many chars to decode; must be a multiple of 4
     * @return decode binary data; 3 bytes for every 4 chars - minus padding
     * @exception IOException
     *                is thrown, if an I/O error occurs reading the data
     */
    public static byte[] decode(String encoded, int offset, int length)
            throws IOException {
        int i;
        int decodedLen;
        byte[] decoded;

        // the input must be a multiple of 4
        if (length % 4 != 0) {
            throw new IOException("Base64 string length is not multiple of 4");
        }

        // 4 chars for 3 bytes, but there may have been pad bytes
        decodedLen = length / 4 * 3;
        if (encoded.charAt(offset + length - 1) == '=') {
            decodedLen--;
            if (encoded.charAt(offset + length - 2) == '=') {
                decodedLen--;
            }
        }

        decoded = new byte[decodedLen];
        for (i = 0, decodedLen = 0; i < length; i += 4, decodedLen += 3) {
            decodeQuantum(encoded.charAt(offset + i), encoded.charAt(offset + i
                    + 1), encoded.charAt(offset + i + 2), encoded.charAt(offset
                    + i + 3), decoded, decodedLen);
        }

        return decoded;
    }

    /**
     * Decode 4 Base64 chars as 1, 2, or 3 bytes of data.
     *
     * @param in1
     *            first char of quantum to decode
     * @param in2
     *            second char of quantum to decode
     * @param in3
     *            third char of quantum to decode
     * @param in4
     *            forth char of quantum to decode
     * @param out
     *            buffer to put the output in
     * @param outOffset
     *            where in the output buffer to put the bytes
     */
    private static void decodeQuantum(char in1, char in2, char in3, char in4,
                                      byte[] out, int outOffset) throws IOException {
        int a = 0, b = 0, c = 0, d = 0;
        int pad = 0;

        a = valueDecoding[in1 & 127];
        b = valueDecoding[in2 & 127];

        if (in4 == '=') {
            pad++;
            if (in3 == '=') {
                pad++;
            } else {
                c = valueDecoding[in3 & 127];
            }
        } else {
            c = valueDecoding[in3 & 127];
            d = valueDecoding[in4 & 127];
        }

        if (a < 0 || b < 0 || c < 0 || d < 0) {
            throw new IOException("Invalid character in Base64 string");
        }

        // the first byte is the 6 bits of a and 2 bits of b
        out[outOffset] = (byte) (((a << 2) & 0xfc) | ((b >>> 4) & 3));

        if (pad < 2) {
            // the second byte is 4 bits of b and 4 bits of c
            out[outOffset + 1] = (byte) (((b << 4) & 0xf0) | ((c >>> 2) & 0xf));

            if (pad < 1) {
                // the third byte is 2 bits of c and 4 bits of d
                out[outOffset + 2] = (byte) (((c << 6) & 0xc0) | (d & 0x3f));
            }
        }
    }
}
