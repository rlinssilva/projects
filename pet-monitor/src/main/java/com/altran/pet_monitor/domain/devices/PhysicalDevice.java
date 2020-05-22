package com.altran.pet_monitor.domain.devices;

import java.net.URL;

public interface PhysicalDevice {

    DeviceRead read(URL url);

}
