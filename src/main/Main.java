package main;

import cube.*;

import java.util.Arrays;

public class Main {
    static public void main(String arg[]) {
        Cube now1 = new Cube(3);
        int[][][] now22 = new int[][][]{
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
        };
        Cube now2 = new Cube(3, now22);

        now1.rotateCubeTo(Cube.Rotates.UP);
        now1.rotateCubeTo(Cube.Rotates.UP);
        now1.rotateCubeTo(Cube.Rotates.LEFT);
        now1.rotateBrink(Cube.CubeName.FRONT, 1,1,true);
        now1.rotateBrink(Cube.CubeName.LEFT, 1,1,true);
        now1.rotateBrink(Cube.CubeName.RIGHT, 1,1,true);
        now1.rotateBrink(Cube.CubeName.DOWN, 1,1,true);
        now1.rotateBrink(Cube.CubeName.UP, 1,1,true);
        now1.rotateCubeTo(Cube.Rotates.UP);
        now1.rotateBrink(Cube.CubeName.FRONT, 1,1,true);
        now1.rotateBrink(Cube.CubeName.LEFT, 1,1,true);
        now1.rotateBrink(Cube.CubeName.RIGHT, 1,1,true);
        now1.rotateBrink(Cube.CubeName.DOWN, 1,1,true);
        now1.rotateBrink(Cube.CubeName.UP, 1,1,true);
        now1.rotateCubeTo(Cube.Rotates.RIGHT);

        System.out.println(now1.toString());
        System.out.println(now2.toString());
//        System.out.println(now1.equals(now2));
//        now1.rotateBrink(Cube.CubeName.FRONT,2,1, true);
//        now2.rotateBrink(Cube.CubeName.FRONT,2,1, true);
//        now.toStringOneBrink(Cube.CubeName.BACK);
//        now.rotateBrink(Cube.CubeName.FRONT, 1, Cube.ClockWise.TRUE);
//        now1.rotateCubeTo(Cube.Rotates.UP);
//        now2.rotateCubeTo(Cube.Rotates.FRONT_RIGHT);
//        now.rotateBrink(Cube.CubeName.UP, 1, Cube.ClockWise.TRUE);
//        now.rotateBrink(Cube.CubeName.RIGHT, 1, Cube.ClockWise.TRUE);
//        System.out.println(Arrays.deepToString(now.getCubeNow()));
//
//        System.out.println(now1);
//        System.out.println(Arrays.deepToString(now.getCubeNow()));
//        System.out.println(now1.equals(now2));

    }
}
