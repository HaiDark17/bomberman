package oop.bomberman.entities.character.enemy.algorithm;

import java.util.Random;

public abstract class Algorithm {
    protected Random random = new Random();

    public abstract int getDirection();
}
