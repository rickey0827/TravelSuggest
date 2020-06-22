package com.soft507.travelsuggest.util;

import android.graphics.Bitmap;

import com.soft507.travelsuggest.TravelApp;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/14 15:55
 */
public class FileUtils {

    public static File getFile(Bitmap bmp) {
        String defaultPath = TravelApp.context.getFilesDir()
                .getAbsolutePath() + "/defaultGoodInfo";
        File file = new File(defaultPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String defaultImgPath = defaultPath + "/messageImg.jpg";
        file = new File(defaultImgPath);
        try {
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 20, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
}
