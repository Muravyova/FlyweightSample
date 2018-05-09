package com.github.muravyova.flyweightsample.client;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.muravyova.flyweightsample.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    public interface OnItemClickListener{
        void onClick(int position);
    }

    @NonNull
    private final OnItemClickListener mListener;
    @NonNull
    private List<ListItem> mData;

    public ListAdapter(@NonNull List<ListItem> data, @NonNull OnItemClickListener listener) {
        mData = data;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.item_particle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final ListItem item = mData.get(position);
        if (item.isSelect()){
            holder.mContainer.setCardBackgroundColor(Color.CYAN);
        } else {
            holder.mContainer.setCardBackgroundColor(Color.WHITE);
        }

        if (item.getCount() == 0){
            holder.mCountContainer.setVisibility(View.GONE);
        } else {
            holder.mCountContainer.setVisibility(View.VISIBLE);
            holder.mCount.setText(String.valueOf(item.getCount()));
        }

        holder.mImage.setImageBitmap(item.getBitmap());
        holder.mSizeText.setText(concat(item.getSize()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!item.isSelect()){
                    mListener.onClick(holder.getAdapterPosition());
                }
            }
        });
    }

    public void addItem(ListItem item) {
        mData.add(item);
        notifyItemInserted(mData.size()-1);
    }

    @NonNull
    private String concat(int size){
        return size + " px";
    }

    public void selectPosition(int position, int progress){
        unSelect();
        mData.get(position).setSelect(true);
        mData.get(position).setCount(progress);
        notifyItemChanged(position);
    }

    private void unSelect(){
        for (int i = 0; i < mData.size(); i++){
            if (mData.get(i).isSelect()){
                mData.get(i).setSelect(false);
                notifyItemChanged(i);
            }
        }
    }

    @NonNull
    public List<ListItem> getData() {
        return mData;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void changeSelectedItem(int progress) {
        for (int i = 0; i < mData.size(); i++){
            if (mData.get(i).isSelect()){
                mData.get(i).setCount(progress);
                notifyItemChanged(i);
            }
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView mImage;
        final TextView mCount;
        final CardView mContainer;
        final CardView mCountContainer;
        final TextView mSizeText;

        ViewHolder(View view) {
            super(view);
            mSizeText = view.findViewById(R.id.item_particle_text_size);
            mImage = view.findViewById(R.id.item_particle_image);
            mCount = view.findViewById(R.id.item_particle_text_count);
            mContainer = view.findViewById(R.id.item_particle_container);
            mCountContainer = view.findViewById(R.id.item_particle_count);
        }
    }
}
