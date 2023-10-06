import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
public class Wall extends Element {
    public Wall(int x, int y) {
        super(x, y);
    }
    public void draw(TextGraphics graphics) {
        // Draw the Wall
        graphics.setForegroundColor(TextColor.Factory.fromString("#000000"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "#");
    }
}