package com.example.cs360dariangernandeventtrackingappoption2projectone;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Event> eventList;
    private final OnDeleteClickListener deleteClickListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(Event event);
    }

    public EventAdapter(List<Event> eventList, OnDeleteClickListener deleteClickListener) {
        this.eventList = eventList;
        this.deleteClickListener = deleteClickListener;
    }

    public void setEventList(List<Event> events) {
        this.eventList = events;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.tvName.setText(event.getName());
        holder.tvDate.setText(event.getDate());

        holder.btnDelete.setOnClickListener(v -> {
            if (deleteClickListener != null) {
                deleteClickListener.onDeleteClick(event);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList != null ? eventList.size() : 0;
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDate;
        ImageButton btnDelete;

        @SuppressLint("WrongViewCast")
        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvEventName);
            tvDate = itemView.findViewById(R.id.tvEventDate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}