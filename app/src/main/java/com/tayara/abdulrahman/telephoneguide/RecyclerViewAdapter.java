package com.tayara.abdulrahman.telephoneguide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Contact> data;
    private Context context;

    public RecyclerViewAdapter(Context context, List<Contact> data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.recycler_view_item, viewGroup, false);
        return (new ViewHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (data == null || data.size() == 0) {

        } else {
            viewHolder.contactName.setText(data.get(i).getFullName());
            viewHolder.contactImage.setImageDrawable(getSuitableImage(data.get(i).getFullName(), i));

        }
    }

    @Override
    public int getItemCount() {
        return ((data == null || data.size() == 0) ? 0 : data.size());
    }

    void loadNewData(List<Contact> newData) {
        data = newData;
        notifyDataSetChanged();
    }

    public Contact getItemAt(int position) {
        return data.get(position);
    }

    public int getColorAt(int position) {
        int color = ColorGenerator.MATERIAL.getColor(data.get(position).getId());
        return color;
    }


    private Drawable getSuitableImage(String name, int position) {
        int color = ColorGenerator.MATERIAL.getColor(data.get(position).getId());
        TextDrawable drawable = TextDrawable.builder()
                .buildRoundRect(String.valueOf(Character.toUpperCase(name.charAt(0))),
                        color, 50);
        return drawable;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView contactImage;
        TextView contactName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactName = itemView.findViewById(R.id.contact_name);
            contactImage = itemView.findViewById(R.id.contact_image);
        }
    }

}
