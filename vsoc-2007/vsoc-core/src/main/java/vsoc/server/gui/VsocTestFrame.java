package vsoc.server.gui;

import java.awt.EventQueue;
import java.util.*;
import java.util.concurrent.ExecutorService;

import atan.model.*;
import vsoc.behaviour.*;
import vsoc.server.*;
import vsoc.server.initial.InitialPlacementLineup;
import vsoc.server.initial.InitialPlacementNone;

public class VsocTestFrame extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    private ExecutorService es = java.util.concurrent.Executors.newFixedThreadPool(10);

    private Random ran = new Random();

    private Server srv;

    /**
     * Creates new form VsocTestFrame
     */
    public VsocTestFrame() {
        initComponents();
        InitialPlacementLineup east = new InitialPlacementLineup(1);
        InitialPlacementNone west = new InitialPlacementNone();
        srv = ServerUtil.current().createServer(west, east);
        srv.getPlayers().forEach((p) -> p.setController(createController()));
        this.fieldPanel.setSim(srv);
    }

    private void initComponents() {

        javax.swing.JPanel cntrPanel;
        javax.swing.JPanel contPanel;
        javax.swing.JButton takeStep;
        javax.swing.JButton takeSteps;

        contPanel = new javax.swing.JPanel();
        cntrPanel = new javax.swing.JPanel();
        takeStep = new javax.swing.JButton();
        takeSteps = new javax.swing.JButton();
        fieldPanel = new FieldPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("vsoc test");
        setName("vsocTestFrame"); // NOI18N

        contPanel.setLayout(new java.awt.BorderLayout());

        takeStep.setText("take step");
        takeStep.addActionListener(this::takeStepActionPerformed);
        takeSteps.setText("take steps");
        takeSteps.addActionListener(this::takeStepsActionPerformed);
        cntrPanel.add(takeStep);
        cntrPanel.add(takeSteps);

        contPanel.add(fieldPanel, java.awt.BorderLayout.CENTER);
        contPanel.add(cntrPanel, java.awt.BorderLayout.SOUTH);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(contPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(contPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE));

        pack();
    }

    private void takeStepActionPerformed(java.awt.event.ActionEvent evt) {
        es.execute(() -> srv.takeStep());
    }

    private void takeStepsActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("takeStepsActionPerformed");
        es.execute(() -> takeSteps(srv));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Create and display the form */
        EventQueue.invokeLater(() -> new VsocTestFrame().setVisible(true));
    }

    private FieldPanel fieldPanel;

    private int ranw() {
        return ran(-60, 60);
    }

    private int ranh() {
        return ran(-50, 50);
    }

    private double ranTurn() {
        return ran(0, 360);
    }


    private Controller createController() {
        Behaviour behav = new Behaviour() {
            private static final long serialVersionUID = 1L;

            @Override
            public void apply(Sensors sens, Player player) {
                player.move(ranw(), ranh());
                player.turn(ranTurn());
            }

            @Override
            public boolean shouldBeApplied(Sensors sens) {
                return true;
            }

            @Override
            public Optional<Behaviour> getChild() {
                return Optional.empty();
            }
        };
        return new BehaviourController(behav);
    }

    private int ran(int from, int to) {
        int w = to - from;
        int off = w / 2;
        return ran.nextInt(w) - off;
    }

    private void takeSteps(final Server s) {
        for (int i = 0; i < 50; i++) {
            s.takeStep();
            pause(10);
        }
    }

    private synchronized void pause(int ms) {
        try {
            wait(ms);
        } catch (InterruptedException ex) {
            // Nothing to do
        }
    }

}
