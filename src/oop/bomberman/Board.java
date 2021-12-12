package oop.bomberman;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oop.bomberman.entities.Entity;
import oop.bomberman.entities.Message;
import oop.bomberman.entities.bomb.Bomb;
import oop.bomberman.entities.bomb.Explosion;
import oop.bomberman.entities.character.Character;
import oop.bomberman.entities.character.Player;
import oop.bomberman.entities.tile.powerup.Powerup;
import oop.bomberman.graphics.IRender;
import oop.bomberman.graphics.Screen;
import oop.bomberman.input.Keyboard;
import oop.bomberman.level.FileLevel;
import oop.bomberman.level.Level;

public class Board implements IRender {

    public int _width, _height;
    protected Level _level;
    protected Game _game;
    protected Keyboard _input;
    protected Screen _screen;

    public Entity[] _entities;
    public List<Character> _mobs = new ArrayList<Character>();
    protected List<Bomb> _bombs = new ArrayList<Bomb>();
    private List<Message> _messages = new ArrayList<Message>();

    private int _screenToShow = -1; //1:endgame, 2:changelevel, 3:paused

    private int _time = Game.TIME;
    private int points = Game.POINTS;
    private int _lives = Game.LIVES;

    public Board(Game game, Keyboard input, Screen screen) {
        _game = game;
        _input = input;
        _screen = screen;

        _screenToShow = 4;
        //changeLevel(1);
    }

    /*
    |--------------------------------------------------------------------------
    | Render & Update
    |--------------------------------------------------------------------------
     */
    @Override
    public void update() {
        if (_game.isPaused()) return;

        updateEntities();
        updateMobs();
        updateBombs();
        updateMessages();
        detectEndGame();

        for (int i = 0; i < _mobs.size(); i++) {
            Character a = _mobs.get(i);
            if (((Entity) a).isRemoved()) _mobs.remove(i);
        }
    }


    @Override
    public void render(Screen screen) {
        if (_game.isPaused()) return;

        //only render the visible part of screen
        int x0 = Screen.xOffset >> 4; //tile precision, -> left X
        int x1 = (Screen.xOffset + screen.getWidth() + Game.TILES_SIZE) / Game.TILES_SIZE; // -> right X
        int y0 = Screen.yOffset >> 4;
        int y1 = (Screen.yOffset + screen.getHeight()) / Game.TILES_SIZE; //render one tile plus to fix black margins

        for (int y = y0; y < y1; y++) {
            for (int x = x0; x < x1; x++) {
                _entities[x + y * _level.getWidth()].render(screen);
            }
        }

        renderBombs(screen);
        renderMobs(screen);

    }

    /*
    |--------------------------------------------------------------------------
    | ChangeLevel
    |--------------------------------------------------------------------------
     */
    public void newGame() {
        resetProperties();
        changeLevel(1);
    }

    @SuppressWarnings("static-access")
    private void resetProperties() {
        points = Game.POINTS;
        _lives = Game.LIVES;
        Player._powerups.clear();

        _game.playerSpeed = 1.0;
        _game.bombRadius = 1;
        _game.bombRate = 1;

    }

    public void restartLevel() {
        changeLevel(_level.getLevel());
    }

    public void nextLevel() {
        changeLevel(_level.getLevel() + 1);
    }

    public void changeLevel(int level) {
        _time = Game.TIME;
        _screenToShow = 2;
        _game.resetScreenDelay();
        _game.pause();
        _mobs.clear();
        _bombs.clear();
        _messages.clear();

        _level = new FileLevel("levels/Level" + level + ".txt", this);
        _entities = new Entity[_level.getHeight() * _level.getWidth()];

        _level.createEntities();

    }

    public void changeLevelByCode(String str) {
        int i = _level.validCode(str);

        if (i != -1) {
            if (_game.isSetting()) {
                _game.setSetting(false);
            }
            changeLevel(i + 1);
        }
    }

    public boolean isPowerupUsed(int x, int y, int level) {
        Powerup p;
        for (int i = 0; i < Player._powerups.size(); i++) {
            p = Player._powerups.get(i);
            if (p.getX() == x && p.getY() == y && level == p.getLevel())
                return true;
        }

        return false;
    }

    /*
    |--------------------------------------------------------------------------
    | Detections
    |--------------------------------------------------------------------------
     */
    protected void detectEndGame() {
        if (_time <= 0)
            restartLevel();
    }

    public void endGame() {
        _screenToShow = 1;
        _game.resetScreenDelay();
        _game.pause();
        _game.isEndgame = true;
        if(getPoints() >= _game.get_highscore()){
            _game.set_highscore(getPoints());
            _game.saveHighScore();
        }
    }

    public boolean detectNoEnemies() {
        int total = 0;
        for (int i = 0; i < _mobs.size(); i++) {
            if (!(_mobs.get(i) instanceof Player))
                ++total;
        }

        return total == 0;
    }

    /*
    |--------------------------------------------------------------------------
    | Pause & Resume
    |--------------------------------------------------------------------------
     */
    public void gamePauseOnSetting() {
        _game.resetScreenDelay();
        _screenToShow = 5;
        _game.pause();
        _game.setSetting(true);
    }

    public void gamePauseOnReset(){
        _game.resetScreenDelay();
        _screenToShow = 7;
        _game.pause();
        _game.isResetGame = true;
    }

    public void gameResume() {
        _game.resetScreenDelay();
        _screenToShow = -1;
        _game.run();
    }

