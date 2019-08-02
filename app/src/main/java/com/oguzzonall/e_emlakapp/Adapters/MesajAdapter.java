package com.oguzzonall.e_emlakapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.oguzzonall.e_emlakapp.Models.MessageModel;
import com.oguzzonall.e_emlakapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MesajAdapter extends RecyclerView.Adapter<MesajAdapter.ViewHolder>{

    Activity activity;
    Context context;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    String userId, otherId;
    Boolean state;
    List<MessageModel> messageModelList;
    int view_type_sent = 1, view_type_received = 2;

    public MesajAdapter(Activity activity, Context context, String userId, String otherId, List<MessageModel> messageModelList) {
        this.activity = activity;
        this.context = context;
        this.userId = userId;
        this.otherId = otherId;
        this.messageModelList = messageModelList;
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();
        state=false;
    }

    @Override
    public MesajAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == view_type_sent) {
            view = LayoutInflater.from(context).inflate(R.layout.message_sent_layout, parent, false);
            return new ViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.message_received_layout, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MesajAdapter.ViewHolder holder,final int position) {
        holder.messageText.setText(messageModelList.get(position).getText());

        if(state)
        {
            if(messageModelList.get(position).getSeen())
            {
                holder.chatCheckImageView.setImageResource(R.drawable.ic_check_blue_24dp);
            }else{
                holder.chatCheckImageView.setImageResource(R.drawable.ic_check_gray_24dp);
            }
        }

    }

    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    //Viewların tanımlanam işlemleri yapılacak.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        CircleImageView chatCheckImageView;
        CircleImageView friend_image, friend_state_img;
        LinearLayout chatLinearLayout;

        ViewHolder(View itemView) {
            super(itemView);
            if (state) {
                chatLinearLayout = itemView.findViewById(R.id.chatUserLinear);
                messageText = itemView.findViewById(R.id.message_sent_textView);
                chatCheckImageView = itemView.findViewById(R.id.chatCheckImageView);
            } else {
                messageText = itemView.findViewById(R.id.message_received_textView);
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (messageModelList.get(position).getFrom().equals(userId)) {
            state = true;
            return view_type_sent;
        } else {
            state = false;
            return view_type_received;
        }
    }
}
