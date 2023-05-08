package com.maor.localstorage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;


public class LocalStorage {

    private String fileName;
    private static LocalStorage me;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private String secretKey;

    private LocalStorage(Context context, String fileName, String secretKey) {
        this.gson = new Gson();
        this.fileName = fileName;
        this.secretKey = secretKey;
        this.sharedPreferences = context.getApplicationContext().getSharedPreferences(this.fileName,
                Context.MODE_PRIVATE);
    }

    public static LocalStorage init(Context context, String fileName, String secretKey) {
        if (me == null) {
            me = new LocalStorage(context, fileName, secretKey);
        }

        return me;
    }

    public static LocalStorage getMe() {
        return me;
    }

    public String getFileName() {
        return fileName;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveData(String KEY, String value) throws Exception {
        String _value = EncryptionUtils.encrypt(value, this.secretKey);
        sharedPreferences.edit().putString(KEY, _value).apply();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getData(String KEY, String defValue) throws Exception {
        String _value = sharedPreferences.getString(KEY, defValue);
        if (_value.equals(defValue))
            return _value;
        return EncryptionUtils.decrypt(_value, this.secretKey);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void putString(String KEY, String value) throws Exception {
        saveData(KEY, value);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getString(String KEY, String defValue) throws Exception {
        return getData(KEY, defValue);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void putDouble(String KEY, double value) throws Exception {
        saveData(KEY, String.valueOf(value));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public double getDouble(String KEY, double defValue) throws Exception {
        return Double.parseDouble(getData(KEY, String.valueOf(defValue)));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void putInt(String KEY, int value) throws Exception {
        saveData(KEY, value + "");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getInt(String KEY, int defValue) throws Exception {
        return Integer.parseInt(getData(KEY, defValue + ""));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public <T> void putObject(String KEY, T object) throws Exception {
        String value = this.gson.toJson(object);
        saveData(KEY, value);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public <T> T getObject(String KEY, Class<T> classType) throws Exception {
        String value = getData(KEY, "");
        if(value.equals(""))
            return null;

        return this.gson.fromJson(value, classType);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public <T> void putArray (String KEY, T[] array) throws Exception {
        String value = this.gson.toJson(array);
        saveData(KEY, value);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public <T> T[] getArray(String KEY, Class<T[]> classType) throws Exception {
        String value = getData(KEY, "");

        if(value.equals(""))
            return null;

        return gson.fromJson(value, classType);
    }
}
