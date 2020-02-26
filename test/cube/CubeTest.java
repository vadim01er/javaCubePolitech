package cube;

import org.junit.Test;


import static org.junit.Assert.*;

public class CubeTest {
    @Test
    public void rotateBrink() {
        Cube real = new Cube(3);
        Cube now = new Cube(3);
        real.setSide(Cube.CubeName.FRONT, new int[][]{
                {5, 1, 5},
                {1, 1, 2},
                {0, 0, 2}
        });
        real.setSide(Cube.CubeName.DOWN, new int[][]{
                {4, 4, 5},
                {3, 0, 4},
                {3, 3, 3}
        });
        real.setSide(Cube.CubeName.LEFT, new int[][]{
                {2, 5, 3},
                {5, 4, 5},
                {0, 2, 1}
        });
        real.setSide(Cube.CubeName.UP, new int[][]{
                {1, 0, 2},
                {2, 5, 1},
                {2, 0, 1}
        });
        real.setSide(Cube.CubeName.RIGHT, new int[][]{
                {4, 4, 0},
                {1, 2, 2},
                {1, 5, 5}
        });
        real.setSide(Cube.CubeName.BACK, new int[][]{
                {3, 3, 0},
                {0, 3, 3},
                {4, 4, 4}
        });

        now.rotateBrink(Cube.CubeName.RIGHT, 1, 1, true);
        now.rotateBrink(Cube.CubeName.LEFT, 1, 2, true);
        now.rotateBrink(Cube.CubeName.UP, 1, 1, true);
        now.rotateBrink(Cube.CubeName.BACK, 1, 1, false);
        now.rotateBrink(Cube.CubeName.FRONT, 1, 1, true);
        now.rotateCubeTo(Cube.Rotates.UP);
        now.rotateBrink(Cube.CubeName.FRONT, 1, 1, false);
        now.rotateBrink(Cube.CubeName.RIGHT, 1, 1, false);
        now.rotateBrink(Cube.CubeName.DOWN, 1, 1, true);
        now.rotateBrink(Cube.CubeName.UP, 1, 1, false);
        now.rotateBrink(Cube.CubeName.LEFT, 1, 1, false);
        now.rotateBrink(Cube.CubeName.FRONT, 1, 2, true);
        now.rotateCubeTo(Cube.Rotates.RIGHT);
        now.rotateCubeTo(Cube.Rotates.RIGHT);
        now.rotateCubeTo(Cube.Rotates.UP);
        now.rotateCubeTo(Cube.Rotates.LEFT);
        System.out.println(real);
        System.out.println(now);
//        System.out.println(real.hashCode());
//        System.out.println(now.hashCode());
        System.out.println(now.equals(real));
        assertEquals(now, real);
    }

}
