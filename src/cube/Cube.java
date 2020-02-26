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

    public Cube(int size) { // Конструктор
        if (size < 0) throw new IllegalArgumentException("Wrong SIZE!");
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

    public Cube(int size, int[][][] cube) {
        if (size < 0) throw new IllegalArgumentException("Wrong SIZE!");
        this.sizeCube = size;
        this.cubeNow = cube.clone();
    }

    public void setSide(CubeName nameSide, int[][] side) { // Установка своего кубика
        this.cubeNow[nameSide.num] = side;
    }

    static private void rotateLeft(int[][] cube) { // Поворот квадратной матрицы в лево
        int size = cube.length;
        for (int j = 0; j < size / 2; j++) {
            for (int k = j; k < size - j - 1; k++) {
                int x = cube[j][k];
                cube[j][k] = cube[k][size - j - 1];
                cube[k][size - j - 1] = cube[size - j - 1][size - k - 1];
                cube[size - j - 1][size - k - 1] = cube[size - k - 1][j];
                cube[size - k - 1][j] = x;
            }
        }
    }

    static private void rotateRight(int[][] cube) { // Поворот квадратной матрицы в право
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


    private int[][] getNumber(CubeName name) {
        return this.cubeNow[name.num];
    }

    public void rotateBrink(CubeName name, int startIndex, int endIndex, boolean clock) {  // Поворот грани или центров
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
                        if (now == 1) rotateRight(getNumber(CubeName.FRONT));// Поворот самой грани
                        if (now == sizeCube) rotateLeft(getNumber(CubeName.BACK));
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
                        if (now == 1) rotateLeft(getNumber(CubeName.FRONT)); // Поворот самой грани
                        if (now == sizeCube) rotateRight(getNumber(CubeName.BACK));
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
                        if (now == 1) rotateRight(getNumber(CubeName.RIGHT));
                        if (now == sizeCube) rotateLeft(getNumber(CubeName.LEFT));
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
                        if (now == 1) rotateLeft(this.cubeNow[CubeName.RIGHT.num]);
                        if (now == sizeCube) rotateRight(this.cubeNow[CubeName.LEFT.num]);
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
                        if (now == 1) rotateRight(getNumber(CubeName.LEFT));
                        if (now == sizeCube) rotateLeft(getNumber(CubeName.RIGHT));
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
                        if (now == 1) rotateLeft(getNumber(CubeName.LEFT));
                        if (now == sizeCube) rotateRight(getNumber(CubeName.RIGHT));
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
                        if (now == 1) rotateRight(getNumber(CubeName.UP));
                        if (now == sizeCube) rotateLeft(getNumber(CubeName.DOWN));
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
                        if (now == 1) rotateLeft(getNumber(CubeName.UP));
                        if (now == sizeCube) rotateRight(getNumber(CubeName.DOWN));
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
                        if (now == 1) rotateRight(getNumber(CubeName.DOWN));
                        if (now == sizeCube) rotateLeft(getNumber(CubeName.UP));
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
                        if (now == 1) rotateLeft(getNumber(CubeName.DOWN));
                        if (now == sizeCube) rotateRight(getNumber(CubeName.UP));
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
                        if (now == 1) rotateRight(getNumber(CubeName.BACK));
                        if (now == sizeCube) rotateLeft(getNumber(CubeName.FRONT));
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
                        if (now == 1) rotateLeft(getNumber(CubeName.BACK));
                        if (now == sizeCube) rotateRight(getNumber(CubeName.FRONT));
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

    public void rotateCubeTo(Rotates move) {  // Поворот куба
        switch (move) {
            case DOWN: {
                int x[][] = cubeNow[CubeName.FRONT.num];
                cubeNow[CubeName.FRONT.num] = cubeNow[CubeName.DOWN.num];
                cubeNow[CubeName.DOWN.num] = cubeNow[CubeName.BACK.num];
                cubeNow[CubeName.BACK.num] = cubeNow[CubeName.UP.num];
                cubeNow[CubeName.UP.num] = x;

                rotateLeft(getNumber(CubeName.LEFT)); //90

                rotateRight(getNumber(CubeName.RIGHT)); //90

                rotateRight(getNumber(CubeName.BACK)); //180
                rotateRight(getNumber(CubeName.BACK));

                rotateRight(getNumber(CubeName.DOWN)); //180
                rotateRight(getNumber(CubeName.DOWN));
                break;
            }
            case RIGHT: {
                int x[][] = cubeNow[CubeName.FRONT.num];
                cubeNow[CubeName.FRONT.num] = cubeNow[CubeName.RIGHT.num];
                cubeNow[CubeName.RIGHT.num] = cubeNow[CubeName.BACK.num];
                cubeNow[CubeName.BACK.num] = cubeNow[CubeName.LEFT.num];
                cubeNow[CubeName.LEFT.num] = x;

                rotateRight(getNumber(CubeName.UP)); //90

                rotateLeft(getNumber(CubeName.DOWN)); //90
                break;
            }
            case LEFT: {
                int[][] x = cubeNow[CubeName.FRONT.num];
                cubeNow[CubeName.FRONT.num] = cubeNow[CubeName.LEFT.num];
                cubeNow[CubeName.LEFT.num] = cubeNow[CubeName.BACK.num];
                cubeNow[CubeName.BACK.num] = cubeNow[CubeName.RIGHT.num];
                cubeNow[CubeName.RIGHT.num] = x;

                rotateLeft(getNumber(CubeName.UP)); //90

                rotateRight(getNumber(CubeName.DOWN)); //90
                break;
            }
            case UP: {
                int[][] x = cubeNow[CubeName.FRONT.num];
                cubeNow[CubeName.FRONT.num] = cubeNow[CubeName.UP.num];
                cubeNow[CubeName.UP.num] = cubeNow[CubeName.BACK.num];
                cubeNow[CubeName.BACK.num] = cubeNow[CubeName.DOWN.num];
                cubeNow[CubeName.DOWN.num] = x;

                rotateRight(getNumber(CubeName.LEFT)); //90

                rotateLeft(getNumber(CubeName.RIGHT)); //90

                rotateRight(getNumber(CubeName.UP)); //180
                rotateRight(getNumber(CubeName.UP));

                rotateRight(getNumber(CubeName.BACK)); //180
                rotateRight(getNumber(CubeName.BACK));
                break;
            }
            case FRONT_RIGHT: {
                int[][] x = cubeNow[CubeName.DOWN.num];
                cubeNow[CubeName.DOWN.num] = cubeNow[CubeName.RIGHT.num];
                cubeNow[CubeName.RIGHT.num] = cubeNow[CubeName.UP.num];
                cubeNow[CubeName.UP.num] = cubeNow[CubeName.LEFT.num];
                cubeNow[CubeName.LEFT.num] = x;

                rotateRight(getNumber(CubeName.FRONT)); //90

                rotateRight(getNumber(CubeName.RIGHT));

                rotateRight(getNumber(CubeName.LEFT));

                rotateRight(getNumber(CubeName.UP));

                rotateRight(getNumber(CubeName.DOWN));

                rotateLeft(getNumber(CubeName.BACK));
                break;
            }
            case FRONT_LEFT: {
                int[][] x = cubeNow[CubeName.DOWN.num];
                cubeNow[CubeName.DOWN.num] = cubeNow[CubeName.LEFT.num];
                cubeNow[CubeName.LEFT.num] = cubeNow[CubeName.UP.num];
                cubeNow[CubeName.UP.num] = cubeNow[CubeName.RIGHT.num];
                cubeNow[CubeName.RIGHT.num] = x;

                rotateLeft(getNumber(CubeName.FRONT)); //90

                rotateLeft(getNumber(CubeName.RIGHT));

                rotateLeft(getNumber(CubeName.LEFT));

                rotateLeft(getNumber(CubeName.UP));

                rotateLeft(getNumber(CubeName.DOWN));

                rotateRight(getNumber(CubeName.BACK));
            }
        }
    }

    public int getSizeCube() {  // Выводит размер
        return sizeCube;
    }

    public int[][][] getCubeNow() {  // Выводит сам куб
        return cubeNow.clone();
    }

    static private int search(int[] arr, int key){
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == key) return i;
        }
        return -1;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Cube)) return false;
        Cube other = (Cube) obj;
        if (other.sizeCube != sizeCube) return false;
        boolean res1, res2;
        boolean coincides = false;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    res1 = true;
                    for (int m = 0; m < 6; m++) {
                        res2 = true;
                        for (int ii = 0; ii < sizeCube; ii++) {
                                res2 &= Arrays.equals(cubeNow[m][ii], other.cubeNow[m][ii]);
                        }
                        res1 &= res2;
                    }
                    coincides |=res1;
                    if (coincides) return true;
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
    public int hashCode() {
        return Arrays.deepHashCode(cubeNow);
    }
}