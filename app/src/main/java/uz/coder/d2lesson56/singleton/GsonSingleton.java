package uz.coder.d2lesson56.singleton;

import com.google.gson.Gson;

public class GsonSingleton {
    public static GsonSingleton gsonSingleton = new GsonSingleton();
    public static Gson gson;

    public static GsonSingleton getInstance(){
        if (gsonSingleton ==null){
            gson = new Gson();
        }
        return gsonSingleton;
    }

    public Gson getGson() {
        return gson;
    }
}
