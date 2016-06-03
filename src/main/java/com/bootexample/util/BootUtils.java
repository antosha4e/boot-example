package com.bootexample.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;

/**
 * Created by antosha4e on 20.05.16.
 */
public class BootUtils {
    public static boolean isNOE(String str) {
        return str == null || str.length() == 0;
    }

    public static String urlToString(String url) {
        try {
            return IOUtils.toString(new URL(url));
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}