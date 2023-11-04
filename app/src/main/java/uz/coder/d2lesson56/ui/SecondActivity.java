package uz.coder.d2lesson56.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import uz.coder.d2lesson56.R;
import uz.coder.d2lesson56.databinding.ActivitySecondBinding;
import uz.coder.d2lesson56.models.TovarModel;
import uz.coder.d2lesson56.singleton.GsonSingleton;
import uz.coder.d2lesson56.singleton.MySharedPref;

public class SecondActivity extends AppCompatActivity {
    private ActivitySecondBinding binding;
    private int position = -1;
    private List<TovarModel> tovarModelList;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String TAG = "SecondActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivitySecondBinding.inflate(getLayoutInflater());
        sharedPreferences = getSharedPreferences("Tovarlar", MODE_PRIVATE);
        Gson gson = new Gson();
        setContentView(binding.getRoot());


        if (getIntent().getExtras() != null){
            TovarModel tovarModel = (TovarModel) getIntent().getSerializableExtra("objj");
            position = getIntent().getIntExtra("position",-1);
            binding.tovarNomi.setText(tovarModel.getTovarNomi());
            binding.tovarSoni.setText(String.valueOf(tovarModel.getCount()));
            int s = getS(binding.spinner, tovarModel.getCountType());
            binding.spinner.setSelection(s);
        }

        binding.btnSave.setOnClickListener(v -> {
            String s = binding.tovarNomi.getText().toString();
            String s1 = binding.tovarSoni.getText().toString();
            String s2 = binding.spinner.getSelectedItem().toString();

            TovarModel tovarModel = new TovarModel(s,s1,s2);
            String data = sharedPreferences.getString("tovar","");
            if (data.equalsIgnoreCase("")){
                tovarModelList = new ArrayList<>();

            }else {
                Type type = new TypeToken<List<TovarModel>>() {
                }.getType();
                tovarModelList = gson.fromJson(data, type);
            }
            tovarModelList.add(tovarModel);
            Log.d(TAG, "onCreate: "+String.valueOf(tovarModelList));

            String s3 = gson.toJson(tovarModelList);
            editor = sharedPreferences.edit();
            editor.putString("tovar",s3);
            editor.commit();
            Intent intent = new Intent();
            intent.putExtra("obj",tovarModel);
            intent.putExtra("pos",position);
            setResult(-1,intent);
            finish();
        });
    }
    public int getS(Spinner spinner, String s){
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(s)){
                return i;
            }
        }
        return 0;
    }
private  boolean exit = true;
    @Override
    public void onBackPressed() {
        if (exit){
            Intent intent = new Intent(SecondActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "Tap again to exit.", Toast.LENGTH_SHORT)
                    .show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);
        }


    }
}