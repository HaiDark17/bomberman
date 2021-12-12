package oop.bomberman.entities.character.enemy.algorithm;

public class SimpleAlgo extends Algorithm {
    @Override
    public int getDirection() {
        return random.nextInt(4);
    }
}
