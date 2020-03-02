package cube;

import java.util.Arrays;

public final class Cube {

    private static final String ANSI_RESET = "\u001b[0m";
    private static final String ANSI_RED = "\u001b[31m";
    private static final String ANSI_GREEN = "\u001b[32m";
    private static final String ANSI_YELLOW = "\u001b[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_WHITE = "\u001B[37m";

    private final String[] color = {
            ANSI_WHITE + "wh",
            ANSI_RED + "re",
            ANSI_GREEN + "gr",
            ANSI_PURPLE + "pu",
            ANSI_BLUE + "bl",
            ANSI_YELLOW + "ye"
    };

    private final int sizeCube;

    private final int[][][] cubeNow;

    Cube(int size) { // Конструктор
        if (size < 0) throw new IllegalArgumentException("Wrong SIZE!");
        this.sizeCube = size;
        this.cubeNow = new int[6][size][size];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < size; j++) {
                cubeNow[i][j] = new int[size];
                for (int k = 0; k < size; k++) {
                    cubeNow[i][j][k] = i;
                }
            }
        }
    }

    Cube(int size, int[][][] cube) {
        if (size < 0) throw new IllegalArgumentException("Wrong SIZE!");
        if (cube.length != 6) throw new IllegalArgumentException("It's no cube!");
        for (int[][] ints : cube) {
            if (ints.length != size) throw new IllegalArgumentException("It's no cube!");
            for (int[] anInt : ints) {
                if (anInt.length != size) throw new IllegalArgumentException("It's no cube!");
            }
        }
        this.sizeCube = size;
        this.cubeNow = new int[6][size][size];
        for (int i = 0; i < cube.length; i++) {
            for (int j = 0; j < cube[i].length; j++) {
                this.cubeNow[i][j] = cube[i][j].clone();
            }
        }
    }


     private void rotateLeft(CubeName ... name) { // Поворот квадратной матрицы влево
        int size = sizeCube;
         for (CubeName cubename: name) {
             int[][] cubeSide = getNumber(cubename);
             for (int j = 0; j < size / 2; j++) {
                 for (int k = j; k < size - j - 1; k++) {
                     int x = cubeSide[j][k];
                     cubeSide[j][k] = cubeSide[k][size - j - 1];
                     cubeSide[k][size - j - 1] = cubeSide[size - j - 1][size - k - 1];
                     cubeSide[size - j - 1][size - k - 1] = cubeSide[size - k - 1][j];
                     cubeSide[size - k - 1][j] = x;
                 }
             }
         }
     }

     private void rotateRight(CubeName ... name) { // Поворот квадратной матрицы вправо
        int size = sizeCube;
         for (CubeName cubeName : name) {
             int[][] cubeSide = getNumber(cubeName);
             for (int j = 0; j < size / 2; j++) {
                 for (int k = j; k < size - j - 1; k++) {
                     int x = cubeSide[j][k];
                     cubeSide[j][k] = cubeSide[size - k - 1][j];
                     cubeSide[size - k - 1][j] = cubeSide[size - j - 1][size - k - 1];
                     cubeSide[size - j - 1][size - k - 1] = cubeSide[k][size - j - 1];
                     cubeSide[k][size - j - 1] = x;
                 }
             }
         }
     }

    public enum Rotates {
        FRONT_RIGHT,
        FRONT_LEFT,
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


    private int[][] getNumber(CubeName name) { return this.cubeNow[name.num]; }

    void rotateBrink(CubeName name, int startIndex, int endIndex, boolean clock) {  // Поворот грани или центров
        if (startIndex < 1 || startIndex > sizeCube || endIndex < 1 || endIndex > sizeCube)
            throw new IllegalArgumentException("WRONG Index :)");
        if (startIndex > endIndex) {
            int x = endIndex;
            endIndex = startIndex;
            startIndex = x;
        }
        switch (name) {
            case FRONT: {
                if (clock) {
                    for (int now = startIndex; now <= endIndex; now++) {
                        if (now == 1) rotateRight(CubeName.FRONT);// Поворот самой грани
                        if (now == sizeCube) rotateLeft(CubeName.BACK);
                        for (int i = 0; i < sizeCube; i++) {
                            int x = getNumber(CubeName.DOWN)[now - 1][sizeCube - i - 1];
                            getNumber(CubeName.DOWN)[now - 1][sizeCube - i - 1] = getNumber(CubeName.RIGHT)[i][now - 1];
                            getNumber(CubeName.RIGHT)[i][now - 1] = getNumber(CubeName.UP)[sizeCube - now][i];
                            getNumber(CubeName.UP)[sizeCube - now][i] = getNumber(CubeName.LEFT)[sizeCube - i - 1][sizeCube - now];
                            getNumber(CubeName.LEFT)[sizeCube - i - 1][sizeCube - now] = x;
                        }
                    }
                } else {
                    for (int now = startIndex; now <= endIndex; now++) {
                        if (now == 1) rotateLeft(CubeName.FRONT); // Поворот самой грани
                        if (now == sizeCube) rotateRight(CubeName.BACK);
                        for (int i = 0; i < sizeCube; i++) {
                            int x = getNumber(CubeName.DOWN)[now - 1][sizeCube - i - 1];
                            getNumber(CubeName.DOWN)[now - 1][sizeCube - i - 1] = getNumber(CubeName.LEFT)[sizeCube - i - 1][sizeCube - now];
                            getNumber(CubeName.LEFT)[sizeCube - i - 1][sizeCube - now] = getNumber(CubeName.UP)[sizeCube - now][i];
                            getNumber(CubeName.UP)[sizeCube - now][i] = getNumber(CubeName.RIGHT)[i][now - 1];
                            getNumber(CubeName.RIGHT)[i][now - 1] = x;
                        }
                    }
                }
                break;
            }

            case RIGHT: {
                if (clock) {
                    for (int now = startIndex; now <= endIndex; now++) {
                        if (now == 1) rotateRight(CubeName.RIGHT);
                        if (now == sizeCube) rotateLeft(CubeName.LEFT);
                        for (int i = 0; i < sizeCube; i++) {
                            int x = getNumber(CubeName.DOWN)[i][sizeCube - now];
                            getNumber(CubeName.DOWN)[i][sizeCube - now] = getNumber(CubeName.BACK)[sizeCube - i - 1][now - 1];
                            getNumber(CubeName.BACK)[sizeCube - i - 1][now - 1] = getNumber(CubeName.UP)[i][sizeCube - now];
                            getNumber(CubeName.UP)[i][sizeCube - now] = getNumber(CubeName.FRONT)[i][sizeCube - now];
                            getNumber(CubeName.FRONT)[i][sizeCube - now] = x;
                        }
                    }
                } else {
                    for (int now = startIndex; now <= endIndex; now++) {
                        if (now == 1) rotateLeft(CubeName.RIGHT);
                        if (now == sizeCube) rotateRight(CubeName.LEFT);
                        for (int i = 0; i < sizeCube; i++) {
                            int x = getNumber(CubeName.FRONT)[i][sizeCube - now];
                            getNumber(CubeName.FRONT)[i][sizeCube - now] = getNumber(CubeName.UP)[i][sizeCube - now];
                            getNumber(CubeName.UP)[i][sizeCube - now] = getNumber(CubeName.BACK)[sizeCube - i -1][now - 1];
                            getNumber(CubeName.BACK)[sizeCube - i - 1][now - 1] = getNumber(CubeName.DOWN)[i][sizeCube - now];
                            getNumber(CubeName.DOWN)[i][sizeCube - now] = x;
                        }
                    }
                }
                break;
            }

            case LEFT: {
                if (clock) {
                    for (int now = startIndex; now <= endIndex; now++) {
                        if (now == 1) rotateRight(CubeName.LEFT);
                        if (now == sizeCube) rotateLeft(CubeName.RIGHT);
                        for (int i = 0; i < sizeCube; i++) {
                            int x = getNumber(CubeName.FRONT)[i][now - 1];
                            getNumber(CubeName.FRONT)[i][now - 1] = getNumber(CubeName.UP)[i][now - 1];
                            getNumber(CubeName.UP)[i][now - 1] = getNumber(CubeName.BACK)[sizeCube - i - 1][sizeCube - now];
                            getNumber(CubeName.BACK)[sizeCube - i - 1][sizeCube - now] = getNumber(CubeName.DOWN)[i][now - 1];
                            getNumber(CubeName.DOWN)[i][now - 1] = x;
                        }
                    }
                } else {
                    for (int now = startIndex; now <= endIndex; now++) {
                        if (now == 1) rotateLeft(CubeName.LEFT);
                        if (now == sizeCube) rotateRight(CubeName.RIGHT);
                        for (int i = 0; i < sizeCube; i++) {
                            int x = getNumber(CubeName.FRONT)[i][now - 1];
                            getNumber(CubeName.FRONT)[i][now - 1] = getNumber(CubeName.DOWN)[i][now - 1];
                            getNumber(CubeName.DOWN)[i][now - 1] = getNumber(CubeName.BACK)[sizeCube - i - 1][sizeCube - now];
                            getNumber(CubeName.BACK)[sizeCube - i - 1][sizeCube - now] = getNumber(CubeName.UP)[i][now - 1];
                            getNumber(CubeName.UP)[i][now - 1] = x;
                        }
                    }
                }
                break;
            }

            case UP: {
                if (clock) {
                    for (int now = startIndex; now <= endIndex; now++) {
                        if (now == 1) rotateRight(CubeName.UP);
                        if (now == sizeCube) rotateLeft(CubeName.DOWN);
                        for (int i = 0; i < sizeCube; i++) {
                            int x = cubeNow[CubeName.FRONT.num][now - 1][i];
                            getNumber(CubeName.FRONT)[now - 1][i] = getNumber(CubeName.RIGHT)[now - 1][i];
                            getNumber(CubeName.RIGHT)[now - 1][i] = getNumber(CubeName.BACK)[now - 1][i];
                            getNumber(CubeName.BACK)[now - 1][i] = getNumber(CubeName.LEFT)[now - 1][i];
                            getNumber(CubeName.LEFT)[now - 1][i] = x;
                        }
                    }
                } else {
                    for (int now = startIndex; now <= endIndex; now++) {
                        if (now == 1) rotateLeft(CubeName.UP);
                        if (now == sizeCube) rotateRight(CubeName.DOWN);
                        for (int i = 0; i < sizeCube; i++) {
                            int x = getNumber(CubeName.FRONT)[now - 1][i];
                            getNumber(CubeName.FRONT)[now - 1][i] = getNumber(CubeName.LEFT)[now - 1][i];
                            getNumber(CubeName.LEFT)[now - 1][i] = getNumber(CubeName.BACK)[now - 1][i];
                            getNumber(CubeName.BACK)[now - 1][i] = getNumber(CubeName.RIGHT)[now - 1][i];
                            getNumber(CubeName.RIGHT)[now - 1][i] = x;
                        }
                    }
                }
                break;
            }

            case DOWN: {
                if (clock) {
                    for (int now = startIndex; now <= endIndex; now++) {
                        if (now == 1) rotateRight(CubeName.DOWN);
                        if (now == sizeCube) rotateLeft(CubeName.UP);
                        for (int i = 0; i < sizeCube; i++) {
                            int x = getNumber(CubeName.FRONT)[sizeCube - now][i];
                            getNumber(CubeName.FRONT)[sizeCube - now][i] = getNumber(CubeName.LEFT)[sizeCube - now][i];
                            getNumber(CubeName.LEFT)[sizeCube - now][i] = getNumber(CubeName.BACK)[sizeCube - now][i];
                            getNumber(CubeName.BACK)[sizeCube - now][i] = getNumber(CubeName.RIGHT)[sizeCube - now][i];
                            getNumber(CubeName.RIGHT)[sizeCube - now][i] = x;
                        }
                    }
                } else {
                    for (int now = startIndex; now <= endIndex; now++) {
                        if (now == 1) rotateLeft(CubeName.DOWN);
                        if (now == sizeCube) rotateRight(CubeName.UP);
                        for (int i = 0; i < sizeCube; i++) {
                            int x = getNumber(CubeName.FRONT)[sizeCube - now][i];
                            getNumber(CubeName.FRONT)[sizeCube - now][i] = getNumber(CubeName.RIGHT)[sizeCube - now][i];
                            getNumber(CubeName.RIGHT)[sizeCube - now][i] = getNumber(CubeName.BACK)[sizeCube - now][i];
                            getNumber(CubeName.BACK)[sizeCube - now][i] = getNumber(CubeName.LEFT)[sizeCube - now][i];
                            getNumber(CubeName.LEFT)[sizeCube - now][i] = x;
                        }
                    }
                }
                break;
            }
            case BACK: {
                if (clock) {
                    for (int now = startIndex; now <= endIndex; now++) {
                        if (now == 1) rotateRight(CubeName.BACK);
                        if (now == sizeCube) rotateLeft(CubeName.FRONT);
                        for (int i = 0; i < sizeCube; i++) {
                            int x = getNumber(CubeName.DOWN)[sizeCube - now][sizeCube - i - 1];
                            getNumber(CubeName.DOWN)[sizeCube - now][sizeCube - i - 1] = getNumber(CubeName.LEFT)[sizeCube - i - 1][now - 1];
                            getNumber(CubeName.LEFT)[sizeCube - i - 1][now - 1] = getNumber(CubeName.UP)[now - 1][i];
                            getNumber(CubeName.UP)[now - 1][i] = getNumber(CubeName.RIGHT)[i][sizeCube - now];
                            getNumber(CubeName.RIGHT)[i][sizeCube - now] = x;
                        }
                    }
                } else {
                    for (int now = startIndex; now <= endIndex; now++) {
                        if (now == 1) rotateLeft(CubeName.BACK);
                        if (now == sizeCube) rotateRight(CubeName.FRONT);
                        for (int i = 0; i < sizeCube; i++) {
                            int x = getNumber(CubeName.DOWN)[sizeCube - now][sizeCube - i - 1];
                            getNumber(CubeName.DOWN)[sizeCube - now][sizeCube - i - 1] = getNumber(CubeName.RIGHT)[i][sizeCube - now];
                            getNumber(CubeName.RIGHT)[i][sizeCube - now] = getNumber(CubeName.UP)[now - 1][i];
                            getNumber(CubeName.UP)[now - 1][i] = getNumber(CubeName.LEFT)[sizeCube - i - 1][now - 1];
                            getNumber(CubeName.LEFT)[sizeCube - i - 1][now - 1] = x;
                        }
                    }
                }
                break;
            }
        }
    }

    void rotateCubeTo(Rotates move) {  // Поворот куба
        switch (move) {
            case DOWN: {
                int[][] x = cubeNow[CubeName.FRONT.num];
                cubeNow[CubeName.FRONT.num] = cubeNow[CubeName.DOWN.num];
                cubeNow[CubeName.DOWN.num] = cubeNow[CubeName.BACK.num];
                cubeNow[CubeName.BACK.num] = cubeNow[CubeName.UP.num];
                cubeNow[CubeName.UP.num] = x;

                rotateLeft(CubeName.LEFT); //90

                rotateRight(CubeName.RIGHT, CubeName.BACK, CubeName.BACK, CubeName.DOWN, CubeName.DOWN); //90

                break;
            }
            case RIGHT: {
                int[][] x = cubeNow[CubeName.FRONT.num];
                cubeNow[CubeName.FRONT.num] = cubeNow[CubeName.RIGHT.num];
                cubeNow[CubeName.RIGHT.num] = cubeNow[CubeName.BACK.num];
                cubeNow[CubeName.BACK.num] = cubeNow[CubeName.LEFT.num];
                cubeNow[CubeName.LEFT.num] = x;

                rotateRight(CubeName.UP); //90

                rotateLeft(CubeName.DOWN); //90
                break;
            }
            case LEFT: {
                int[][] x = cubeNow[CubeName.FRONT.num];
                cubeNow[CubeName.FRONT.num] = cubeNow[CubeName.LEFT.num];
                cubeNow[CubeName.LEFT.num] = cubeNow[CubeName.BACK.num];
                cubeNow[CubeName.BACK.num] = cubeNow[CubeName.RIGHT.num];
                cubeNow[CubeName.RIGHT.num] = x;

                rotateLeft(CubeName.UP); //90

                rotateRight(CubeName.DOWN); //90
                break;
            }
            case UP: {
                int[][] x = cubeNow[CubeName.FRONT.num];
                cubeNow[CubeName.FRONT.num] = cubeNow[CubeName.UP.num];
                cubeNow[CubeName.UP.num] = cubeNow[CubeName.BACK.num];
                cubeNow[CubeName.BACK.num] = cubeNow[CubeName.DOWN.num];
                cubeNow[CubeName.DOWN.num] = x;

                rotateRight(CubeName.LEFT, CubeName.UP, CubeName.UP, CubeName.BACK, CubeName.BACK); //90

                rotateLeft(CubeName.RIGHT); //90

                break;
            }
            case FRONT_RIGHT: {
                int[][] x = cubeNow[CubeName.DOWN.num];
                cubeNow[CubeName.DOWN.num] = cubeNow[CubeName.RIGHT.num];
                cubeNow[CubeName.RIGHT.num] = cubeNow[CubeName.UP.num];
                cubeNow[CubeName.UP.num] = cubeNow[CubeName.LEFT.num];
                cubeNow[CubeName.LEFT.num] = x;

                rotateRight(CubeName.FRONT, CubeName.RIGHT, CubeName.LEFT, CubeName.UP, CubeName.DOWN); //90

                rotateLeft(CubeName.BACK);
                break;
            }
            case FRONT_LEFT: {
                int[][] x = cubeNow[CubeName.DOWN.num];
                cubeNow[CubeName.DOWN.num] = cubeNow[CubeName.LEFT.num];
                cubeNow[CubeName.LEFT.num] = cubeNow[CubeName.UP.num];
                cubeNow[CubeName.UP.num] = cubeNow[CubeName.RIGHT.num];
                cubeNow[CubeName.RIGHT.num] = x;

                rotateLeft(CubeName.FRONT, CubeName.RIGHT, CubeName.LEFT, CubeName.UP, CubeName.DOWN); //90

                rotateRight(CubeName.BACK);
            }
        }
    }

    public int getSizeCube() {  // Выводит размер
        return sizeCube;
    }

    public int[][][] getCubeNow() {  // Выводит сам куб
        int[][][] cube = new int[6][sizeCube][sizeCube];
        for (int i = 0; i < cube.length; i++) {
            for (int j = 0; j < cube[i].length; j++) {
                cube[i][j] = cubeNow[i][j].clone();
            }
        }
        return cube;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) throw new IllegalArgumentException("null");
        if (this == obj) return true;
        if (!(obj instanceof Cube)) return false;
        Cube other = (Cube) obj;
        if (other.sizeCube != sizeCube) return false;
        int[][][] cubeArr = new int[6][sizeCube][sizeCube];
        for (int i = 0; i < this.cubeNow.length; i++) {
            for (int j = 0; j < this.cubeNow[i].length; j++) {
                cubeArr[i][j] = this.cubeNow[i][j].clone();
            }
        }
        Cube cube = new Cube(sizeCube, cubeArr);
        boolean res = false;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    for (int m = 0; m < 6; m++) {
                        res = Arrays.deepEquals(cube.cubeNow[m], other.cubeNow[m]);
                    }
                    if (res) return true;
                    cube.rotateCubeTo(Rotates.LEFT);
                }
                cube.rotateCubeTo(Rotates.FRONT_RIGHT);
            }
            cube.rotateCubeTo(Rotates.UP);
            cube.rotateCubeTo(Rotates.FRONT_RIGHT);
            cube.rotateCubeTo(Rotates.FRONT_RIGHT);
        }
        return false;
    }

    @Override
    public String toString() {  // Просмотр всего куба в целом
        int size = sizeCube;
        String s1 = " ".repeat(2 * size + size + 3) + ANSI_RESET + ("—").repeat(2 * size + size + 1);
        StringBuilder text = new StringBuilder(s1 + "\n");
        for (int j = 0; j < sizeCube; j++) {
            text.append(" ".repeat(2 * size + size + 2)).append(ANSI_RESET + "|");
            for (int k = 0; k < sizeCube; k++) {
                text.append(" ").append(color[cubeNow[CubeName.UP.num][j][k]]);
            }
            text.append(ANSI_RESET + " |\n");
        }
        String s2 = ANSI_RESET + " " + "—".repeat(2 * size + size + 1) + " " + "—".repeat(2 * size + size + 1) +
                " " + "—".repeat(2 * size + size + 1) + " " + "—".repeat(2 * size + size + 1);
        text.append(s2).append("\n");
        for (int j = 0; j < sizeCube; j++) {
            text.append(ANSI_RESET + "|");
            for (int k = 0; k < sizeCube; k++) {
                text.append(" ").append(color[cubeNow[CubeName.LEFT.num][j][k]]);
            }
            text.append(ANSI_RESET + " |");
            for (int k = 0; k < sizeCube; k++) {
                text.append(" ").append(color[cubeNow[CubeName.FRONT.num][j][k]]);
            }
            text.append(ANSI_RESET + " |");
            for (int k = 0; k < sizeCube; k++) {
                text.append(" ").append(color[cubeNow[CubeName.RIGHT.num][j][k]]);
            }
            text.append(ANSI_RESET + " |");
            for (int k = 0; k < sizeCube; k++) {
                text.append(" ").append(color[cubeNow[CubeName.BACK.num][j][k]]);
            }
            text.append(ANSI_RESET + " |").append("\n");
        }
        text.append(s2).append("\n");
        for (int j = 0; j < sizeCube; j++) {
            text.append(" ".repeat(2 * size + size + 2)).append(ANSI_RESET + "|");
            for (int k = 0; k < sizeCube; k++) {
                text.append(" ").append(color[cubeNow[CubeName.DOWN.num][j][k]]);
            }
            text.append(ANSI_RESET + " |").append("\n");
        }
        text.append(s1).append("\n");
        return text.toString();
    }

    public void toStringOneBrink(CubeName name) {  // Просмотр одной стороны
        StringBuilder text = new StringBuilder(" ".repeat(sizeCube + 1) + name).append("\n");
        for (int j = 0; j < sizeCube; j++) {
            for (int k = 0; k < sizeCube; k++) {
                text.append(" ").append(color[cubeNow[name.num][j][k]], 0, 12);
            }
            text.append("\n");
        }
        System.out.println(text);
    }

    @Override
    public int hashCode() { return Arrays.deepHashCode(cubeNow); }
}