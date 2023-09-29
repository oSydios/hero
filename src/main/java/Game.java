import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.input.KeyStroke;


import javax.swing.*;
import java.io.IOException;

public class Game {
    private Screen screen;
    private int x = 10;
    private int y = 10;
    public Game() throws IOException {
        TerminalSize terminalSize = new TerminalSize(40, 20);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory()
                .setInitialTerminalSize(terminalSize);
        Terminal terminal = terminalFactory.createTerminal();
        this.screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null); // we don't need a cursor
        screen.startScreen(); // screens must be started
        screen.doResizeIfNecessary(); // resize screen if necessary
        screen.clear();
        screen.setCharacter(x, y, TextCharacter.fromCharacter('X')[0]);
    }
    private void draw() throws IOException {
        screen.refresh();
    }
    public void run() {
        try {
            while (true) {
                draw();
                KeyStroke keyStroke = screen.readInput();
                if (keyStroke != null) {
                    processKey(keyStroke);
                    if (((com.googlecode.lanterna.input.KeyStroke) keyStroke).getKeyType() == KeyType.Escape) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                screen.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void processKey(KeyStroke key) {
        System.out.println(key);
        switch (key.getKeyType()) {
            case ArrowUp:
                y--;
                break;
            case ArrowDown:
                y++;
                break;
            case ArrowRight:
                x++;
                break;
            case ArrowLeft:
                x--;
                break;
            default:
        }
        try {
            screen.clear();
            screen.setCharacter(x, y, TextCharacter.fromCharacter('X')[0]);
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Game game = new Game();
            game.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
