package pppp.group14project.model;

import org.junit.jupiter.api.BeforeEach;

public class Team13WallTest extends WallTest{
    @Override
    @BeforeEach
    void setUp() {
        super.wall = new WallAdapter();
    }
}
