package com.czk.testapi;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyle);

        ArrayList<Integer> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add(i);
        }
        MyAdapter adapter = new MyAdapter(this, datas);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
    }

    private class MyAdapter extends RecyclerView.Adapter {
        private final ArrayList<Integer> datas;
        private final Context context;

        MyAdapter(Context context, ArrayList<Integer> datas) {
            this.context = context;
            this.datas = datas;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_rc, parent, false);
            return new MyViewHoldel(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MyViewHoldel) {
                MyViewHoldel myViewHoldel = (MyViewHoldel) holder;
                myViewHoldel.tv.setText(String.valueOf(datas.get(position)));

            }
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        private class MyViewHoldel extends RecyclerView.ViewHolder {

            final TextView tv;

            MyViewHoldel(View view) {
                super(view);
                tv = view.findViewById(R.id.tv);
            }
        }
    }
}
