package com.invertbit.games.gtw.b.e;

/* loaded from: classes.dex */
public final class Cb {
    private static void a(int[] iArr, int[] iArr2, int i, int i2) {
        int i3 = 0;
        int[] iArr3 = new int[2304];
        for (int i4 = 0; i4 < 2304; i4++) {
            iArr3[i4] = i4 / 9;
        }
        int[] iArr4 = new int[5];
        if (4 < i) {
            for (int i5 = 0; i5 < 5; i5++) {
                iArr4[i5] = i5;
            }
        } else {
            for (int i6 = 0; i6 < i; i6++) {
                iArr4[i6] = i6;
            }
            for (int i7 = i; i7 < 5; i7++) {
                iArr4[i7] = i - 1;
            }
        }
        int i8 = 0;
        while (true) {
            int i9 = i3;
            if (i8 < i2) {
                int i10 = iArr[i9];
                int i11 = (((i10 >> 24) & 255) * 5) + 0;
                int i12 = (((i10 >> 16) & 255) * 5) + 0;
                int i13 = (((i10 >> 8) & 255) * 5) + 0;
                int i14 = ((i10 & 255) * 5) + 0;
                for (int i15 = 1; i15 <= 4; i15++) {
                    int i16 = iArr[iArr4[i15] + i9];
                    i11 += (i16 >> 24) & 255;
                    i12 += (i16 >> 16) & 255;
                    i13 += (i16 >> 8) & 255;
                    i14 += i16 & 255;
                }
                int i17 = i13;
                int i18 = i12;
                int i19 = i11;
                int i20 = 0;
                int i21 = i14;
                int i22 = i8;
                while (i20 < i) {
                    iArr2[i22] = (iArr3[i19] << 24) | (iArr3[i18] << 16) | (iArr3[i17] << 8) | iArr3[i21];
                    int i23 = i22 + i2;
                    int i24 = i20 + 5;
                    int i25 = i24 >= i ? i - 1 : i24;
                    int i26 = i20 - 4;
                    if (i26 < 0) {
                        i26 = 0;
                    }
                    int i27 = iArr[i25 + i9];
                    int i28 = iArr[i26 + i9];
                    i19 = (i19 + ((i27 >> 24) & 255)) - ((i28 >> 24) & 255);
                    i18 = (i18 + ((i27 >> 16) & 255)) - ((i28 >> 16) & 255);
                    i17 = (i17 + ((i27 >> 8) & 255)) - ((i28 >> 8) & 255);
                    i20++;
                    i21 = ((i27 & 255) + i21) - (i28 & 255);
                    i22 = i23;
                }
                i3 = i9 + i;
                i8++;
            } else {
                return;
            }
        }
    }

    public static int[] a(int[] iArr, int i, int i2) {
        int[] iArr2 = new int[i * i2];
        int[] iArr3 = new int[i * i2];
        java.lang.System.arraycopy(iArr, 0, iArr2, 0, iArr2.length);
        for (int i3 = 0; i3 < 2; i3++) {
            a(iArr2, iArr3, i, i2);
            a(iArr3, iArr2, i2, i);
        }
        return iArr2;
    }

    public static int[] a(java.nio.ByteBuffer byteBuffer) {
        int[] iArr = new int[byteBuffer.limit() / 4];
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = ((byteBuffer.get() & 255) << 24) | ((byteBuffer.get() & 255) << 16) | ((byteBuffer.get() & 255) << 8) | (byteBuffer.get() & 255);
        }
        return iArr;
    }

    public static java.nio.ByteBuffer a(int[] iArr) {
        java.nio.ByteBuffer byteBufferA = com.badlogic.gdx.utils.BufferUtils.a(iArr.length * 4);
        for (int i : iArr) {
            byteBufferA.put((byte) (((-16777216) & i) >>> 24)).put((byte) ((16711680 & i) >>> 16)).put((byte) ((65280 & i) >>> 8)).put((byte) (i & 255));
        }
        byteBufferA.flip();
        return byteBufferA;
    }
}
