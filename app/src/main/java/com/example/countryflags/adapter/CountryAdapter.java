package com.example.countryflags.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.countryflags.R;
import com.example.countryflags.model.Result;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    private final Context context;
    private List<Result> results;
    private int checkedPosition = 0;

    public CountryAdapter(Context context, List<Result> results){
        this.context = context;
        this.results = results;
    }

    public void setCountries(List<Result> results){
        this.results = new ArrayList<>();
        this.results = results;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.country_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(results.get(position));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView countryName;
        private final RadioButton button;
        private final ImageView flag;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            countryName = itemView.findViewById(R.id.country_name);
            button = itemView.findViewById(R.id.country_check);
            flag = itemView.findViewById(R.id.country_flag);

        }

        void bind(final Result data){
            if (checkedPosition == -1){
                button.setChecked(false);
            }else{
                button.setChecked(checkedPosition == getAdapterPosition());
            }

            countryName.setText(data.getName());
            RequestOptions requestOption = new  RequestOptions()
                    .placeholder(R.drawable.loading).centerCrop();
            String url = "https://www.countryflags.io/" + data.getCode() + "/flat/64.png";
            Glide.with(context).load(url).apply(requestOption).centerCrop().into(flag);
            itemView.setOnClickListener(v -> {
                button.setChecked(true);
                if (checkedPosition != getAdapterPosition()){
                    notifyItemChanged(checkedPosition);
                    checkedPosition = getAdapterPosition();
                }
            });
        }


    }

    public Result getSelected(){
        if (checkedPosition != -1){
            return results.get(checkedPosition);
        }

        return null;
    }
}
