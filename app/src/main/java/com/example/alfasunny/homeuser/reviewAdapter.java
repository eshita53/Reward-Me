package com.example.alfasunny.homeuser;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tanmoy Krishna Das on 2/11/2018.
 */

class reviewHolder extends RecyclerView.ViewHolder {
    View view;
    TextView restaurantName;
    TextView itemName;
    TextView ratingData;
    TextView authorName;
    TextView reviewDescription;
    Button readMore;

    public reviewHolder(View itemView) {
        super(itemView);

        view = itemView;
        restaurantName = (TextView) itemView.findViewById(R.id.restaurantName);
        itemName = (TextView) itemView.findViewById(R.id.itemName);
        ratingData = (TextView) itemView.findViewById(R.id.ratingData);
        authorName = (TextView) itemView.findViewById(R.id.authorName);
        reviewDescription = (TextView) itemView.findViewById(R.id.reviewDescription);
        readMore = (Button) itemView.findViewById(R.id.readMore);
    }
}

class reviewAdapter extends RecyclerView.Adapter<reviewHolder> {
    ArrayList<ReviewEach> reviewEachArrayList;
    Reviews parent;

    public reviewAdapter(ArrayList<ReviewEach> reviewEachArrayList, Reviews parent) {
        this.reviewEachArrayList = reviewEachArrayList;
        this.parent = parent;
    }

    @Override
    public reviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = this.parent.getLayoutInflater();
        View view = inflater.inflate(R.layout.review_each_layout, null);

        //needed for match_parent
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new reviewHolder(view);
    }

    @Override
    public void onBindViewHolder(reviewHolder holder, int position) {
        ReviewEach currentReview = reviewEachArrayList.get(position);

        holder.restaurantName.setText(currentReview.getRestaurantName());
        holder.itemName.setText(currentReview.getItemName());
        holder.ratingData.setText(currentReview.getRating());
        holder.authorName.setText(currentReview.getAuthorName());
        holder.reviewDescription.setText(currentReview.getReviewDescription());
        holder.readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.reviewDescription.getVisibility()==View.GONE) {
                    holder.reviewDescription.setVisibility(View.VISIBLE);
                    holder.readMore.setText("Show Less");
                }
                else {
                    holder.reviewDescription.setVisibility(View.GONE);
                    holder.readMore.setText("Read More");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return reviewEachArrayList.size();
    }
}
