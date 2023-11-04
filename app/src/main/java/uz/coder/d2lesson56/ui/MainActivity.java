package uz.coder.d2lesson56.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import uz.coder.d2lesson56.R;
import uz.coder.d2lesson56.adapter.TovarAdapter;
import uz.coder.d2lesson56.databinding.ActivityMainBinding;
import uz.coder.d2lesson56.models.TovarModel;
import uz.coder.d2lesson56.singleton.GsonSingleton;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private TovarAdapter adapter;
    private List<TovarModel> tovarModelList;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Gson gson = new Gson();
        sharedPreferences = getSharedPreferences("Tovarlar", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        loadData();
        adapter = new TovarAdapter(this, tovarModelList, new TovarAdapter.onClickButton() {
            @Override
            public void edit(TovarModel tovarModel, int position) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("objj", tovarModel);
                intent.putExtra("position", position);
                adapter.notifyDataSetChanged();
                startActivityForResult(intent, 0);

                String s = gson.toJson((tovarModelList.remove(position)));
                editor.putString("tavar",s);
                editor.commit();

            }

            @Override
            public void delete(TovarModel tovarModel, int position) {
                tovarModelList.remove(position);
                String s = gson.toJson(tovarModelList);
                editor.putString("tovar", s);
                editor.commit();
                adapter.notifyDataSetChanged();
            }
        });
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "sdsds", Toast.LENGTH_SHORT).show();
            }
        });


        binding.fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, SecondActivity.class);
            startActivityForResult(intent, 0);
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        TovarModel tovarModel = (TovarModel) data.getSerializableExtra("obj");
        int pos = data.getIntExtra("pos", -1);
        if (pos == -1) {
            tovarModelList.add(tovarModel);
            adapter.notifyDataSetChanged();
        } else {
            tovarModelList.get(pos).setTovarNomi(tovarModel.getTovarNomi());
            tovarModelList.get(pos).setCount(tovarModel.getCount());
            tovarModelList.get(pos).setCountType(tovarModel.getCountType());
            adapter.notifyDataSetChanged();
        }

    }

    private void loadData() {
        //tovarModelList.add(new TovarModel("sdsd",23232,"3434"));
        String tovar = sharedPreferences.getString("tovar", "");
        if (tovar.equalsIgnoreCase("")) {
            tovarModelList = new ArrayList<>();
        } else {
            Type type = new TypeToken<List<TovarModel>>() {
            }.getType();
            Gson gson = new Gson();
            tovarModelList = gson.fromJson(tovar, type);
        }
    }
}