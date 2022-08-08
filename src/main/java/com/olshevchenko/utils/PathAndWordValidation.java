package com.olshevchenko.utils;

import java.io.File;

/**
 * @author Oleksandr Shevchenko
 */
public abstract class PathAndWordValidation {

    public static void validate(String path, String word) {
        File file = new File(path);
        if (!file.exists()) {
            throw new IllegalArgumentException("Path '" + path + "' is invalid.");
        }
        if (word == null) {
            throw new NullPointerException("The word is empty.");
        }
    }

}
