package com.altran.pet_monitor.unit.domain.environments;

import com.altran.pet_monitor.domain.EventPublisher;
import com.altran.pet_monitor.domain.events.DomainEvent;
import com.altran.pet_monitor.domain.events.EnvironmentState;
import com.altran.pet_monitor.domain.pets.Pet;
import com.altran.pet_monitor.domain.pets.Specie;
import com.altran.pet_monitor.domain.shared.Interval;
import com.altran.pet_monitor.util.Constants;
import com.google.common.collect.Lists;
import lombok.Getter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;


@Getter
class DeviceReadsMock implements DeviceReads {

    private boolean saved = false;

    @Override
    public DeviceRead save(DeviceRead read) {
        saved = true;
        return read;
    }
}

class EnvironmentsMock implements Environments {

    @Override
    public Environment findByDeviceId(UUID deviceId) {
        return EnvironmentTests.dougsHouse;
    }

    @Override
    public Environment save(Environment environment) {
        return EnvironmentTests.dougsHouse;
    }

    @Override
    public List<Environment> findAll() {
        return Lists.newArrayList(EnvironmentTests.dougsHouse);
    }

    @Override
    public List<Environment> findByState(EnvironmentState state) {
        return findAll();
    }
}

@Getter
class EventPublisherMock extends EventPublisher {

    DomainEvent temperatureUpdatedEvent;

    DomainEvent deviceReadOutOfBounds;

    @Override
    public void send(DomainEvent event) {
        switch (event.message()) {
            case Constants.TEMPERATURE_UPDATED:
                temperatureUpdatedEvent = event;
                break;
            case Constants.DEVICE_READ_OUT_OF_BOUNDS:
                deviceReadOutOfBounds = event;
                break;
        }
    }
}

public class EnvironmentTests {





    private static UUID deviceId;

    static Environment dougsHouse;

    private static EnvironmentHandler environmentHandler;

    private static Specie specie;

    private static Pet doug;

    private static Set<Pet> pets;

    private static DeviceReadsMock deviceReadsMock;
    private static EventPublisherMock eventPublisherMock;
    private static EnvironmentsMock environmentsMock;

    @BeforeAll
    public static void beforeAll() {



        //Test case mocks
        deviceReadsMock = new DeviceReadsMock();
        environmentsMock = new EnvironmentsMock();
        eventPublisherMock = new EventPublisherMock();

        specie = getRabbitSpecie();
        doug = getDougPet(specie);
        Set<Pet> pets = new HashSet<>();
        pets.add(doug);

        deviceId = getDeviceId();
        dougsHouse = new Environment(deviceId);
        dougsHouse.setUp("Doug's House", pets);
    }

    private static UUID getDeviceId() {
        return UUID.randomUUID();
    }

    private static Specie getRabbitSpecie() {
        return new Specie("rabbit", Interval.newInstance(5,35));
    }

    private static Pet getDougPet(Specie specie) {
        return new Pet("Doug", specie, null);
    }

    @Test
    public void given_deviceReadInExpectedBounds_then_notifyDataUpdated_and_saveReadData() {



        //Test Case data
        int temperature = 20;
        DeviceRead deviceRead = new DeviceRead(deviceId,temperature);



        EnvironmentHandler environmentHandler = mock(EnvironmentHandler.class,
                Mockito.CALLS_REAL_METHODS);

        when(environmentHandler.deviceReads())
                .thenReturn(deviceReadsMock);

        when(environmentHandler.environments())
                .thenReturn(environmentsMock);

        when(environmentHandler.eventPublisher())
                .thenReturn(eventPublisherMock);

        //checking negative condtions before running the method being tested
        assertNull(eventPublisherMock.temperatureUpdatedEvent);
        assertNull(eventPublisherMock.deviceReadOutOfBounds);
        assertFalse(deviceReadsMock.isSaved());

        //running tested method
        environmentHandler.handleDeviceRead(deviceRead);

        //checking if temperature  was updated
        assertNotNull(eventPublisherMock.temperatureUpdatedEvent);
        assertNotNull(eventPublisherMock.temperatureUpdatedEvent.additionalData());

        assertTrue(Environment.class.isAssignableFrom(
                eventPublisherMock
                        .temperatureUpdatedEvent
                        .additionalData()
                        .getClass()));

        Environment updatedEnvironment = (Environment) eventPublisherMock
                .temperatureUpdatedEvent
                .additionalData();

        assertEquals(temperature,updatedEnvironment.getCurrentTemperature());

        //checking if temperature data was saved
        assertTrue(deviceReadsMock.isSaved());
    }




    @Test
    public void given_nullReadData_then_illegalArgumentExceptionExpected() {
        environmentHandler = new EnvironmentHandler();
        assertThrows(IllegalArgumentException.class,
                () -> environmentHandler.handleDeviceRead(null));
    }

    @Test
    public void given_deviceReadOutOfExpectedBounds_then_notifyUnexpectedCondictions_and_saveReadData() {
        fail("Not yet implemented");
    }

}
