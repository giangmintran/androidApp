package com.example.moneymanagement.transporters;

import com.example.moneymanagement.models.Event;

public class EventTransporter {
    private static Event event;

    public static Event getEvent() {
        return event;
    }

    public static void setEvent(Event event) {
        EventTransporter.event = event;
    }
}
