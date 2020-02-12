
import Cube.*;
import java.util.Arrays;

public class hh {
    static public void main(String arg[]) {
        Cube now1 = new Cube(3);
        Cube now2 = new Cube(3);

        System.out.println(now1);
        //now.toStringOneBrink(Cube.CubeName.BACK);
//        now.rotateBrink(Cube.CubeName.FRONT, 1, Cube.ClockWise.TRUE);
        now1.rotateCubeTo(Cube.Rotates.UP);
//        now2.rotateCubeTo(Cube.Rotates.FRONT_RIGHT);
//        now.rotateBrink(Cube.CubeName.UP, 1, Cube.ClockWise.TRUE);
//        now.rotateBrink(Cube.CubeName.RIGHT, 1, Cube.ClockWise.TRUE);
//        System.out.println(Arrays.deepToString(now.getCubeNow()));


//        System.out.println(Arrays.deepToString(now.getCubeNow()));
        System.out.println(now1.equals(now2));
    }
}
