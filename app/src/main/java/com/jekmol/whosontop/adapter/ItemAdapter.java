package com.jekmol.whosontop.adapter;

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

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 2;

    private Context context;
    private ArrayList<Item> arrayList;
    private FooterClickListener footerClickListener;

    public interface FooterClickListener {
        void onFooterClick();
    }

    public ItemAdapter(Context context, ArrayList<Item> arrayList, FooterClickListener footerClickListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.footerClickListener = footerClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recylerview,
                    parent,false);
            return new ItemViewHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_header,
                    parent,false);
            return new HeaderViewHolder(itemView);
        } else if (viewType == TYPE_FOOTER){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_footer,
                    parent,false);
            return new FooterViewHolder(itemView);
        } else return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemPosition = position - 1;
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.tvTitle.setText(R.string.whoisontop);
        } else if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            final Item item = arrayList.get(itemPosition);
            itemViewHolder.tvId.setText(String.valueOf(item.getId()));
            itemViewHolder.tvContent.setText(item.getJoke().replaceAll("&quote;", "'"));
            if (item.getCategory() == null || item.getCategory().size() == 0) {
                itemViewHolder.tvCategories.setText(R.string.uncategorized);
            } else {
                itemViewHolder.tvCategories.setText(item.getCategory().first());
            }
            itemViewHolder.weAreOnTop(itemPosition);
            itemViewHolder.ivMoveUp.setOnClickListener(v -> {
                arrayList.remove(item);
                arrayList.add(0, item);
                notifyDataSetChanged();
            });
            itemViewHolder.cardView.setOnClickListener(v -> {
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
            });
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            if (arrayList.size() >= 5) {
                footerViewHolder.addMoreData.setVisibility(View.GONE);
            } else {
                footerViewHolder.addMoreData.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else if (position == arrayList.size() + 1) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return arrayList.size() + 2;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title) TextView tvTitle;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cardview) CardView cardView;
        @BindView(R.id.tv_id) TextView tvId;
        @BindView(R.id.tv_content) TextView tvContent;
        @BindView(R.id.tv_categories) TextView tvCategories;
        @BindView(R.id.tv_weareontop) TextView tvWeAreOnTop;
        @BindView(R.id.iv_weareontop) ImageView ivMoveUp;

        public ItemViewHolder(final View itemView) {
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

    public class FooterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.add_more_data) TextView addMoreData;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            footerClickListener.onFooterClick();
        }
    }

}
