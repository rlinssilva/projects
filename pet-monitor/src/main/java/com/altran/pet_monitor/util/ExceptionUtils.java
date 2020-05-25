package com.altran.pet_monitor.util;

import com.altran.pet_monitor.unit.domain.environments.Environment;

public class ExceptionUtils {

    public static void printErrorMessage(Throwable exception, String process, String targetObject) {

        //TODO log exception summary
        //TODO print stack trace in log
        System.out.println(String.format("An error occurred while processing %s", process));
        if (targetObject != null) {
            System.out.println(String.format("Target object: %s", targetObject));
        }
        System.out.println("Error Message: " + exception.getMessage());
        System.out.println("Error details: ");
        exception.printStackTrace();
    }

}
