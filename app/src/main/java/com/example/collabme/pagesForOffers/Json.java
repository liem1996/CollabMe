package com.example.collabme.pagesForOffers;

/*
 * Copyright 2020 Google Inc.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 */

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * Utility class to work with JSON content stored locally.
 */
public class Json {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static JSONArray readFromFile(Context context, String fileName) {
        try {
            final InputStream inputStream = context.getAssets().open(fileName);
            return readFromInputStream(inputStream);

        } catch (Exception e) {
            return new JSONArray();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static JSONArray readFromResources(Context context, int resource) {
        try {
            final InputStream inputStream = context.getResources().openRawResource(resource);
            return readFromInputStream(inputStream);

        } catch (Exception e) {
            return new JSONArray();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static JSONArray readFromInputStream(InputStream inputStream) throws JSONException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        final String inputString = reader.lines().collect(Collectors.joining());
        return new JSONArray(inputString);
    }
}