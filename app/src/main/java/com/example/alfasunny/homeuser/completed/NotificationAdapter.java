package com.example.alfasunny.homeuser.completed;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alfasunny.homeuser.NotificationEach;
import com.example.alfasunny.homeuser.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * Created by romana on 2/3/18.
 */

class NotificationViewHolder extends RecyclerView.ViewHolder {
    View view;
    TextView Name;
    TextView label;
    TextView notificationDescription;

    public NotificationViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        label = view.findViewById(R.id.label);
        Name = view.findViewById(R.id.Name);
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

        String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String fromUid = currentNotification.getFrom();

        if(fromUid.equals(myUid)) {
            holder.label.setText("Customer:");
            holder.Name.setText(currentNotification.getToPersonName());


            String description = "";

            if(currentNotification.getAmount()<0) {
                description = String.valueOf(0-currentNotification.getAmount()) + " Points were redeemed from the customer's account for buying a product that cost " + String.valueOf(currentNotification.getCost())  + " Taka";
            }
            else {
                description = String.valueOf(currentNotification.getAmount()) + " Points were added to the customer's account for buying a product that cost " + String.valueOf(currentNotification.getCost())  + " Taka";
            }
            holder.notificationDescription.setText(description);

        } else {
            holder.Name.setTextColor(Color.parseColor("#006400"));
            holder.Name.setText(currentNotification.getFromRestaurantName());

            String description = "";

            if(currentNotification.getAmount()<0) {
                description = String.valueOf(0-currentNotification.getAmount()) + " Points were redeemed from your account for buying a product that cost " + String.valueOf(currentNotification.getCost())  + " Taka";
            }
            else {
                description = String.valueOf(currentNotification.getAmount()) + " Points were added to your account for buying a product that cost " + String.valueOf(currentNotification.getCost())  + " Taka";
            }

            holder.notificationDescription.setText(description);
        }
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }
}
