/*
 * Copyright (c) 2014-2015 Janith Bandara, This source is a part of
 * Audit4j - An open source auditing framework.
 * http://audit4j.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.audit4j.core.util;

/**
 * The Class Base64Coder.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 1.0
 */
public class Base64Coder {

   /** Holds The Constant LINE_SEPARATOR Value. */
   public static final String LINE_SEPARATOR = "line.separator";

   // The line separator string of the operating system.
   /** Holds The Constant LINE_SEPERATOR Value. */
   private static final String LINE_SEPERATOR = System.getProperty(LINE_SEPARATOR);

   /** The Constant CONSTANT_3. */
   public static final int CONSTANT_3 = 3;

   /** The Constant CONSTANT_4. */
   public static final int CONSTANT_4 = 4;

   /** The Constant CONSTANT_6. */
   public static final int CONSTANT_6 = 6;

   /** The Constant CONSTANT_64. */
   public static final int CONSTANT_64 = 64;

   /** The Constant CONSTANT_76. */
   public static final int CONSTANT_76 = 76;

   /** The Constant CONSTANT_127. */
   public static final int CONSTANT_127 = 127;

   /** The Constant CONSTANT_128. */
   public static final int CONSTANT_128 = 128;

   /** The Constant CONSTANT_0XFF. */
   public static final int CONSTANT_0XFF = 0xff;

   /** The Constant CONSTANT_0XF. */
   public static final int CONSTANT_0XF = 0xf;

   /** The Constant CONSTANT_0X3F. */
   public static final int CONSTANT_0X3F = 0x3F;

   // Mapping table from 6-bit nibbles to Base64 characters.
   /** Holds a map1 Value. */
   private static char[] map1 = new char[CONSTANT_64];
   static {
       int var = 0;
       for (char c = 'A'; c <= 'Z'; c++) {
           map1[var++] = c;
       }
       for (char c = 'a'; c <= 'z'; c++) {
           map1[var++] = c;
       }
       for (char c = '0'; c <= '9'; c++) {
           map1[var++] = c;
       }
       map1[var++] = '+';
       map1[var++] = '/';
   }

   // Mapping table from Base64 characters to 6-bit nibbles.
   /** Holds a map2 Value. */
   private static byte[] map2 = new byte[CONSTANT_128];
   static {
       for (int i = 0; i < map2.length; i++) {
           map2[i] = -1;
       }
       for (int i = 0; i < CONSTANT_64; i++) {
           map2[map1[i]] = (byte) i;
       }
   }

   /**
    * Instantiates a new base64 coder.
    */
   private Base64Coder() {

   }

   /**
    * Encode string.
    * 
    * @param text the text
    * @return the string
    */
   public static String encodeString(final String text) {
       return new String(encode(text.getBytes()));
   }

   /**
    * Encode lines.
    * 
    * @param input the input
    * @return the string
    */
   public static String encodeLines(final byte[] input) {
       return encodeLines(input, 0, input.length, CONSTANT_76, LINE_SEPERATOR);
   }

   /**
    * Encode lines.
    * 
    * @param input the input
    * @param iOff the i off
    * @param iLen the i len
    * @param lineLen the line len
    * @param lineSeparator the line separator
    * @return the string
    */
   public static String encodeLines(final byte[] input, final int iOff, final int iLen, final int lineLen,
           final String lineSeparator) {
       final int blockLen = (lineLen * CONSTANT_3) / CONSTANT_4;
       if (blockLen <= 0) {
           throw new IllegalArgumentException();
       }
       final int lines = (iLen + blockLen - 1) / blockLen;
       final int bufLen = ((iLen + 2) / CONSTANT_3) * CONSTANT_4 + lines * lineSeparator.length();
       final StringBuilder buf = new StringBuilder(bufLen);
       int ipValue = 0;
       while (ipValue < iLen) {
           final int length = Math.min(iLen - ipValue, blockLen);
           buf.append(encode(input, iOff + ipValue, length));
           buf.append(lineSeparator);
           ipValue += length;
       }
       return buf.toString();
   }

   /**
    * Encode.
    * 
    * @param input the input
    * @return the char[]
    */
   public static char[] encode(final byte[] input) {
       return encode(input, 0, input.length);
   }

   /**
    * Encode.
    * 
    * @param input the input
    * @param iLen the i len
    * @return the char[]
    */
   public static char[] encode(final byte[] input, final int iLen) {
       return encode(input, 0, iLen);
   }

