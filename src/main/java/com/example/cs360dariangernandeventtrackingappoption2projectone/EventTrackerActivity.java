package com.example.cs360dariangernandeventtrackingappoption2projectone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

public class EventTrackerActivity extends AppCompatActivity {

    private EventAdapter eventAdapter;
    private List<Event> eventList;

    private EditText etEventName;
    private EditText etEventDate;

    private EventDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_tracker); // Ensure this layout file exists

        // Initialize views
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        etEventName = findViewById(R.id.etEventName);
        etEventDate = findViewById(R.id.etEventDate);
        Button btnAddEvent = findViewById(R.id.btnAddEvent);

        // Initialize database helper and load events
        dbHelper = new EventDatabaseHelper(this);
        eventList = dbHelper.getAllEvents();

        // Set up RecyclerView with adapter and layout manager
        eventAdapter = new EventAdapter(eventList, this::deleteEvent);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(eventAdapter);

        // Set button click listener
        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEvent();
            }

        });

    }

    // Deletes an event by object (used by the adapter's lambda)
    private void deleteEvent(Event event) {
        int position = eventList.indexOf(event);
        if (position != -1) {
            deleteEvent(position);
        }
    }

    // Deletes an event at a specific position
    private void deleteEvent(int position) {
        Event eventToDelete = eventList.get(position);
        dbHelper.deleteEvent(eventToDelete.getName(), eventToDelete.getDate());
        eventList.remove(position);
        eventAdapter.notifyItemRemoved(position);
    }

    // Adds a new event
    private void addEvent() {
        String name = etEventName.getText().toString().trim();
        String date = etEventDate.getText().toString().trim();

        if (!name.isEmpty() && !date.isEmpty()) {
            Event newEvent = new Event(name, date);
            dbHelper.insertEvent(newEvent);
            eventList.add(newEvent);
            eventAdapter.notifyItemInserted(eventList.size() - 1);
            etEventName.setText("");
            etEventDate.setText("");
        }
    }
}
