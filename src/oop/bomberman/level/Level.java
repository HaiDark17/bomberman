package oop.bomberman.level;

import oop.bomberman.Board;


public abstract class Level implements ILevel {

    protected int width, height, level;
    protected String[] lineTiles;
    protected Board board;

    protected static String[] codes = {
            "ng3I/uQttnHX1/EFFjFj3Rv/nNRfE2upZOenQbEsXPE=",
            "jB+b1iEWYFoh6GXkYn3k4GbcI9Eo3D1qlDgu8ZBVyhI=",
            "Kkp03H5BNbKGP9giHdY7aA5+YRmYCgPFJ5/Eo17pKDo=",
            "HmpJWLnwd6dIUB3BcYZ8/IRAEHBQm4EnLTwcrYm8/zI=",
            "8drcJZ+XiULPkoT5xiYSaSThf01dpewt9hjfvV9ufOs=",
            "test0",
            "test1",
    };

    public Level(String path, Board board) {
        loadLevel(path);
        this.board = board;
    }

    @Override
    public abstract void loadLevel(String path);

    public abstract void createEntities();

    public int validCode(String str) {
        for (int i = 0; i < codes.length; i++) {
            if (codes[i].equals(str)) {
                return i;
            }
        }
        return -1;
    }

    public String getActualCode() {
        return codes[level - 1];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getLevel() {
        return level;
    }

}
