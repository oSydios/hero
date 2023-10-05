import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import java.util.ArrayList;
import java.util.List;

public class Arena {
    private int width;
    private int height;
    private Hero hero;
    private List<Wall> walls;
    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.hero = new Hero(width / 2, height / 2);
        this.walls = createWalls();
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
    private boolean canHeroMove(Position position) {
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)) {
                return false;
            }
        }
        return position.getX() >= 0 && position.getX() < width && position.getY() >= 0 && position.getY() < height;
    }
    public void moveHero(Position position) {
        if (canHeroMove(position))
            hero.setPosition(position);
    }
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#176312"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        hero.draw(graphics);
        for (Wall wall : walls) {
            wall.draw(graphics);
        }
    }
    public void processKey(KeyStroke key) {
        System.out.println(key);
        switch (key.getKeyType()) {
            case ArrowUp:
                moveHero(hero.moveUp());
                break;
            case ArrowDown:
                moveHero(hero.moveDown());
                break;
            case ArrowRight:
                moveHero(hero.moveRight());
                break;
            case ArrowLeft:
                moveHero(hero.moveLeft());
                break;
            default:
        }
    }
}