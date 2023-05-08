# Local-Storage

Local-Storage is a library that allows you to securely save encrypted data on a local device using shared preferences.

## Setup

To get started with Local-Storage, follow these simple steps:

Step 1. Add the following code at the end of your root build.gradle file:
```
allprojects {
    repositories {
	maven { url 'https://jitpack.io' }
    }
}
```
Step 2. Add the dependency to your app-level build.gradle file:

```
dependencies {
	implementation 'com.github.maor412:Local-Storage:v1.0.0'
}
```

## Usage
To use Local-Storage, you can create an instance of the LocalStorage class, passing in a filename and a secret key. You can then use the various methods available to store and retrieve your data.

Here is an example of how to use Local-Storage:
###### StepProgress Constructor:
```java
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

       ```
       
