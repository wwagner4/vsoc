package vsoc.tryout;

import sample.Simple;
import vsoc.server.InitialPlacement;
import vsoc.server.Server;
import vsoc.server.ServerUtil;
import vsoc.server.VsocPlayer;
import vsoc.server.gui.FieldCanvas;
import vsoc.server.initial.InitialPlacementLineup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Playes one demo match
 */
public class DemoMatchGui {

    public static void main(String... args) {
        InitialPlacement east = new InitialPlacementLineup(5);
        InitialPlacement west = new InitialPlacementLineup(5);
//        InitialPlacement east = new InitialPlacementDemo();
//        InitialPlacement west = new InitialPlacementDemo();
        Server srv = ServerUtil.current().createServer(west, east);
        for (VsocPlayer p : srv.getPlayersWest()) {
            p.setController(new Simple());
        }
        for (VsocPlayer p : srv.getPlayersEast()) {
            p.setController(new Simple());
        }
        StepwiseGui.open(srv);
    }

}

class StepwiseGui {

    private Server srv;

    private JFrame frame = new JFrame("Demo Match VSOC");

    public StepwiseGui(Server srv) {
        this.srv = srv;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setSize(new Dimension(800, 600));
        FieldCanvas field = new FieldCanvas();
        field.setSim(srv);
        srv.addListener(field);
        frame.getContentPane().add(field, BorderLayout.CENTER);
        frame.addKeyListener(new VsocListener(srv));
    }

    static void open(Server srv) {
        new StepwiseGui(srv).openGui();
    }

    private void openGui() {
        frame.setVisible(true);
    }

}

class VsocListener implements KeyListener {
    private Server srv;

    public VsocListener(Server srv) {
        this.srv = srv;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int keyCode = e.getKeyChar();
        if (keyCode == 32) {
            srv.takeStep();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Nothing to do
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Nothing to do
    }
}
class InitialPlacementDemo implements InitialPlacement {

    @Override
    public int numberOfPlayers() {
        return 4;
    }

    @Override
    public Values placementValuesWest(int number) {
        if (number == 0) return new Values(-10, 20, 20);
        if (number == 1) return new Values(-10, 10, 0);
        if (number == 2) return new Values(-10, 0, -20);
        if (number == 3) return new Values(0, -10, -90);
        else throw new IllegalArgumentException();
    }
}
