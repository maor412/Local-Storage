package com.maor.localstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String myFileName = "MY_LOCAL_DB";
            String mySecretKey = "MySecretPassword123";

            LocalStorage ref =  LocalStorage.init(this, myFileName, mySecretKey);
            try{
                // String
                ref.putString("STRING_KEY", "String Test");
                String stringValue = ref.getString("STRING_KEY", "");

                // Double
                ref.putDouble("DOUBLE_KEY", 123.5);
                double doubleValue = ref.getDouble("DOUBLE_KEY", 0);

                // Integer
                ref.putInt("INTEGER_KEY", 50);
                int intValue = ref.getInt("INTEGER_KEY", 0);

                // Object
                Date date = new Date();
                ref.putObject("OBJECT_KEY", date);
                Date dateValue = ref.getObject("OBJECT_KEY", Date.class);

                // Array
                Integer[] arr = {1, 2, 3};
                ref.putArray("ARRAY_KEY", arr);
                Integer[] arrValue = ref.getArray("ARRAY_KEY", Integer[].class);


            }catch (Exception e){
              e.printStackTrace();
            }
        }

    }
}