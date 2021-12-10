package oop.bomberman.entities.mob.enemy.algorithm;

public class SimpleAlgo extends Algorithm {
    @Override
    public int getDirection() {
        return random.nextInt(4);
    }
}
