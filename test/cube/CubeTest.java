package cube;

import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import static org.junit.Assert.*;

public class CubeTest {
    @Test
    public void rotateBrink() {
        Cube real = new Cube(3);
        Cube now = new Cube(3);
        real.setSide(Cube.CubeName.FRONT, new int[][]{
                {5, 4, 4},
                {5, 4, 4},
                {5, 3, 3}
        });
        real.setSide(Cube.CubeName.DOWN, new int[][]{
                {3, 0, 0},
                {4, 0, 0},
                {4, 0, 0}
        });
        real.setSide(Cube.CubeName.LEFT, new int[][]{
                {2, 3, 3},
                {2, 3, 3},
                {2, 3, 3}
        });
        real.setSide(Cube.CubeName.UP, new int[][]{
                {2, 5, 5},
                {2, 5, 5},
                {1, 5, 5}
        });
        real.setSide(Cube.CubeName.RIGHT, new int[][]{
                {1, 1, 1},
                {1, 1, 1},
                {4, 4, 4}
        });
        real.setSide(Cube.CubeName.BACK, new int[][]{
                {2, 2, 0},
                {2, 2, 0},
                {1, 1, 0}
        });
        now.rotateBrink(Cube.CubeName.FRONT, 1, 1, false);
        now.rotateBrink(Cube.CubeName.LEFT, 1, 1, false);
//        now.rotateBrink(Cube.CubeName.RIGHT, 1, 1, false);
//        now.rotateBrink(Cube.CubeName.DOWN, 1, 1, false);
//        now.rotateBrink(Cube.CubeName.UP, 1, 1, false);
        now.rotateCubeTo(Cube.Rotates.UP);
//        now.rotateBrink(Cube.CubeName.FRONT, 1, 1, true);
//        now.rotateBrink(Cube.CubeName.LEFT, 1, 1, true);
//        now.rotateBrink(Cube.CubeName.RIGHT, 1, 1, true);
//        now.rotateBrink(Cube.CubeName.DOWN, 1, 1, true);
//        now.rotateBrink(Cube.CubeName.UP, 1, 1, true);
//        now.rotateCubeTo(Cube.Rotates.RIGHT);
        System.out.println(now);
        assertEquals(now, real);
    }

    @Test
    public void moveFace() {
        Cube cubePROGRAM = new Cube(3);
        Cube cubeREAL = new Cube(3);
        cubeREAL.setSide(Cube.CubeName.FRONT, new int[][]{
                {4, 3, 3},
                {0, 5, 0},
                {3, 4, 4}
        });
        cubeREAL.setSide(Cube.CubeName.BACK, new int[][]{
                {1, 1, 2},
                {1, 0, 2},
                {1, 2, 5}
        });
        cubeREAL.setSide(Cube.CubeName.RIGHT, new int[][]{
                {5, 4, 4},
                {3, 3, 0},
                {5, 3, 0}
        });
        cubeREAL.setSide(Cube.CubeName.LEFT, new int[][]{
                {2, 0, 1},
                {2, 1, 1},
                {2, 1, 0}
        });
        cubeREAL.setSide(Cube.CubeName.UP, new int[][]{
                {5, 5, 3},
                {4, 4, 3},
                {0, 2, 2}
        });
        cubeREAL.setSide(Cube.CubeName.DOWN, new int[][]{
                {4, 5, 1},
                {5, 2, 5},
                {0, 4, 3}
        });
        cubePROGRAM.rotateBrink(Cube.CubeName.RIGHT, 1, 1, true);
        cubePROGRAM.rotateBrink(Cube.CubeName.LEFT, 1, 2, true);
        cubePROGRAM.rotateBrink(Cube.CubeName.UP, 1, 1, true);
        cubePROGRAM.rotateBrink(Cube.CubeName.BACK, 1, 1, false);
        cubePROGRAM.rotateBrink(Cube.CubeName.FRONT, 1, 1, true);
        cubePROGRAM.rotateCubeTo(Cube.Rotates.UP);
        cubePROGRAM.rotateBrink(Cube.CubeName.FRONT, 1, 1, false);
        cubePROGRAM.rotateBrink(Cube.CubeName.RIGHT, 1, 1, false);
        cubePROGRAM.rotateBrink(Cube.CubeName.DOWN, 1, 1, true);
        cubePROGRAM.rotateBrink(Cube.CubeName.UP, 1, 1, false);
        cubePROGRAM.rotateBrink(Cube.CubeName.LEFT, 1, 1, false);
        cubePROGRAM.rotateBrink(Cube.CubeName.FRONT, 1, 2, true);
        cubePROGRAM.rotateCubeTo(Cube.Rotates.RIGHT);
        cubePROGRAM.rotateCubeTo(Cube.Rotates.RIGHT);
        cubePROGRAM.rotateCubeTo(Cube.Rotates.UP);
        cubePROGRAM.rotateCubeTo(Cube.Rotates.LEFT);
        assertEquals(cubePROGRAM, cubeREAL);
    }
}
