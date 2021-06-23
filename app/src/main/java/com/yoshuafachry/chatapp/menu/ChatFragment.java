package com.yoshuafachry.chatapp.menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yoshuafachry.chatapp.R;
import com.yoshuafachry.chatapp.adapter.AdapterChat;
import com.yoshuafachry.chatapp.model.Chat;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    public ChatFragment() {
        // Required empty public constructor
    }

    private List<Chat> list = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        bacaChat();
        return view;
    }

    private void bacaChat() {
        list.add(new Chat("001", "Abc", "daring", "23/06/2021"));
        list.add(new Chat("002", "Def", "luring", "24/06/2021"));
        list.add(new Chat("003", "Ghi", "boring", "25/06/2021"));

        recyclerView.setAdapter(new AdapterChat(list, getContext()));
    }
}