    /*
    |--------------------------------------------------------------------------
    | Screens
    |--------------------------------------------------------------------------
     */
    public void drawScreen(Graphics g) {
        _screen.intializeFont();
        switch (_screenToShow) {
            case 1:
                _screen.drawEndGame(g, points, _game.get_highscore(), _level.getLevel());
                break;
            case 2:
                _screen.drawChangeLevel(g, _level.getLevel());
                break;
            case 3:
                _screen.drawPaused(g);
                break;
            case 4:
                _screen.drawMenu(g);
                break;
            case 5:
                _screen.drawSetting(g);
                break;
            case 6:
                _screen.drawAbout(g);
                break;
            case 7:
                _screen.drawChooseNewGame(g);
                break;
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Getters And Setters
    |--------------------------------------------------------------------------
     */
    public Entity getEntity(double x, double y, Character m) {

        Entity res = null;

        res = getExplosionAt((int) x, (int) y);
        if (res != null) return res;

        res = getBombAt(x, y);
        if (res != null) return res;

        res = getMobAtExcluding((int) x, (int) y, m);
        if (res != null) return res;

        res = getEntityAt((int) x, (int) y);

        return res;
    }

    public List<Bomb> getBombs() {
        return _bombs;
    }

    public Bomb getBombAt(double x, double y) {
        Iterator<Bomb> bs = _bombs.iterator();
        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.getX() == (int) x && b.getY() == (int) y)
                return b;
        }

        return null;
    }

    public Character getMobAt(double x, double y) {
        Iterator<Character> itr = _mobs.iterator();
        Character cur;
        while (itr.hasNext()) {
            cur = itr.next();

            if (cur.getXTile() == x && cur.getYTile() == y)
                return cur;
        }

        return null;
    }

    public Player getPlayer() {
        Iterator<Character> itr = _mobs.iterator();
        Character cur;
        while (itr.hasNext()) {
            cur = itr.next();

            if (cur instanceof Player)
                return (Player) cur;
        }

        return null;
    }

    public Character getMobAtExcluding(int x, int y, Character a) {
        Iterator<Character> itr = _mobs.iterator();
        Character cur;
        while (itr.hasNext()) {
            cur = itr.next();
            if (cur == a) {
                continue;
            }

            if (cur.getXTile() == x && cur.getYTile() == y) {
                return cur;
            }

        }

        return null;
    }

    public Explosion getExplosionAt(int x, int y) {
        Iterator<Bomb> bs = _bombs.iterator();
        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();

            Explosion e = b.explosionAt(x, y);
            if (e != null) {
                return e;
            }

        }

        return null;
    }

    public Entity getEntityAt(double x, double y) {
        return _entities[(int) x + (int) y * _level.getWidth()];
    }

    /*
    |--------------------------------------------------------------------------
    | Adds and Removes
    |--------------------------------------------------------------------------
     */
    public void addEntitie(int pos, Entity e) {
        _entities[pos] = e;
    }

    public void addMob(Character e) {
        _mobs.add(e);
    }

    public void addBomb(Bomb e) {
        _bombs.add(e);
    }

    public void addMessage(Message e) {
        _messages.add(e);
    }

    /*
    |--------------------------------------------------------------------------
    | Renders
    |--------------------------------------------------------------------------
     */
    protected void renderEntities(Screen screen) {
        for (int i = 0; i < _entities.length; i++) {
            _entities[i].render(screen);
        }
    }

    protected void renderMobs(Screen screen) {
        Iterator<Character> itr = _mobs.iterator();

        while (itr.hasNext())
            itr.next().render(screen);
    }

    protected void renderBombs(Screen screen) {
        Iterator<Bomb> itr = _bombs.iterator();

        while (itr.hasNext())
            itr.next().render(screen);
    }

    public void renderMessages(Graphics g) {
        Message m;
        for (int i = 0; i < _messages.size(); i++) {
            m = _messages.get(i);

            g.setFont(new Font("Arial", Font.PLAIN, m.getSize()));
            g.setColor(m.getColor());
            g.drawString(m.getMessage(), (int) m.getX() - Screen.xOffset * Game.SCALE, (int) m.getY());
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Updates
    |--------------------------------------------------------------------------
     */
    protected void updateEntities() {
        if (_game.isPaused()) return;
        for (int i = 0; i < _entities.length; i++) {
            _entities[i].update();
        }
    }

    protected void updateMobs() {
        if (_game.isPaused()) return;
        Iterator<Character> itr = _mobs.iterator();

        while (itr.hasNext() && !_game.isPaused())
            itr.next().update();
    }

    protected void updateBombs() {
        if (_game.isPaused()) return;
        Iterator<Bomb> itr = _bombs.iterator();

        while (itr.hasNext())
            itr.next().update();
    }

    protected void updateMessages() {
        if (_game.isPaused()) return;
        Message m;
        int left = 0;
        for (int i = 0; i < _messages.size(); i++) {
            m = _messages.get(i);
            left = m.getDuration();

            if (left > 0)
                m.setDuration(--left);
            else
                _messages.remove(i);
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Getters & Setters
    |--------------------------------------------------------------------------
     */
    public Keyboard getInput() {
        return _input;
    }

    public Level getLevel() {
        return _level;
    }

    public Game getGame() {
        return _game;
    }

    public int getShow() {
        return _screenToShow;
    }

    public void setShow(int i) {
        _screenToShow = i;
    }

    public int getTime() {
        return _time;
    }

    public int getLives() {
        return _lives;
    }

    public int subtractTime() {
        if (_game.isPaused())
            return this._time;
        else
            return this._time--;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void resetPoints() {
        points = 0;
    }

    public void addLives(int lives) {
        this._lives += lives;
    }

    public int getWidth() {
        return _level.getWidth();
    }

    public int getHeight() {
        return _level.getHeight();
    }

}