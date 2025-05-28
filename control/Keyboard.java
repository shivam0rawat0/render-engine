package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{
    Render panel;
    public Keyboard(Render panel){
        this.panel = panel;
    }

    @Override
            public void keyPressed(KeyEvent e) {
                int move = e.getKeyCode();
                panel.redraw(move);
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
}