   /**
    * Encode.
    * 
    * @param input the input
    * @param iOff the i off
    * @param iLen the i len
    * @return the char[]
    */
   public static char[] encode(final byte[] input, final int iOff, final int iLen) {
       final int oDataLen = (iLen * CONSTANT_4 + 2) / CONSTANT_3; // output length without padding
       final int oLen = ((iLen + 2) / CONSTANT_3) * CONSTANT_4; // output length including padding
       char[] out = new char[oLen];
       int ipValue = iOff;
       final int iEnd = iOff + iLen;
       int opValue = 0;
       while (ipValue < iEnd) {
           final int i0Value = input[ipValue++] & CONSTANT_0XFF;
           final int i1Value = ipValue < iEnd ? input[ipValue++] & CONSTANT_0XFF : 0;
           final int i2Value = ipValue < iEnd ? input[ipValue++] & CONSTANT_0XFF : 0;
           final int o0Value = i0Value >>> 2;
           final int o1Value = ((i0Value & CONSTANT_3) << CONSTANT_4) | (i1Value >>> CONSTANT_4);
           final int o2Value = ((i1Value & CONSTANT_0XF) << 2) | (i2Value >>> CONSTANT_6);
           final int o3Value = i2Value & CONSTANT_0X3F;
           out[opValue++] = map1[o0Value];
           out[opValue++] = map1[o1Value];
           out[opValue] = opValue < oDataLen ? map1[o2Value] : '=';
           opValue++;
           out[opValue] = opValue < oDataLen ? map1[o3Value] : '=';
           opValue++;
       }
       return out;
   }

   /**
    * Decode string.
    * 
    * @param text the text
    * @return the string
    */
   public static String decodeString(final String text) {
       return new String(decode(text));
   }

   /**
    * Decode lines.
    * 
    * @param text the text
    * @return the byte[]
    */
   public static byte[] decodeLines(final String text) {
       char[] buf = new char[text.length()];
       int pValue = 0;
       for (int ip = 0; ip < text.length(); ip++) {
           final char cValue = text.charAt(ip);
           if (cValue != ' ' && cValue != '\r' && cValue != '\n' && cValue != '\t') {
               buf[pValue++] = cValue;
           }
       }
       return decode(buf, 0, pValue);
   }

   /**
    * Decode.
    * 
    * @param text the text
    * @return the byte[]
    */
   public static byte[] decode(final String text) {
       return decode(text.toCharArray());
   }

   /**
    * Decode.
    * 
    * @param input the input
    * @return the byte[]
    */
   public static byte[] decode(final char[] input) {
       return decode(input, 0, input.length);
   }

   /**
    * Decode.
    * 
    * @param input the input
    * @param iOff the i off
    * @param iLen the i len
    * @return the byte[]
    */
   public static byte[] decode(final char[] input, final int iOff, final int iLen) {
       int tiLen = iLen;
       if (tiLen % CONSTANT_4 != 0) {
           throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
       }
       while (tiLen > 0 && input[iOff + tiLen - 1] == '=') {
           tiLen--;
       }
       final int oLen = (tiLen * CONSTANT_3) / CONSTANT_4;
       byte[] out = new byte[oLen];
       int ipValue = iOff;
       final int iEnd = iOff + tiLen;
       int opValue = 0;
       while (ipValue < iEnd) {
           final int i0Value = input[ipValue++];
           final int i1Value = input[ipValue++];
           final int i2Value = ipValue < iEnd ? input[ipValue++] : 'A';
           final int i3Value = ipValue < iEnd ? input[ipValue++] : 'A';
           if (i0Value > CONSTANT_127 || i1Value > CONSTANT_127 || i2Value > CONSTANT_127 || i3Value > CONSTANT_127) {
               throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
           }
           final int b0Value = map2[i0Value];
           final int b1Value = map2[i1Value];
           final int b2Value = map2[i2Value];
           final int b3Value = map2[i3Value];
           if (b0Value < 0 || b1Value < 0 || b2Value < 0 || b3Value < 0) {
               throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
           }
           final int o0Value = (b0Value << 2) | (b1Value >>> CONSTANT_4);
           final int o1Value = ((b1Value & CONSTANT_0XF) << CONSTANT_4) | (b2Value >>> 2);
           final int o2Value = ((b2Value & CONSTANT_3) << CONSTANT_6) | b3Value;
           out[opValue++] = (byte) o0Value;
           if (opValue < oLen) {
               out[opValue++] = (byte) o1Value;
           }
           if (opValue < oLen) {
               out[opValue++] = (byte) o2Value;
           }
       }
       return out;
   }
}
