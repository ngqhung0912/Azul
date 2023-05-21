package pppp.group14project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pppp.group14project.model.Player;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;

    private static final String playerName = "TestPlayer";

    @BeforeEach
    void setUp() {
        player = new Player(playerName);
    }

    /**
     * Test Creating player with name.
     */
    @Test
    void TestPlayerCreation() {
        assertEquals(player.getName(), playerName);
    }


}