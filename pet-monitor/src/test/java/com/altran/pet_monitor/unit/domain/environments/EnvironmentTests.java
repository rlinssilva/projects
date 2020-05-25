package com.altran.pet_monitor.unit.domain.environments;

import com.altran.pet_monitor.domain.pets.Specie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EnvironmentTests {

    private static EnvironmentHandler environmentHandler;

    @BeforeAll
    public static void beforeAll() {

    }

    @Test
    public void given_deviceReadInExpectedBounds_then_notifyDataUpdated_and_saveReadData() {
        fail("Not yet implemented");
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
