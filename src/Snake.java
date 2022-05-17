package com.javarush.games.snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    public Snake(int x, int y) {
        GameObject gameObject1 = new GameObject(x, y);
        GameObject gameObject2 = new GameObject(x + 1, y);
        GameObject gameObject3 = new GameObject(x + 2, y);
        snakeParts.add(gameObject1);
        snakeParts.add(gameObject2);
        snakeParts.add(gameObject3);
    }

    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    private List<GameObject> snakeParts = new ArrayList<>();

    public void setDirection(Direction direction) {
        boolean reverseBol = this.direction == Direction.LEFT && direction == Direction.RIGHT ||
                this.direction == Direction.RIGHT && direction == Direction.LEFT ||
                this.direction == Direction.UP && direction == Direction.DOWN ||
                this.direction == Direction.DOWN && direction == Direction.UP;
        boolean validationLeft = this.direction ==Direction.LEFT && snakeParts.get(0).x == snakeParts.get(1).x;
        boolean validationRight = this.direction ==Direction.RIGHT && snakeParts.get(0).x == snakeParts.get(1).x;
        boolean validationUP = this.direction ==Direction.UP && snakeParts.get(0).y == snakeParts.get(1).y;
        boolean validationDown = this.direction ==Direction.DOWN && snakeParts.get(0).y == snakeParts.get(1).y;
        if (!(reverseBol || validationLeft || validationRight || validationUP || validationDown)) {
            this.direction = direction;
        }

    }

    public GameObject createNewHead() {
        GameObject newHead = null;
        if (direction == Direction.LEFT) {
            newHead = new GameObject(snakeParts.get(0).x - 1, snakeParts.get(0).y);
        } else if (direction == Direction.RIGHT) {
            newHead = new GameObject(snakeParts.get(0).x + 1, snakeParts.get(0).y);
        } else if (direction == Direction.UP) {
            newHead = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y - 1);
        } else if (direction == Direction.DOWN) {
            newHead = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y + 1);
        }
        return newHead;
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.get(snakeParts.size() - 1));
    }

    public void move(Apple apple) {
        GameObject newHead = createNewHead();
        boolean borderScreenSize = newHead.x >= 0 && newHead.x <= SnakeGame.WIDTH - 1 &&
                newHead.y >= 0 && newHead.y <= SnakeGame.HEIGHT - 1;
        if (newHead.x == apple.x && newHead.y == apple.y) {
            apple.isAlive = false;
            snakeParts.add(0, newHead);
        } else if (borderScreenSize && !(newHead.x == apple.x && newHead.y == apple.y)) {
            if (checkCollision(newHead)){
                isAlive = false;
            } else {
                snakeParts.add(0, newHead);
                removeTail();
            }

        } else if (!borderScreenSize) {
            isAlive = false;
        } else {
            removeTail();
        }
    }

    public void draw(Game game) {
        Color colorSnake;
        if (isAlive) {
            colorSnake = Color.GREEN;
        } else {
            colorSnake = Color.RED;
        }
        for (int i = 0; i < snakeParts.size(); i++) {
            GameObject gameObject = snakeParts.get(i);
            if (i == 0) {
                game.setCellValueEx(gameObject.x, gameObject.y, Color.NONE, HEAD_SIGN, colorSnake, 75);
            } else {
                game.setCellValueEx(gameObject.x, gameObject.y, Color.NONE, BODY_SIGN, colorSnake, 75);
            }
        }
    }

    public boolean checkCollision(GameObject gameObject) {
        boolean result = false;
        for (int i = 0; i < snakeParts.size(); i++) {
            if (gameObject.x == snakeParts.get(i).x && gameObject.y == snakeParts.get(i).y) {
                result = true;
            }
        }
        return result;
    }
    public int getLength(){
        return snakeParts.size();
    }
}
