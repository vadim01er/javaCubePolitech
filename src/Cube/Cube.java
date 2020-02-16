package Cube;

import java.util.Arrays;

public final class Cube {

    private static final String ANSI_RED = "\u001b[31m";
    private static final String ANSI_GREEN = "\u001b[32m";
    private static final String ANSI_YELLOW = "\u001b[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_WHITE = "\u001B[37m";

    private final String[] color = {"white", "red", "green", "orange", "blue", "yellow"};

    private final int sizeCube;

    private final int[][][] cubeNow;

    public Cube(int size){ // Конструктор
        this.sizeCube = size;
        this.cubeNow = new int[6][size][size];
        int m = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < size; j++) {
                cubeNow[i][j] = new int[size];
                for (int k = 0; k < size; k++) {
                    cubeNow[i][j][k] = m;
                }

            }
            m++;
        }
    }

    static private void rotateLeft(int[][] cube){ // Поворот квадратной матрицы в лево
        int size = cube.length;
        for (int j = 0; j < size / 2; j++) {
            for (int k = j; k < size - j - 1; k++) {
                int x =cube[j][k];
                cube[j][k] = cube[k][size - j - 1];
                cube[k][size - j - 1] = cube[size - j - 1][size - k -1];
                cube[size - j - 1][size - k - 1] = cube[size - k - 1][j];
                cube[size - k - 1][j] = x;
            }
        }
    }

    static private void rotateRight(int[][] cube){ // Поворот квадратной матрицы в право
        int size = cube.length;
        for (int j = 0; j < size / 2; j++) {
            for (int k = j; k < size - j - 1; k++) {
                int x = cube[j][k];
                cube[j][k] = cube[size - k - 1][j];
                cube[size - k - 1][j] = cube[size - j - 1][size - k - 1];
                cube[size - j - 1][size - k - 1] = cube[k][size - j - 1];
                cube[k][size - j - 1] = x;
            }
        }
    }

    public enum Rotates{
        FRONT_RIGHT,
        FRONT_LEFT,
        BACK,
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
    public enum CubeName {
        FRONT(0),
        DOWN(1),
        LEFT(2),
        UP(3),
        RIGHT(4),
        BACK(5);

        private int num;
        CubeName(int i) { num = i; }
    }

    public void rotateBrink(CubeName name, int startIndex, int endIndex, boolean clock){  // Поворот грани или центров
        if (startIndex < 1 || startIndex > sizeCube || endIndex < 1 || endIndex > sizeCube)
            throw new IllegalArgumentException("WRONG Index :)");
        if (startIndex > endIndex) { int x = endIndex; endIndex = startIndex; startIndex = x;}
        switch (name) {
            case FRONT: {
                if (clock) {
                    for (int now = startIndex; now <= endIndex; now++) {
                        if (now == 1) rotateRight(this.cubeNow[CubeName.FRONT.num]);// Поворот самой грани
                        if (now == sizeCube) rotateLeft(this.cubeNow[CubeName.BACK.num]);
                        for (int i = 0; i < sizeCube; i++) {
                            int x = this.cubeNow[CubeName.DOWN.num][now - 1][i];
                            this.cubeNow[CubeName.DOWN.num][now - 1][i] = this.cubeNow[CubeName.RIGHT.num][i][now - 1];
                            this.cubeNow[CubeName.RIGHT.num][i][now - 1] = this.cubeNow[CubeName.UP.num][sizeCube - now][i];
                            this.cubeNow[CubeName.UP.num][sizeCube - now][i] = this.cubeNow[CubeName.LEFT.num][i][sizeCube - now];
                            this.cubeNow[CubeName.LEFT.num][i][sizeCube - now] = x;
                        }
                    }
                } else {
                    for (int now = startIndex; now < endIndex; now++) {
                        if (now == 1) rotateLeft(this.cubeNow[CubeName.FRONT.num]); // Поворот самой грани
                        if (now == 1) rotateRight(this.cubeNow[CubeName.BACK.num]);
                        for (int i = 0; i < sizeCube; i++) {
                            int x = this.cubeNow[CubeName.DOWN.num][now - 1][i];
                            this.cubeNow[CubeName.DOWN.num][now - 1][i] = this.cubeNow[CubeName.LEFT.num][i][sizeCube - now];
                            this.cubeNow[CubeName.LEFT.num][i][sizeCube - now] = this.cubeNow[CubeName.UP.num][sizeCube - now][i];
                            this.cubeNow[CubeName.UP.num][sizeCube - now][i] = this.cubeNow[CubeName.RIGHT.num][i][now - 1];
                            this.cubeNow[CubeName.RIGHT.num][i][now - 1] = x;
                        }
                    }
                }
                break;
            }

            case RIGHT: {
                if (clock) {
                    for (int now = startIndex; now < endIndex; now++) {
                        if (now == 1) rotateRight(this.cubeNow[CubeName.RIGHT.num]);
                        if (now == sizeCube) rotateLeft(this.cubeNow[CubeName.LEFT.num]);
                        for (int i = 0; i < sizeCube; i++) {
                            int x = this.cubeNow[CubeName.DOWN.num][i][now - 1];
                            this.cubeNow[CubeName.DOWN.num][i][sizeCube - now] = this.cubeNow[CubeName.BACK.num][i][now - 1];
                            this.cubeNow[CubeName.BACK.num][i][now - 1] = this.cubeNow[CubeName.UP.num][i][sizeCube - now];
                            this.cubeNow[CubeName.UP.num][i][sizeCube - now] = cubeNow[CubeName.FRONT.num][i][sizeCube - now];
                            cubeNow[CubeName.FRONT.num][i][sizeCube - now] = x;
                        }
                    }
                } else {
                    for (int now = startIndex; now < endIndex; now++) {
                        if (now == 1) rotateLeft(this.cubeNow[CubeName.RIGHT.num]);
                        if (now == sizeCube) rotateRight(this.cubeNow[CubeName.LEFT.num]);
                        for (int i = 0; i < sizeCube; i++) {
                            int x = cubeNow[CubeName.FRONT.num][i][sizeCube - now];
                            cubeNow[CubeName.FRONT.num][i][sizeCube - now] = this.cubeNow[CubeName.UP.num][i][sizeCube - now];
                            this.cubeNow[CubeName.UP.num][i][sizeCube - now] = this.cubeNow[CubeName.BACK.num][i][now - 1];
                            this.cubeNow[CubeName.BACK.num][i][now - 1] = this.cubeNow[CubeName.DOWN.num][i][sizeCube - now];
                            this.cubeNow[CubeName.DOWN.num][i][sizeCube - now] = x;
                        }
                    }
                }
                break;
            }

            case LEFT:{
                if (clock) {
                    for (int now = startIndex; now < endIndex; now++) {
                        if (now == 1) rotateRight(cubeNow[CubeName.LEFT.num]);
                        if (now == sizeCube) rotateLeft(cubeNow[CubeName.RIGHT.num]);
                        for (int i = 0; i < sizeCube; i++) {
                            int x = cubeNow[CubeName.FRONT.num][i][sizeCube - now];
                            cubeNow[CubeName.FRONT.num][i][now - 1] = cubeNow[CubeName.UP.num][i][now - 1];
                            cubeNow[CubeName.UP.num][i][now - 1] = cubeNow[CubeName.BACK.num][i][sizeCube - now];
                            cubeNow[CubeName.BACK.num][i][sizeCube - now] = cubeNow[CubeName.DOWN.num][i][now - 1];
                            cubeNow[CubeName.DOWN.num][i][now - 1] = x;
                        }
                    }
                } else {
                    for (int now = startIndex; now < endIndex; now++) {
                        if (now == 1) rotateLeft(cubeNow[CubeName.LEFT.num]);
                        if (now == sizeCube) rotateRight(cubeNow[CubeName.RIGHT.num]);
                        for (int i = 0; i < sizeCube; i++) {
                            int x = cubeNow[CubeName.FRONT.num][i][now - 1];
                            cubeNow[CubeName.FRONT.num][i][now - 1] = cubeNow[CubeName.DOWN.num][i][now - 1];
                            cubeNow[CubeName.DOWN.num][i][now - 1] = cubeNow[CubeName.BACK.num][i][sizeCube - now];
                            cubeNow[CubeName.BACK.num][i][sizeCube - now] = cubeNow[CubeName.UP.num][i][now - 1];
                            cubeNow[CubeName.UP.num][i][now - 1] = x;
                        }
                    }
                }
                break;
            }

            case UP:{
                if (clock) {
                    for (int now = startIndex; now < endIndex; now++) {
                        if (now == 1) rotateRight(cubeNow[CubeName.UP.num]);
                        if (now == sizeCube) rotateLeft(cubeNow[CubeName.DOWN.num]);
                        for (int i = 0; i < sizeCube; i++) {
                            int x = cubeNow[CubeName.FRONT.num][now - 1][i];
                            cubeNow[CubeName.FRONT.num][now - 1][i] = cubeNow[CubeName.RIGHT.num][now - 1][i];
                            cubeNow[CubeName.RIGHT.num][now - 1][i] = cubeNow[CubeName.BACK.num][now - 1][i];
                            cubeNow[CubeName.BACK.num][now - 1][i] = cubeNow[CubeName.LEFT.num][now - 1][i];
                            cubeNow[CubeName.LEFT.num][now - 1][i] = x;
                        }
                    }
                } else {
                    for (int now = startIndex; now < endIndex; now++) {
                        if (now == 1) rotateLeft(cubeNow[CubeName.UP.num]);
                        if (now == sizeCube) rotateRight(cubeNow[CubeName.DOWN.num]);
                        for (int i = 0; i < sizeCube; i++) {
                            int x = cubeNow[CubeName.FRONT.num][now - 1][i];
                            cubeNow[CubeName.FRONT.num][now - 1][i] = cubeNow[CubeName.LEFT.num][now - 1][i];
                            cubeNow[CubeName.LEFT.num][now - 1][i] = cubeNow[CubeName.BACK.num][now - 1][i];
                            cubeNow[CubeName.BACK.num][now - 1][i] = cubeNow[CubeName.RIGHT.num][now - 1][i];
                            cubeNow[CubeName.RIGHT.num][now - 1][i] = x;
                        }
                    }
                }
                break;
            }

            case DOWN:{
                if (clock) {
                    for (int now = startIndex; now < endIndex; now++) {
                        if (now == 1) rotateRight(cubeNow[CubeName.DOWN.num]);
                        if (now == sizeCube) rotateLeft(cubeNow[CubeName.UP.num]);
                        for (int i = 0; i < sizeCube; i++) {
                            int x = cubeNow[CubeName.FRONT.num][sizeCube - now][i];
                            cubeNow[CubeName.FRONT.num][sizeCube - now][i] = cubeNow[CubeName.LEFT.num][sizeCube - now][i];
                            cubeNow[CubeName.LEFT.num][sizeCube - now][i] = cubeNow[CubeName.BACK.num][sizeCube - now][i];
                            cubeNow[CubeName.BACK.num][sizeCube - now][i] = cubeNow[CubeName.RIGHT.num][sizeCube - now][i];
                            cubeNow[CubeName.RIGHT.num][sizeCube - now][i] = x;
                        }
                    }
                } else {
                    for (int now = startIndex; now < endIndex; now++) {
                        if (now == 1) rotateLeft(cubeNow[CubeName.DOWN.num]);
                        if (now == sizeCube) rotateRight(cubeNow[CubeName.UP.num]);
                        for (int i = 0; i < sizeCube; i++) {
                            int x = cubeNow[CubeName.FRONT.num][sizeCube - now][i];
                            cubeNow[CubeName.FRONT.num][sizeCube - now][i] = cubeNow[CubeName.RIGHT.num][sizeCube - now][i];
                            cubeNow[CubeName.RIGHT.num][sizeCube - now][i] = cubeNow[CubeName.BACK.num][sizeCube - now][i];
                            cubeNow[CubeName.BACK.num][sizeCube - now][i] = cubeNow[CubeName.LEFT.num][sizeCube - now][i];
                            cubeNow[CubeName.LEFT.num][sizeCube - now][i] = x;
                        }
                    }
                }
                break;
            }

            case BACK:{
                if (clock) {
                    for (int now = startIndex; now < endIndex; now++) {
                        if (now == 1) rotateRight(cubeNow[CubeName.BACK.num]);
                        if (now == sizeCube) rotateLeft(cubeNow[CubeName.FRONT.num]);
                        for (int i = 0; i < sizeCube; i++) {
                            int x = cubeNow[CubeName.DOWN.num][sizeCube - now][i];
                            cubeNow[CubeName.DOWN.num][sizeCube - now][i] = cubeNow[CubeName.LEFT.num][i][sizeCube - now];
                            cubeNow[CubeName.LEFT.num][i][sizeCube - now] = cubeNow[CubeName.UP.num][sizeCube - now][i];
                            cubeNow[CubeName.UP.num][sizeCube - now][i] = cubeNow[CubeName.RIGHT.num][i][sizeCube - now];
                            cubeNow[CubeName.RIGHT.num][i][sizeCube - now] = x;
                        }
                    }
                } else {
                    for (int now = startIndex; now < endIndex; now++) {
                        if (now == 1) rotateLeft(cubeNow[CubeName.BACK.num]);
                        if (now == sizeCube) rotateRight(cubeNow[CubeName.FRONT.num]);
                        for (int i = 0; i < sizeCube; i++) {
                            int x = cubeNow[CubeName.DOWN.num][sizeCube - now][i];
                            cubeNow[CubeName.DOWN.num][sizeCube - now][i] = cubeNow[CubeName.RIGHT.num][i][sizeCube - now];
                            cubeNow[CubeName.RIGHT.num][i][sizeCube - now] = cubeNow[CubeName.UP.num][sizeCube - now][i];
                            cubeNow[CubeName.UP.num][sizeCube - now][i] = cubeNow[CubeName.LEFT.num][i][sizeCube - now];
                            cubeNow[CubeName.LEFT.num][i][sizeCube - now] = x;
                        }
                    }
                }
                break;
            }
        }
    }

    public void rotateCubeTo(Rotates move) {  // Поворот куба ()
        switch (move) {
            case DOWN:{
                int[][] x = this.cubeNow[CubeName.FRONT.num];
                this.cubeNow[CubeName.FRONT.num] = this.cubeNow[CubeName.DOWN.num];
                this.cubeNow[CubeName.DOWN.num] = this.cubeNow[CubeName.BACK.num];
                this.cubeNow[CubeName.BACK.num] = this.cubeNow[CubeName.UP.num];
                this.cubeNow[CubeName.UP.num] = x;

                rotateLeft(this.cubeNow[CubeName.LEFT.num]); //90

                rotateRight(this.cubeNow[CubeName.RIGHT.num]); //90

                rotateRight(this.cubeNow[CubeName.DOWN.num]); //180
                rotateRight(this.cubeNow[CubeName.DOWN.num]);

                rotateRight(this.cubeNow[CubeName.BACK.num]); //180
                rotateRight(this.cubeNow[CubeName.BACK.num]);
                break;
            }
            case RIGHT:{
                int[][] x = this.cubeNow[CubeName.FRONT.num];
                this.cubeNow[CubeName.FRONT.num] = this.cubeNow[CubeName.RIGHT.num];
                this.cubeNow[CubeName.RIGHT.num] = this.cubeNow[CubeName.BACK.num];
                this.cubeNow[CubeName.BACK.num] = this.cubeNow[CubeName.LEFT.num];
                this.cubeNow[CubeName.LEFT.num] = x;

                rotateRight(this.cubeNow[CubeName.UP.num]); //90

                rotateLeft(this.cubeNow[CubeName.DOWN.num]); //90
                break;
            }
            case LEFT:{
                int[][] x = this.cubeNow[CubeName.FRONT.num];
                this.cubeNow[CubeName.FRONT.num] = this.cubeNow[CubeName.LEFT.num];
                this.cubeNow[CubeName.LEFT.num] = this.cubeNow[CubeName.BACK.num];
                this.cubeNow[CubeName.BACK.num] = this.cubeNow[CubeName.RIGHT.num];
                this.cubeNow[CubeName.RIGHT.num] = x;

                rotateLeft(this.cubeNow[CubeName.UP.num]); //90

                rotateRight(this.cubeNow[CubeName.DOWN.num]); //90
                break;
            }
            case UP:{
                int[][] x = this.cubeNow[CubeName.FRONT.num];
                this.cubeNow[CubeName.FRONT.num] = this.cubeNow[CubeName.UP.num];
                this.cubeNow[CubeName.UP.num] = this.cubeNow[CubeName.BACK.num];
                this.cubeNow[CubeName.BACK.num] = this.cubeNow[CubeName.DOWN.num];
                this.cubeNow[CubeName.DOWN.num] = x;

                rotateRight(this.cubeNow[CubeName.LEFT.num]); //90

                rotateLeft(this.cubeNow[CubeName.RIGHT.num]); //90

                rotateRight(this.cubeNow[CubeName.UP.num]); //180
                rotateRight(this.cubeNow[CubeName.UP.num]);

                rotateRight(this.cubeNow[CubeName.BACK.num]); //180
                rotateRight(this.cubeNow[CubeName.BACK.num]);
                break;
            }
            case BACK:{
                rotateCubeTo(Rotates.RIGHT);
                rotateCubeTo(Rotates.RIGHT);
                break;
            }
            case FRONT_RIGHT:{
                int[][] x = this.cubeNow[CubeName.DOWN.num];
                this.cubeNow[CubeName.DOWN.num] = this.cubeNow[CubeName.RIGHT.num];
                this.cubeNow[CubeName.RIGHT.num] = this.cubeNow[CubeName.UP.num];
                this.cubeNow[CubeName.UP.num] = this.cubeNow[CubeName.LEFT.num];
                this.cubeNow[CubeName.LEFT.num] = x;

                rotateRight(this.cubeNow[CubeName.FRONT.num]); //90
                rotateRight(this.cubeNow[CubeName.RIGHT.num]);
                rotateRight(this.cubeNow[CubeName.LEFT.num]);
                rotateRight(this.cubeNow[CubeName.UP.num]);
                rotateRight(this.cubeNow[CubeName.DOWN.num]);
                rotateLeft(this.cubeNow[CubeName.BACK.num]);
                break;
            }
            case FRONT_LEFT:{
                int[][] x = this.cubeNow[CubeName.DOWN.num];
                this.cubeNow[CubeName.DOWN.num] = this.cubeNow[CubeName.LEFT.num];
                this.cubeNow[CubeName.LEFT.num] = this.cubeNow[CubeName.UP.num];
                this.cubeNow[CubeName.UP.num] = this.cubeNow[CubeName.RIGHT.num];
                this.cubeNow[CubeName.RIGHT.num] = x;

                rotateLeft(this.cubeNow[CubeName.FRONT.num]); //90
                rotateLeft(this.cubeNow[CubeName.RIGHT.num]);
                rotateLeft(this.cubeNow[CubeName.LEFT.num]);
                rotateLeft(this.cubeNow[CubeName.UP.num]);
                rotateLeft(this.cubeNow[CubeName.DOWN.num]);
                rotateRight(this.cubeNow[CubeName.BACK.num]);
            }
        }
    }

    public int getSizeCube() {  // Выводит размер
        return sizeCube;
    }

    public int[][][] getCubeNow() {  // Выводит сам куб
        return cubeNow;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Cube)) return false;
        Cube other = (Cube) obj;
        boolean res1, res2;
        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 4; j++){
                for (int k = 0; k < 4; k++){
                    res1 = true;
                    for (int m = 0; m < 6; m++) {
                        res2 = true;
                        for (int ii = 0; ii < sizeCube; ii++){
                            for (int jj = 0; jj< sizeCube; jj++) {
                                res2 &= this.cubeNow[m][ii][jj] == other.cubeNow[m][ii][jj];
                            }
                        }
                        res1 &= res2;
                    }
                    if (res1) return true;
                    other.rotateCubeTo(Rotates.LEFT);
                }
                other.rotateCubeTo(Rotates.FRONT_RIGHT);
            }
            other.rotateCubeTo(Rotates.UP);
            other.rotateCubeTo(Rotates.FRONT_RIGHT);
            other.rotateCubeTo(Rotates.FRONT_RIGHT);
        }
        return false;
    }

    @Override
    public String toString(){  // Просмотр всего куба в целом
        int size = sizeCube;

        String s1 = " ".repeat(2 * size + size + 3) + "—".repeat(2 * size + size + 1);
        StringBuilder text = new StringBuilder(s1 + "\n");
        for (int j = 0; j < sizeCube; j++) {
            text.append(" ".repeat(2 * size + size + 2)).append("|");
            for ( int k = 0; k < sizeCube; k++){
               text.append(" ").append(color[cubeNow[CubeName.UP.num][j][k]], 0, 2);
            }
            text.append(" |\n");
        }
        String s2 = " " + "—".repeat(2 * size + size + 1) + " " + "—".repeat(2 * size + size + 1) +
                " " + "—".repeat(2 * size + size + 1) + " " + "—".repeat(2 * size + size + 1);
        text.append(s2).append("\n");
        for (int j = 0; j < sizeCube; j++) {
            text.append("|");
            for ( int k = 0; k < sizeCube; k++){
                text.append(" ").append(color[cubeNow[CubeName.LEFT.num][j][k]], 0, 2);
            }
            text.append(" |");
            for ( int k = 0; k < sizeCube; k++){
                text.append(" ").append(color[cubeNow[CubeName.FRONT.num][j][k]], 0, 2);
            }
            text.append(" |");
            for ( int k = 0; k < sizeCube; k++){
                text.append(" ").append(color[cubeNow[CubeName.RIGHT.num][j][k]], 0, 2);
            }
            text.append(" |");
            for ( int k = 0; k < sizeCube; k++){
                text.append(" ").append(color[cubeNow[CubeName.BACK.num][j][k]], 0, 2);
            }
            text.append(" |").append("\n");
        }
        text.append(s2).append("\n");
        for (int j = 0; j < sizeCube; j++) {
            text.append(" ".repeat(2 * size + size + 2)).append("|");
            for ( int k = 0; k < sizeCube; k++){
               text.append(" ").append(color[cubeNow[CubeName.DOWN.num][j][k]], 0, 2);
            }
            text.append(" |").append("\n");
        }
        text.append(s1).append("\n");
        return text.toString();
    }

    public void toStringOneBrink(CubeName name) {  // Просмотр одной стороны
        StringBuilder text = new StringBuilder(" ".repeat(sizeCube + 1)+ name).append("\n");
        for (int j = 0; j < sizeCube; j++) {
            for (int k = 0; k < sizeCube; k++) {
                text.append(" ").append(color[cubeNow[name.num][j][k]], 0, 2);
            }
            text.append("\n");
        }
        System.out.println(text);
    }
}
