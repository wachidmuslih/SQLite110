package com.example.sqlite110.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite110.MainActivity;
import com.example.sqlite110.R;
import com.example.sqlite110.database.DBController;
import com.example.sqlite110.database.Teman;
import com.example.sqlite110.edit_teman;

import java.util.ArrayList;
import java.util.HashMap;

public class TemanAdapter extends RecyclerView.Adapter<TemanAdapter.TemanViewHolder> implements Filterable {
    private ArrayList<Teman> listdata;
    private final TemanAdapterListener listener;
    private Context control;

    public TemanAdapter(ArrayList<Teman> listdata, MainActivity mainActivity) {
        this.listdata = listdata;
    }

    @Override
    public TemanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInf = LayoutInflater.from(parent.getContext());
        View view = layoutInf.inflate(R.layout.row_data_teman,parent,false);
        control = parent.getContext();
        return new TemanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TemanAdapter.TemanViewHolder holder, int position) {
        String nm,tlp, id;

        id = listdata.get(position).getId();
        nm = listdata.get(position).getNama();
        tlp = listdata.get(position).getTelpon();
        DBController db = new DBController(control);

        holder.namaTxt.setTextColor(Color.BLUE);
        holder.namaTxt.setTextSize(20);
        holder.namaTxt.setText(nm);
        holder.telponTxt.setText(tlp);

        holder.cardku.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu = new PopupMenu((control, holder.cardku));
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.mnEdit:
                                Intent i = new Intent(control, edit_teman.class);
                                i.putExtra("id", id);
                                i.putExtra("nama", nm);
                                i.putExtra("telpon", tlp);
                                control.startActivity(i);
                                break;
                            case R.id.mnHapus:
                                HashMap<String, String> value = new HashMap<>();
                                value.put("id", id);
                                db.DeleteData(value);
                                Intent j = new Intent(control, MainActivity.class);
                                control.startActivity(j);
                                break;
                        }
                        popupMenu.show();
                        return false;
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return  (listdata != null)?listdata.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public class TemanViewHolder extends RecyclerView.ViewHolder {
        private CardView cardku;
        private TextView namaTxt,telponTxt;
        public TemanViewHolder(View view) {
            super(view);
            cardku = (CardView) view.findViewById(R.id.kartuku);
            namaTxt = (TextView) view.findViewById(R.id.textNama);
            telponTxt = (TextView) view.findViewById(R.id.textTelpon);

            cardku.setOnClickListener(v->{
                Teman KontakItem = listdata.get(getAdapterPosition());
                listener.onTemanSelected(v, KontakItem, getAdapterPosition());

            });
        }
    }
}
