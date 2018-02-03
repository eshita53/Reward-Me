package com.example.alfasunny.homeuser;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by romana on 2/3/18.
 */

class NotificationViewHolder extends RecyclerView.ViewHolder {
    View view;
    TextView restaurantName;
    TextView notificationDescription;

    public NotificationViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        restaurantName = view.findViewById(R.id.restaurantName);
        notificationDescription = view.findViewById(R.id.notificationDescription);
    }
}

class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {
    ArrayList<NotificationEach> notificationList;
    Notifications notifications;

    public NotificationAdapter(ArrayList<NotificationEach> notificationList, Notifications notifications) {
        this.notificationList = notificationList;
        this.notifications = notifications;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = notifications.getLayoutInflater();
        View view = inflater.inflate(R.layout.notification_each_layout, null);

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        NotificationViewHolder holder = new NotificationViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        NotificationEach currentNotification = notificationList.get(position);

        holder.restaurantName.setText(currentNotification.getFromRestaurantName());

        String description = "";

        if(currentNotification.getAmount()<0) {
            description = String.valueOf(0-currentNotification.getAmount()) + " was redeemed from your account for buying a product that cost " + String.valueOf(currentNotification.getCost())  + " Taka";
        }
        else {
            description = String.valueOf(currentNotification.getAmount()) + " was added to your account for buying a product that cost " + String.valueOf(currentNotification.getCost())  + " Taka";
        }


        holder.notificationDescription.setText(description);

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }
}
