package com.zemingo.ekaterinatemnogrudova.zemingo.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.zemingo.ekaterinatemnogrudova.zemingo.R;
import com.zemingo.ekaterinatemnogrudova.zemingo.utils.RecycleClickInterface;
import com.zemingo.ekaterinatemnogrudova.zemingo.utils.RssParser;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * Created by lirons on 18/02/2018.
 */

public class GlobesAdapter extends RecyclerView.Adapter<GlobesAdapter.BindingHolder> implements RecycleClickInterface {
    private List<RssParser.RssItem> mAccountItems;
    private GlobesAdapter.IAccountItemClick mListener;

    public interface IAccountItemClick {
        void onAccountItemClick(RssParser.RssItem item);
    }
    private RecycleClickInterface mClickedListener;

    GlobesAdapter(List<RssParser.RssItem> accountItems, GlobesAdapter.IAccountItemClick listener) {
        mAccountItems = accountItems;
        mListener = listener;
    }

    @Override
    public void onItemClicked(int position) {
        if (mListener != null && mAccountItems.size()!=0) {
            mListener.onAccountItemClick(mAccountItems.get(position));
        }
    }

    @NonNull
    @Override
    public GlobesAdapter.BindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ViewDataBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.layout_my_account_item, parent, false);
        GlobesAdapter.BindingHolder holder = new GlobesAdapter.BindingHolder(binding.getRoot());
        holder.setBinding(binding);
        holder.setClickedListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GlobesAdapter.BindingHolder holder, final int position) {
        if (getItemCount() <= 0 && position >= getItemCount()) {
            return;
        }
        if(mAccountItems!= null && mAccountItems.size() !=0) {
            RssParser.RssItem accountItem = mAccountItems.get(position);
            TextView accountTitle = holder.itemView.findViewById(R.id.text1);
            TextView accountSubTitle = holder.itemView.findViewById(R.id.text2);
            accountTitle.setText(accountItem.description);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickedListener.onItemClicked(position);
                }
            });
            holder.getBinding().executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        return mAccountItems == null ? 0 : mAccountItems.size();
    }


    public class BindingHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;
        BindingHolder(View rowView) {
            super(rowView);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }

        void setClickedListener(RecycleClickInterface clickedListener) {
            mClickedListener = clickedListener;
        }
    }

    public void refreshList(List<RssParser.RssItem> accountItems) {
        mAccountItems = accountItems;
        notifyDataSetChanged();
    }
}
