package pppp.group14project.view;

import org.junit.jupiter.api.Test;
import pppp.group14project.controller.exceptions.InvalidPositionException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerExceptionTest {

    @Test
    void testDefaultConstructor() {
        InvalidPositionException exception = new InvalidPositionException();
        assertNull(exception.getMessage());
    }

    @Test
    void testMessageConstructor() {
        String errorMessage = "Invalid position!";
        InvalidPositionException exception = new InvalidPositionException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }
}

