package com.javarush.games.snake;

import com.javarush.engine.cell.*;

public class SnakeGame extends Game {
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private Snake snake;
    private int turnDelay;
    private Apple apple;
    private boolean isGameStopped;
    private final static int GOAL = 28;
    private int score;

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();

    }

    @Override
    public void onTurn(int step) {
        snake.move(apple);
        if (apple.isAlive == false) {
            createNewApple();
            score = score + 5;
            setScore(score);
            turnDelay = turnDelay - 10;
            setTurnTimer(turnDelay);
        }
        if (snake.isAlive == false) {
            gameOver();
        }
        if (snake.getLength() > GOAL) {
            win();
        }
        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {
        if (key == Key.LEFT) {
            snake.setDirection(Direction.LEFT);
        } else if (key == Key.RIGHT) {
            snake.setDirection(Direction.RIGHT);
        } else if (key == Key.DOWN) {
            snake.setDirection(Direction.DOWN);
        } else if (key == Key.UP) {
            snake.setDirection(Direction.UP);
        } else if (key == Key.SPACE && isGameStopped == true) {
            createGame();
        }

    }

    private void drawScene() {
        for (int x = 0; x < 15; x++) {
            for (int y = 0; y < 15; y++) {
                setCellValueEx(x, y, Color.DARKGRAY, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    private void createGame() {
        score = 0;
        Snake snake1 = new Snake(WIDTH / 2, HEIGHT / 2);
        this.snake = snake1;
        createNewApple();
        isGameStopped = false;
        setScore(score);
        drawScene();
        turnDelay = 300;
        setTurnTimer(turnDelay);
    }

    private void createNewApple() {
        Apple newApple;
        do {
            int x = getRandomNumber(WIDTH);
            int y = getRandomNumber(HEIGHT);
            newApple = new Apple(x, y);
        } while (snake.checkCollision(newApple));
        apple = newApple;
    }


    private void gameOver() {
        this.stopTurnTimer();
        isGameStopped = true;
        this.showMessageDialog(Color.BLACK, "GAME OVER", Color.RED, 75);
    }

    private void win() {
        this.stopTurnTimer();
        isGameStopped = true;
        this.showMessageDialog(Color.BLACK, "YOU WIN", Color.GREEN, 75);
    }

    @Override
    public void setScore(int score) {
        super.setScore(score);
        this.score = score;
    }
}
