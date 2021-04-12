package ru.geekbrains.cityheraldry.event_bus;

import com.squareup.otto.Bus;

public class EventBus {
    private static Bus bus = null;

    public static Bus getBus() {
        if(bus == null) {
            bus = new Bus();
        }
        return bus;
    }
}
