/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package de.codecrafters.apaarb;

import java.util.Arrays;

/**
 * An extract of the Base64 Implementation in Java8. As this implementation differs
 * in the different java version I was forced to copy an extract of it to this library
 * to ensure that the result is always the same (without respecting the java version used.<br>
 * <p>
 * Original JavaDoc:<br>
 * This class consists exclusively of static methods for obtaining
 * encoders and decoders for the Base64 encoding scheme. The
 * implementation of this class supports the following types of Base64
 * as specified in
 * <a href="http://www.ietf.org/rfc/rfc4648.txt">RFC 4648</a> and
 * <a href="http://www.ietf.org/rfc/rfc2045.txt">RFC 2045</a>.
 * <p>
 * <ul>
 * <li><a name="basic"><b>Basic</b></a>
 * <p> Uses "The Base64 Alphabet" as specified in Table 1 of
 * RFC 4648 and RFC 2045 for encoding and decoding operation.
 * The encoder does not add any line feed (line separator)
 * character. The decoder rejects data that contains characters
 * outside the base64 alphabet.</p></li>
 * <p>
 * <li><a name="url"><b>URL and Filename safe</b></a>
 * <p> Uses the "URL and Filename safe Base64 Alphabet" as specified
 * in Table 2 of RFC 4648 for encoding and decoding. The
 * encoder does not add any line feed (line separator) character.
 * The decoder rejects data that contains characters outside the
 * base64 alphabet.</p></li>
 * <p>
 * <li><a name="mime"><b>MIME</b></a>
 * <p> Uses the "The Base64 Alphabet" as specified in Table 1 of
 * RFC 2045 for encoding and decoding operation. The encoded output
 * must be represented in lines of no more than 76 characters each
 * and uses a carriage return {@code '\r'} followed immediately by
 * a linefeed {@code '\n'} as the line separator. No line separator
 * is added to the end of the encoded output. All line separators
 * or other characters not found in the base64 alphabet table are
 * ignored in decoding operation.</p></li>
 * </ul>
 * <p>
 * <p> Unless otherwise noted, passing a {@code null} argument to a
 * method of this class will cause a {@link NullPointerException
 * NullPointerException} to be thrown.
 *
 * @author Xueming Shen
 * @since 1.8
 */

final class Base64 {

    private Base64() {
    }

    /**
     * Returns a {@link Encoder} that encodes using the
     * <a href="#basic">Basic</a> type base64 encoding scheme.
     *
     * @return A Base64 encoder.
     */
    static Encoder getEncoder() {
        return Encoder.RFC4648;
    }

    /**
     * This class implements an encoder for encoding byte data using
     * the Base64 encoding scheme as specified in RFC 4648 and RFC 2045.
     * <p>
     * <p> Instances of {@link Encoder} class are safe for use by
     * multiple concurrent threads.
     * <p>
     * <p> Unless otherwise noted, passing a {@code null} argument to
     * a method of this class will cause a
     * {@link NullPointerException NullPointerException} to
     * be thrown.
     *
     * @see Decoder
     * @since 1.8
     */
    static class Encoder {

        private final byte[] newline;
        private final int linemax;
        private final boolean isURL;
        private final boolean doPadding;

        private Encoder(final boolean isURL, final byte[] newline, final int linemax, final boolean doPadding) {
            this.isURL = isURL;
            this.newline = newline;
            this.linemax = linemax;
            this.doPadding = doPadding;
        }

        /**
         * This array is a lookup table that translates 6-bit positive integer
         * index values into their "Base64 Alphabet" equivalents as specified
         * in "Table 1: The Base64 Alphabet" of RFC 2045 (and RFC 4648).
         */
        private static final char[] toBase64 = {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
        };

        /**
         * It's the lookup table for "URL and Filename safe Base64" as specified
         * in Table 2 of the RFC 4648, with the '+' and '/' changed to '-' and
         * '_'. This table is used when BASE64_URL is specified.
         */
        private static final char[] toBase64URL = {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'
        };

        private static final int MIMELINEMAX = 76;
        private static final byte[] CRLF = new byte[]{'\r', '\n'};

        static final Encoder RFC4648 = new Encoder(false, null, -1, true);

        private int outLength(final int srclen) {
            int len = 0;
            if (doPadding) {
                len = 4 * ((srclen + 2) / 3);
            } else {
                final int n = srclen % 3;
                len = 4 * (srclen / 3) + (n == 0 ? 0 : n + 1);
            }
            if (linemax > 0)                                  // line separators
                len += (len - 1) / linemax * newline.length;
            return len;
        }

