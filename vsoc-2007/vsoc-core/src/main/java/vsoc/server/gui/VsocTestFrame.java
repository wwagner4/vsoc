package vsoc.server.gui;

import atan.model.Controller;
import atan.model.Player;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import vsoc.behaviour.Behaviour;
import vsoc.behaviour.BehaviourController;
import vsoc.behaviour.Sensors;
import vsoc.server.Server;
import vsoc.server.ServerUtil;

public class VsocTestFrame extends javax.swing.JFrame {

    private ExecutorService es = java.util.concurrent.Executors.newFixedThreadPool(10);

    private Random ran = new Random();

    Optional<Server> srv = Optional.empty();

    /**
     * Creates new form VsocTestFrame
     */
    public VsocTestFrame() {
        initComponents();
        srv = Optional.of(ServerUtil.current().createServer(2, 2));
        srv.ifPresent(s -> {
            s.getPlayers().stream().forEach((p) -> {
                p.setController(createController());
            });
            this.fieldPanel.setSim(s);
        });
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

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
        takeStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                takeStepActionPerformed(evt);
            }
        });
        takeSteps.setText("take steps");
        takeSteps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                takeStepsActionPerformed(evt);
            }
        });
        cntrPanel.add(takeStep);
        cntrPanel.add(takeSteps);

        contPanel.add(fieldPanel, java.awt.BorderLayout.CENTER);
        contPanel.add(cntrPanel, java.awt.BorderLayout.SOUTH);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(contPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(contPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
        );

        pack();
    }

    private void takeStepActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("takeStepActionPerformed");
        srv.ifPresent(s -> {
            es.execute(() -> {
                s.takeStep();
                System.out.println("take step");
            });
        });
    }

    private void takeStepsActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("takeStepsActionPerformed");
        srv.ifPresent(s -> {
            es.execute(() -> {
                takeSteps(s);
                System.out.println("take step");
            });
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VsocTestFrame().setVisible(true);
            }
        });
    }

    private javax.swing.JPanel cntrPanel;
    private javax.swing.JPanel contPanel;
    private FieldPanel fieldPanel;
    private javax.swing.JButton takeStep;
    private javax.swing.JButton takeSteps;

    private int ranw() {
        return ran(-60, 60);
    }

    private int ranh() {
        return ran(-50, 50);
    }

    private Controller createController() {
        Behaviour behav = new Behaviour() {

            @Override
            public void apply(Sensors sens, Player player) {
                player.move(ranw(), ranh());
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
        for (int i = 0; i < 10; i++) {
            s.takeStep();
            pause(100);
            System.out.println("take step " + i);
        }
    }

    private synchronized void pause(int ms) {
        try {
            wait(ms);
        } catch (InterruptedException ex) {
        }
    }

}
