package com.yoshuafachry.chatapp.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.PeriodicSync;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yoshuafachry.chatapp.R;
import com.yoshuafachry.chatapp.adapter.AdapterPesan;
import com.yoshuafachry.chatapp.databinding.ActivityChattingBinding;
import com.yoshuafachry.chatapp.model.Pesan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Chatting extends AppCompatActivity {
    
    private ActivityChattingBinding binding;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private String IDPenerima;
    private AdapterPesan adapter;
    private List<Pesan> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide Action Bar & Title Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //SET BACKGROUND COLOR
        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.main_activity_background_color));

        binding = DataBindingUtil.setContentView(this, R.layout.activity_chatting);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        String nama = intent.getStringExtra("nama");
        IDPenerima = intent.getStringExtra("ID");

        if(IDPenerima != null){
            binding.tvNamaKontak.setText(nama);
        }

        binding.btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(binding.edTulisPesan.getText().toString())){
                    kirimPesan(binding.edTulisPesan.getText().toString());
                    binding.edTulisPesan.setText("");
                }
            }
        });

        list = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        //Kalau mau ngeset chatnya dari bawah
        //layoutManager.setStackFromEnd(true);
        binding.recyclerView.setLayoutManager(layoutManager);

        bacaPesan();
    }

    private void kirimPesan(String text) {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat tanggal = new SimpleDateFormat("dd-MM-yyyy");
        String hari = tanggal.format(date);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat pukul = new SimpleDateFormat("hh:mm");
        String jam = pukul.format(calendar.getTime());

        Pesan pesan = new Pesan(text,
                firebaseUser.getUid(),
                IDPenerima,
                hari + " , " + jam);
        reference.child("Pesan").push().setValue(pesan).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Pengiriman", "Berhasil");
            }
        });
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Daftar Chat").
                child(firebaseUser.getUid()).child(IDPenerima);
        reference1.child("IDChat").setValue(IDPenerima);

        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Daftar Chat").
                child(IDPenerima).child(firebaseUser.getUid());
        reference2.child("IDChat").setValue(IDPenerima);
    }
    private void bacaPesan() {
        try{
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            reference.child("Pesan").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Pesan pesan = snapshot.getValue(Pesan.class);
                        if(pesan != null
                                && pesan.getPengirim().equals(firebaseUser.getUid())
                                && pesan.getPenerima().equals(IDPenerima)
                                || pesan.getPenerima().equals(firebaseUser.getUid())
                                && pesan.getPengirim().equals(IDPenerima)){
                            list.add(pesan);
                        }
                    }
                    if(adapter != null){
                        adapter.notifyDataSetChanged();
                    } else{
                        adapter = new AdapterPesan(list, Chatting.this);
                        binding.recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}