        /**
         * Encodes all bytes from the specified byte array into a newly-allocated
         * byte array using the {@link Base64} encoding scheme. The returned byte
         * array is of the length of the resulting bytes.
         *
         * @param src the byte array to encode
         * @return A newly-allocated byte array containing the resulting
         * encoded bytes.
         */
        byte[] encode(final byte[] src) {
            final int len = outLength(src.length);          // dst array size
            final byte[] dst = new byte[len];
            final int ret = encode0(src, 0, src.length, dst);
            if (ret != dst.length)
                return Arrays.copyOf(dst, ret);
            return dst;
        }

        private int encode0(final byte[] src, final int off, final int end, final byte[] dst) {
            final char[] base64 = isURL ? toBase64URL : toBase64;
            int sp = off;
            int slen = (end - off) / 3 * 3;
            final int sl = off + slen;
            if (linemax > 0 && slen > linemax / 4 * 3)
                slen = linemax / 4 * 3;
            int dp = 0;
            while (sp < sl) {
                final int sl0 = Math.min(sp + slen, sl);
                for (int sp0 = sp, dp0 = dp; sp0 < sl0; ) {
                    final int bits = (src[sp0++] & 0xff) << 16 |
                            (src[sp0++] & 0xff) << 8 |
                            (src[sp0++] & 0xff);
                    dst[dp0++] = (byte) base64[(bits >>> 18) & 0x3f];
                    dst[dp0++] = (byte) base64[(bits >>> 12) & 0x3f];
                    dst[dp0++] = (byte) base64[(bits >>> 6) & 0x3f];
                    dst[dp0++] = (byte) base64[bits & 0x3f];
                }
                final int dlen = (sl0 - sp) / 3 * 4;
                dp += dlen;
                sp = sl0;
                if (dlen == linemax && sp < end) {
                    for (final byte b : newline) {
                        dst[dp++] = b;
                    }
                }
            }
            if (sp < end) {               // 1 or 2 leftover bytes
                final int b0 = src[sp++] & 0xff;
                dst[dp++] = (byte) base64[b0 >> 2];
                if (sp == end) {
                    dst[dp++] = (byte) base64[(b0 << 4) & 0x3f];
                    if (doPadding) {
                        dst[dp++] = '=';
                        dst[dp++] = '=';
                    }
                } else {
                    final int b1 = src[sp++] & 0xff;
                    dst[dp++] = (byte) base64[(b0 << 4) & 0x3f | (b1 >> 4)];
                    dst[dp++] = (byte) base64[(b1 << 2) & 0x3f];
                    if (doPadding) {
                        dst[dp++] = '=';
                    }
                }
            }
            return dp;
        }
    }

    /**
     * This class implements a decoder for decoding byte data using the
     * Base64 encoding scheme as specified in RFC 4648 and RFC 2045.
     * <p>
     * <p> The Base64 padding character {@code '='} is accepted and
     * interpreted as the end of the encoded byte data, but is not
     * required. So if the final unit of the encoded byte data only has
     * two or three Base64 characters (without the corresponding padding
     * character(s) padded), they are decoded as if followed by padding
     * character(s). If there is a padding character present in the
     * final unit, the correct number of padding character(s) must be
     * present, otherwise {@code IllegalArgumentException} (
     * {@code IOException} when reading from a Base64 stream) is thrown
     * during decoding.
     * <p>
     * <p> Instances of {@link Decoder} class are safe for use by
     * multiple concurrent threads.
     * <p>
     * <p> Unless otherwise noted, passing a {@code null} argument to
     * a method of this class will cause a
     * {@link NullPointerException NullPointerException} to
     * be thrown.
     *
     * @see Encoder
     * @since 1.8
     */
    private static class Decoder {

        private final boolean isURL;
        private final boolean isMIME;

        private Decoder(final boolean isURL, final boolean isMIME) {
            this.isURL = isURL;
            this.isMIME = isMIME;
        }

        /**
         * Lookup table for decoding unicode characters drawn from the
         * "Base64 Alphabet" (as specified in Table 1 of RFC 2045) into
         * their 6-bit positive integer equivalents.  Characters that
         * are not in the Base64 alphabet but fall within the bounds of
         * the array are encoded to -1.
         */
        private static final int[] fromBase64 = new int[256];

        static {
            Arrays.fill(fromBase64, -1);
            for (int i = 0; i < Encoder.toBase64.length; i++)
                fromBase64[Encoder.toBase64[i]] = i;
            fromBase64['='] = -2;
        }

        /**
         * Lookup table for decoding "URL and Filename safe Base64 Alphabet"
         * as specified in Table2 of the RFC 4648.
         */
        private static final int[] fromBase64URL = new int[256];

        static {
            Arrays.fill(fromBase64URL, -1);
            for (int i = 0; i < Encoder.toBase64URL.length; i++)
                fromBase64URL[Encoder.toBase64URL[i]] = i;
            fromBase64URL['='] = -2;
        }

    }
}