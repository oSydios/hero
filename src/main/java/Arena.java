import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Arena {
    private int width;
    private int height;
    private Hero hero;
    private List<Wall> walls;
    private List<Coin> coins;
    private List<Monster> monsters;

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.hero = new Hero(width / 2, height / 2);
        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters = createMonsters();
    }
    private List<Monster> createMonsters() {
        Random random = new Random();
        ArrayList<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            monsters.add(new Monster(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));
        return monsters;
    }
    private boolean isMonsterOverlapping(Monster newMonster) {
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(newMonster.getPosition())) {
                return true;
            }
        }
        return false;
    }
    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            walls.add(new Wall(i, 0));
            walls.add(new Wall(i, height - 1));
        }
        for (int j = 1; j < height - 1; j++) {
            walls.add(new Wall(0, j));
            walls.add(new Wall(width - 1, j));
        }
        return walls;
    }
    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            coins.add(new Coin(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));
        return coins;
    }
    private boolean isCoinOverlapping(Coin coin) {
        if (coins == null) {
            return false;
        }
        for (Coin existingCoin : coins) {
            if (existingCoin.getPosition().equals(coin.getPosition())) {
                return true;
            }
        }
        return false;
    }
    public void moveHero(Position position) {
        if (canHeroMove(position)) {
            hero.setPosition(position);
            retrieveCoins();
            verifyMonsterCollisions();
        }
    }
    private boolean canHeroMove(Position position) {
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)) {
                return false;
            }
        }
        return position.getX() >= 0 && position.getX() < width && position.getY() >= 0 && position.getY() < height;
    }
    private void retrieveCoins() {
        List<Coin> collectedCoins = new ArrayList<>();
        for (Coin coin : coins) {
            if (coin.getPosition().equals(hero.getPosition())) {
                collectedCoins.add(coin);
            }
        }
        coins.removeAll(collectedCoins);
        while (coins.size() < 5) {
            addNewCoin();
        }
    }
    private void addNewCoin() {
        Random random = new Random();
        Coin newCoin;
        do {
            newCoin = new Coin(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
        } while (isCoinOverlapping(newCoin) || newCoin.getPosition().equals(hero.getPosition()));

        coins.add(newCoin);
    }
    private void verifyMonsterCollisions() {
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(hero.getPosition())) {
                System.out.println("Skill Issue");
                System.exit(0);
            }
        }
    }
    private void moveMonsters() {
        for (Monster monster : monsters) {
            Position newPosition = monster.move();
            if (canMonsterMove(newPosition)) {
                monster.setPosition(newPosition);
                verifyMonsterCollisions();
            }
        }
    }
    private boolean canMonsterMove(Position position) {
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)) {
                return false;
            }
        }
        return position.getX() >= 0 && position.getX() < width && position.getY() >= 0 && position.getY() < height;
    }
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#90EE90"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        hero.draw(graphics);
        for (Wall wall : walls) {
            wall.draw(graphics);
        }
        for (Coin coin : coins) {
            coin.draw(graphics);
        }
        for (Monster monster : monsters) {
            monster.draw(graphics);
        }
    }
    public void processKey(KeyStroke key) {
        System.out.println(key);
        switch (key.getKeyType()) {
            case ArrowUp:
                moveHero(hero.moveUp());
                moveMonsters();
                break;
            case ArrowDown:
                moveHero(hero.moveDown());
                moveMonsters();
                break;
            case ArrowRight:
                moveHero(hero.moveRight());
                moveMonsters();
                break;
            case ArrowLeft:
                moveHero(hero.moveLeft());
                moveMonsters();
                break;
            default:
        }
    }
}