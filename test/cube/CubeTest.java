package cube;

import org.junit.Test;


import java.util.Arrays;

import static org.junit.Assert.*;

public class CubeTest {
    @Test
    public void rotateBrink() {
        Cube now = new Cube(3);
        Cube real = new Cube(3, new int[][][]{
                {
                        {1, 1, 3},
                        {2, 1, 2},
                        {2, 0, 3}
                },
                {
                        {1, 3, 5},
                        {5, 0, 4},
                        {2, 3, 1}
                },
                {
                        {2, 0, 4},
                        {0, 4, 5},
                        {0, 4, 5}
                },
                {
                        {1, 1, 4},
                        {1, 5, 3},
                        {5, 5, 4}
                },
                {       {0, 5, 5},
                        {1, 2, 3},
                        {2, 0, 0}
                },
                {
                        {3, 4, 0},
                        {2, 3, 2},
                        {4, 4, 3}
                }
        });
        now.rotateBrink(Cube.CubeName.FRONT, 1, 1, true);
        now.rotateBrink(Cube.CubeName.RIGHT, 1, 1, true);
        now.rotateBrink(Cube.CubeName.LEFT, 1, 2, true);
        now.rotateBrink(Cube.CubeName.UP, 1, 1, true);
        now.rotateBrink(Cube.CubeName.BACK, 1, 1, false);
        now.rotateBrink(Cube.CubeName.FRONT, 1, 1, true);
        now.rotateCubeTo(Cube.Rotates.UP);
        now.rotateBrink(Cube.CubeName.FRONT, 1, 1, false);
        now.rotateBrink(Cube.CubeName.RIGHT, 1, 1, false);
        now.rotateBrink(Cube.CubeName.FRONT, 1, 2, true);
        now.rotateBrink(Cube.CubeName.LEFT, 1, 1, false);
        now.rotateBrink(Cube.CubeName.DOWN, 1, 1, true);
        now.rotateBrink(Cube.CubeName.UP, 1, 1, false);
        now.rotateBrink(Cube.CubeName.LEFT, 1, 1, false);
        now.rotateCubeTo(Cube.Rotates.RIGHT);
        now.rotateCubeTo(Cube.Rotates.RIGHT);
//        now.rotateCubeTo(Cube.Rotates.UP);
//        now.rotateCubeTo(Cube.Rotates.LEFT);
//        System.out.println(real);
//        System.out.println(Arrays.deepToString(now.getCubeNow()));
//        System.out.println(real.hashCode());
//        System.out.println(now.hashCode());
//        System.out.println(now.equals(real));
        assertEquals(now, real);
    }
}
