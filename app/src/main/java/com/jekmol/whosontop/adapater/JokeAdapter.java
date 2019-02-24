package com.jekmol.whosontop.adapater;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jekmol.whosontop.R;
import com.jekmol.whosontop.model.entity.Item;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Item> arrayList;

    public JokeAdapter(Context context, ArrayList<Item> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recylerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Item item = arrayList.get(position);
        holder.tvId.setText(String.valueOf(item.getId()));
        holder.tvContent.setText(item.getJoke().replaceAll("&quote;", "'"));
        if (item.getCategory() == null || item.getCategory().size() == 0) {
            holder.tvCategories.setText(R.string.uncategorized);
        } else {
            holder.tvCategories.setText(item.getCategory().first());
        }
        holder.weAreOnTop(position);
        holder.ivMoveUp.setOnClickListener(v -> {
            arrayList.remove(item);
            arrayList.add(0, item);
            notifyDataSetChanged();
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Item " + item.getId());
                builder.setMessage(item.getJoke());
                builder.setPositiveButton(R.string.its_funny, (dialog, which) -> {
                    Toast.makeText(context, R.string.its_funny_response, Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                });
                builder.setNegativeButton(R.string.cringe, (dialog, which) -> {
                    Toast.makeText(context, R.string.cringe_response, Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                });
                Dialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cardview) CardView cardView;
        @BindView(R.id.tv_id) TextView tvId;
        @BindView(R.id.tv_content) TextView tvContent;
        @BindView(R.id.tv_categories) TextView tvCategories;
        @BindView(R.id.tv_weareontop) TextView tvWeAreOnTop;
        @BindView(R.id.iv_weareontop) ImageView ivMoveUp;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void weAreOnTop(int position) {
            if (position == 0) {
                tvWeAreOnTop.setVisibility(View.VISIBLE);
                ivMoveUp.setVisibility(View.INVISIBLE);
            } else {
                tvWeAreOnTop.setVisibility(View.INVISIBLE);
                ivMoveUp.setVisibility(View.VISIBLE);
            }
        }

    }

}
