package oop.bomberman.entities.mob.enemy.algorithm;

import java.util.Random;

public abstract class Algorithm {
    protected Random random = new Random();

    public abstract int calculateDirection();
}
