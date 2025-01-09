import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphicalGame {

    private final char[][] terrain = ConfigTerrain.getTerrain();

    public void start() throws CharInconnu {
        // Créer une fenêtre
        JFrame frame = new JFrame("Simulation Robot - Mode Graphique");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);

        // Créer un panneau pour représenter le terrain
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(11, 14)); // Adapter les dimensions au terrain
        frame.add(gridPanel);

        // Ajouter des cases au panneau
        JLabel[][] gridLabels = new JLabel[terrain.length][terrain[0].length];

        // Ajouter des cases au panneau
        for (int i = 0; i < terrain.length; i++) {
            for (int j = 0; j < terrain[i].length; j++) {
                JLabel cell = new JLabel();
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cell.setOpaque(true);
                ImageIcon icon = getIconForCell(terrain[i][j]);
                if (icon != null) {
                    cell.setIcon(icon); // Utilisez setIcon au lieu de setBackground pour afficher l'image
                }
                gridPanel.add(cell);
                gridLabels[i][j] = cell;
            }
        }


        // Mettre à jour la grille pour afficher les modifications
        gridPanel.revalidate();
        gridPanel.repaint();

        // Afficher la fenêtre
        frame.setVisible(true);

        // Appel de la méthode pour initialiser le robot et charger le programme
        Robot robot = initializeRobotAndProgram(gridLabels, terrain, 30);
        ImageIcon robotIcon = getIconForCell(TypeCase.ROBOT.getSymbol());


        // Utiliser un Timer pour exécuter le programme
        Timer timer = new Timer(500, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!robot.hasDrowned() &&
                robot.getMineralCount() == 0 &&
                robot.getPosition() != null &&
                !robot.isProgramComplete()) {

                // Retirer l'icône du robot de l'ancienne position
                Point previousPosition = robot.getPosition();
                gridLabels[previousPosition.x][previousPosition.y].setIcon(getIconForCell(TypeCase.EMPTY.getSymbol()));

                // Exécuter la commande suivante
                robot.executeNext();

                // Mettre à jour la nouvelle position
                Point currentPosition = robot.getPosition();
                gridLabels[currentPosition.x][currentPosition.y].setIcon(robotIcon);

                // Si le robot a trouvé des minerais
                if (robot.getMineralCount() > 0) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "Le robot a trouvé des minerais ! Quantité : " + robot.getMineralCount(),
                            "Fin de simulation",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    gridLabels[currentPosition.x][currentPosition.y].setIcon(getIconForCell(TypeCase.ROBOT.getSymbol()));
                    System.exit(0);
                }

                // Si le robot a coulé, afficher un message
                if (robot.hasDrowned()) {
                    JOptionPane.showMessageDialog(frame, "Le robot a coulé !", "Fin de simulation", JOptionPane.INFORMATION_MESSAGE);
                    gridLabels[currentPosition.x][currentPosition.y].setIcon(getIconForCell(TypeCase.ROBOT_DEAD.getSymbol()));
                    System.exit(0);
                }

                // Si le programme est terminé
                if (robot.isProgramComplete()) {
                    // Afficher un message à l'écran
                    JOptionPane.showMessageDialog(frame, "Programme terminé !", "Fin de simulation", JOptionPane.INFORMATION_MESSAGE);

                    // Fermer la fenêtre après 5 secondes
                    Timer closeTimer = new Timer(5000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            frame.dispose(); // Fermer la fenêtre
                            ((Timer) e.getSource()).stop(); // Stopper le Timer
                        }
                    });
                    closeTimer.setRepeats(false); // Exécuter une seule fois
                    closeTimer.start();
                    System.exit(0);
                }

                // Rafraîchir l'affichage
                gridPanel.revalidate();
                gridPanel.repaint();
            } else {
                ((Timer) e.getSource()).stop(); // Stopper le Timer
            }
        }
    });

    timer.start();

    }


    /**
     * Initialisation du robot et du programme
     * @param gridLabels
     * @param terrain
     * @param programLength
     * @return
     * @throws CharInconnu
     */
    private Robot initializeRobotAndProgram(JLabel[][] gridLabels, char[][] terrain, int programLength) throws CharInconnu {
        // Initialisation du robot
        Point startPosition = new Point(5, 5);
        JLabel robotCell = gridLabels[startPosition.x][startPosition.y];
        ImageIcon robotIcon = getIconForCell(TypeCase.ROBOT.getSymbol());

        if (robotIcon != null) {
            robotCell.setIcon(robotIcon); // Mettre à jour l'icône de la case avec celle du robot
        }

        // Création d'un programme d'actions
        Command[] program = ActionCommand.generateRandomProgram(programLength);

        // Initialisation de l'affichage du terminal et du robot
        AfficheRobotTerminal affichageTerminal = new AfficheRobotTerminal();
        affichageTerminal.initialiser(terrain);

        // Création du robot avec la position initiale et le terrain
        Robot robot = new Robot(startPosition, terrain);

        // Chargement du programme
        robot.setProgram(program);

        return robot;
    }

    /**
     * Récupère l'image en fonction du symbol de la carte
     * @param cell
     * @return
     */
    private ImageIcon getIconForCell(char cell) {
        String basePath = "images_robots/"; // Dossier contenant les PNG
        String fileName;

        // Associer chaque type de case à un fichier PNG
        switch (cell) {
            case '*' -> fileName = "r-rock.png";
            case ' ' -> fileName = "r-sand.png";
            case '#' -> fileName = "r-water.png";
            case '$' -> fileName = "r-coal.png";
            case 'A' -> fileName = "r-robot.png";
            case 'D' -> fileName = "r-robot-bis.png";
            default -> fileName = "r-robot-ter.png";
        }

        // Charger l'image
        try {
            return new ImageIcon(basePath + fileName);
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement de l'icône : " + fileName);
            return null;
        }
    }

}
