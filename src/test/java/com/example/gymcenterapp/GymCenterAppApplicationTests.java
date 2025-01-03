package com.example.gymcenterapp;

import org.junit.jupiter.api.Test;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.PrintWriter;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

@SpringBootTest
class GymCenterAppApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        // Create a launcher
        Launcher launcher = LauncherFactory.create();

        // Build the request to discover tests in the specified package
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectPackage("com.example"))  // replace with your test package
                .build();

        // Create a listener to gather test execution summary
        SummaryGeneratingListener listener = new SummaryGeneratingListener();

        // Execute the tests
        launcher.execute(request, listener);

        // Get and print the summary
        TestExecutionSummary summary = listener.getSummary();
        summary.printTo(new PrintWriter(System.out));
    }
}
