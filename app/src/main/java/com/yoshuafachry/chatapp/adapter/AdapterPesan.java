package com.yoshuafachry.chatapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yoshuafachry.chatapp.R;
import com.yoshuafachry.chatapp.model.Pesan;

import java.util.List;

public class AdapterPesan extends RecyclerView.Adapter<AdapterPesan.ViewHolder> {
    private List<Pesan> list;
    private Context context;
    private FirebaseUser firebaseUser;

    public static final int pesanKirim = 0;
    public static final int pesanTerima = 1;


    public AdapterPesan(List<Pesan> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == pesanKirim){
            View view = LayoutInflater.from(context).inflate(R.layout.pesan_kirim_layout, parent, false);
            return new ViewHolder(view);
        } else{
            View view = LayoutInflater.from(context).inflate(R.layout.pesan_terima_layout, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.gabung(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView isiPesan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            isiPesan = itemView.findViewById(R.id.tv_isi_pesan);
        }

        public void gabung(Pesan pesan) {
            isiPesan.setText(pesan.getIsiPesan());
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(list.get(position).getPengirim().equals(firebaseUser.getUid())){
            return pesanKirim;
        } else{
            return pesanTerima;
        }
    }
}
