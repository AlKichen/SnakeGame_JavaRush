package com.javarush.games.snake;

import com.javarush.engine.cell.*;

public class Apple extends GameObject {
    Apple(int x, int y) {
        super(x, y);
    }

    public boolean isAlive = true;
    private static final String APPLE_SIGN = "\uD83C\uDF4E";

    public void draw(Game game) {
        game.setCellValueEx(x, y, Color.NONE, APPLE_SIGN, Color.RED, 75);
    }

}