package oop.bomberman;

import oop.bomberman.audio.Audio;
import oop.bomberman.graphics.SpriteSheet;

public interface CommonVariables {
    Audio audio = new Audio();

    SpriteSheet character = new SpriteSheet("/textures/character.png", 64);
    SpriteSheet bomb = new SpriteSheet("/textures/bomb.png", 64);
    SpriteSheet item = new SpriteSheet("/textures/item.png", 64);
    SpriteSheet enemy = new SpriteSheet("/textures/enemy.png", 128);
    SpriteSheet map0 = new SpriteSheet("/textures/erangel.png", 64);
    SpriteSheet map1 = new SpriteSheet("/textures/miramar.png", 64);
    SpriteSheet map = new SpriteSheet("/textures/erangel.png", 64);
}
