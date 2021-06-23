package com.yoshuafachry.chatapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yoshuafachry.chatapp.R;
import com.yoshuafachry.chatapp.model.Chat;

import java.util.List;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.Holder> {
    private List<Chat> list;
    private Context context;

    public AdapterChat(List<Chat> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_layout, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Chat chat = list.get(position);
        holder.tvNama.setText(chat.getNama());
        holder.tvKet.setText(chat.getKet());
        holder.tvTgl.setText(chat.getTgl());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView tvNama, tvKet, tvTgl;

        public Holder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tv_nama);
            tvKet = itemView.findViewById(R.id.tv_ket);
            tvTgl = itemView.findViewById(R.id.tv_tgl);
        }
    }
}
