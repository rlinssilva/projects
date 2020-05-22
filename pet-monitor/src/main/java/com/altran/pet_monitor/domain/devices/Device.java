package com.altran.pet_monitor.domain.devices;

import com.altran.pet_monitor.Context;
import com.altran.pet_monitor.domain.events.EnvironmentConditionsOutOfBounds;
import com.altran.pet_monitor.domain.events.Events;
import com.altran.pet_monitor.domain.profiles.Profile;
import com.altran.pet_monitor.util.Constants;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;

import java.net.URL;
import java.util.UUID;

import static com.altran.pet_monitor.domain.events.Events.*;

@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
public class Device {

    private UUID id;

    private String name;

    private String ssid;

    private String password;

    private Profile profile;

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }

    public void name(String name) {
        proceedValidation(validateName(name));
        setName(name);
    }

    public String ssid() {
        return ssid;
    }

    public void ssid(String ssid){
        proceedValidation(validateName(ssid));
        setSsid(ssid);
    }

    public String password() {
        return getPassword();
    }

    public void password(String password) {
        proceedValidation(validateName(password));
        setPassword(password);
    }

    public void check() {

        PhysicalDevice physicalDevice =
                Context
                        .instance()
                        .physicalDevice();

        DeviceRead read = physicalDevice.read(null);

        if (!profile.acceptableConditions(read)){

            Context
                    .eventPublisher()
                    .send(
                            newInstance(
                                EnvironmentConditionsOutOfBounds.class,
                                Constants.DEVICE_READ_OUT_OF_BOUNDS));

        }
    }

    Device() {

    }

    static Pair<Boolean, String> validateName(String name) {
        boolean ret = name != null && name.trim().length() >= 3;
        String value = ret ? name : Constants.INVALID_INPUT_PARAMETERS;
        return Pair.of(ret,value);
    }

    static void proceedValidation(Pair<Boolean, String> validation){
        if (validation == null || validation.getLeft() == null || validation.getRight() == null){
            throw new IllegalStateException(Constants.INCONSISTENT_INTERNAL_STATE);
        }
        if (!validation.getLeft()){
            throw new IllegalArgumentException(validation.getRight());
        }
    }




}
