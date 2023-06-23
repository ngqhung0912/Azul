package pppp.group14project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pppp.group14project.model.exceptions.FullException;
import pppp.group14project.model.exceptions.WrongTileException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class Team13WallTest extends WallTest {
    @Override
    @BeforeEach
    void setUp() {
        super.wall = new WallAdapter();
    }

    @Test
    @Override
    void incorrectTileLocation() {
        assertThrows(RuntimeException.class, () -> {
            try {
                wall.addTile(Tile.RED, 5);
            } catch (WrongTileException | FullException e) {
                fail();
            }
        });
    }

}

