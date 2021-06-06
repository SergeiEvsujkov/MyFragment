package com.example.myfragment.data;

import com.example.myfragment.R;

import java.util.Random;

public class PictureIndexConverter {
    private static Random rnd = new Random();
    private static Object syncObj = new Object();
    private static int[] picIndex = {R.drawable.earth,
            R.drawable.jup,
            R.drawable.merc,
            R.drawable.mars,
            R.drawable.sat,
    };

    public static int randomPictureIndex() {
        synchronized (syncObj) {
            return rnd.nextInt(picIndex.length);
        }
    }

    public static int getPictureByIndex(int index) {
        if (index < 0 || index >= picIndex.length) {
            index = 0;
        }
        return picIndex[index];
    }

    public static int getIndexByPicture(int picture) {
        for (int i = 0; i < picIndex.length; i++) {
            if (picIndex[i] == picture) {
                return i;
            }
        }
        return 0;
    }


}
