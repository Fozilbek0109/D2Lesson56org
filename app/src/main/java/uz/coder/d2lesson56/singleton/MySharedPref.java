package uz.coder.d2lesson56.singleton;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPref {
    private static MySharedPref mySharedPref = new MySharedPref();
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor edit;
    private MySharedPref(){

    }
    public static MySharedPref getInstance(Context context){
        if (sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences("Tovars",context.MODE_PRIVATE);
        }
        return mySharedPref;
    }


    public void setData(String str){
        edit = sharedPreferences.edit();
        edit.putString("tovar",str);
    }

    public String getData(){
        return sharedPreferences.getString("tovar","");
    }

}
