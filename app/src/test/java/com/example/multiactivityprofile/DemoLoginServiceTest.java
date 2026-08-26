package com.example.multiactivityprofile;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DemoLoginServiceTest {
    @Test
    public void acceptsAssignmentCredentials() {
        assertTrue(DemoLoginService.authenticate("neo", "sesamo"));
    }

    @Test
    public void rejectsWrongCredentials() {
        assertFalse(DemoLoginService.authenticate("neo", "wrong"));
        assertFalse(DemoLoginService.authenticate("trinity", "sesamo"));
        assertFalse(DemoLoginService.authenticate(null, null));
    }
}
