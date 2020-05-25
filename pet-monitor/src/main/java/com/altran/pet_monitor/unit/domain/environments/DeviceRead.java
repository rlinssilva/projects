package com.altran.pet_monitor.unit.domain.environments;

import com.altran.pet_monitor.util.Constants;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
public class DeviceRead {

    @Id
    private UUID id;

    private UUID deviceId;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime datetime;

    private int temperature;

    DeviceRead() { }

    public DeviceRead(UUID deviceId, int temperature) {

        if (deviceId == null) {
            throw new IllegalArgumentException(Constants.INVALID_INPUT_PARAMETERS);
        }

        this.id = UUID.randomUUID();
        this.deviceId = deviceId;
        this.datetime = LocalDateTime.now();
        this.temperature = temperature;
    }
}
