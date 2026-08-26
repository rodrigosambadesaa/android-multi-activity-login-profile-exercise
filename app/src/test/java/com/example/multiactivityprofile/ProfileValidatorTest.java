package com.example.multiactivityprofile;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProfileValidatorTest {
    @Test
    public void acceptsCompleteProfile() {
        assertTrue(ProfileValidator.isValid("neo", "Andrés", "Herminio Jiménez", "h"));
    }

    @Test
    public void rejectsBlankFieldsOrUnknownGender() {
        assertFalse(ProfileValidator.isValid(" ", "Andrés", "Herminio", "h"));
        assertFalse(ProfileValidator.isValid("neo", "", "Herminio", "h"));
        assertFalse(ProfileValidator.isValid("neo", "Andrés", "", "h"));
        assertFalse(ProfileValidator.isValid("neo", "Andrés", "Herminio", "x"));
        assertFalse(ProfileValidator.isValid("neo", "Andrés", "Herminio", null));
    }
}
