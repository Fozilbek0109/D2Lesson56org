package uz.coder.d2lesson56.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import uz.coder.d2lesson56.R;
import uz.coder.d2lesson56.databinding.ItemTovarBinding;
import uz.coder.d2lesson56.models.TovarModel;

public class TovarAdapter extends ArrayAdapter<TovarModel> {
    private List<TovarModel> tovarModelList;
    private onClickButton onClickButton;
    private Context context;

    public TovarAdapter(@NonNull Context context, @NonNull List<TovarModel> tovarModelList,onClickButton onClickButton) {
        super(context, R.layout.item_tovar, tovarModelList);
        this.tovarModelList = tovarModelList;
        this.context = context;
        this.onClickButton = onClickButton;
    }

    @Override
    public int getCount() {
        return tovarModelList.size();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ItemTovarBinding binding;
        if (convertView ==null){
            binding = ItemTovarBinding.inflate(LayoutInflater.from(context),parent,false);
        }else {
            binding = ItemTovarBinding.bind(convertView);
        }
        TovarModel tovarModel = tovarModelList.get(position);
        binding.tovarName.setText(tovarModel.getTovarNomi());
        binding.tovarCount.setText(String.valueOf(tovarModel.getCount()));
        binding.tovarCountType.setText(tovarModel.getCountType());

        binding.btnEdit.setOnClickListener(v ->
                onClickButton.edit(tovarModel,position));
        binding.btnDelete.setOnClickListener(v ->
                onClickButton.delete(tovarModel,position));
        return binding.getRoot();
    }
    public interface onClickButton{
        void edit(TovarModel tovarModel, int position);
        void delete(TovarModel tovarModel, int position);

    }
}
