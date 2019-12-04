package com.example.recyclerviewjsonexmpl;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    private Context mContext;
    private ArrayList<ExampleItem> mExampleList;
    public OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener( OnItemClickListener listener){
        mListener = listener;
    }

    public ExampleAdapter(Context context, ArrayList<ExampleItem> exampleList ) {
        mContext = context;
        mExampleList = exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = (View) LayoutInflater.from(mContext).inflate(R.layout.example_cardview, viewGroup, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder exampleViewHolder, int position) {
            ExampleItem currentItem = mExampleList.get(position);

            String imageUrl = currentItem.getImageUrl();
            String creatorName = currentItem.getCreator().toUpperCase();
            int likeCount = currentItem.getmLikes();

            exampleViewHolder.mTextViewCreator.setText(creatorName);
            exampleViewHolder.mLikes.setText("Likes: " + likeCount);
            Picasso.get().load(imageUrl).fit().centerInside().into(exampleViewHolder.mImageView);

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mTextViewCreator;
        public TextView mLikes;
        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.img_view);
            mTextViewCreator = itemView.findViewById(R.id.text_view_creator);
            mLikes = itemView.findViewById(R.id.text_view_like);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int postion = getAdapterPosition();
                        if(postion != RecyclerView.NO_POSITION){
                            mListener.onItemClick(postion);
                        }
                    }
                }
            });
        }
    }
}
