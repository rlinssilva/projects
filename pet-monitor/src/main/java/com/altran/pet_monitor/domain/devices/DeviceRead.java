package com.altran.pet_monitor.domain.devices;

import lombok.Getter;

@Getter
public class DeviceRead {

    private int temperature;

    private int humidity;

    public DeviceRead(int temperature, int humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
    }
}
