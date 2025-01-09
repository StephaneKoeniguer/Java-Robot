import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GraphicalGame {

    private final char[][] terrain = {
            {'*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
            {'*', ' ', ' ', ' ', '$', ' ', ' ', ' ', '*', '*', '*', ' ', ' ', '*'},
            {'*', '*', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '*'},
            {'*', '*', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '*'},
            {'*', ' ', '#', ' ', ' ', ' ', ' ', '*', '*', ' ', ' ', ' ', ' ', '*'},
            {'*', ' ', '$', ' ', ' ', ' ', '*', '*', ' ', ' ', '#', ' ', ' ', '*'},
            {'*', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '*'},
            {'*', ' ', ' ', ' ', '#', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '*'},
            {'*', ' ', ' ', ' ', ' ', ' ', ' ', '$', ' ', ' ', ' ', ' ', ' ', '*'},
            {'*', ' ', '$', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '*', '*', ' ', '*'},
            {'*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*'}
    };

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

        // Initialisation du robot
        Point startPosition = new Point(5, 5);
        JLabel robotCell = gridLabels[startPosition.x][startPosition.y];
        ImageIcon robotIcon = getIconForCell(TypeCase.ROBOT.getSymbol());

        if (robotIcon != null) {
            robotCell.setIcon(robotIcon); // Mettre à jour l'icône de la case avec celle du robot
        }

        // Mettre à jour la grille pour afficher les modifications
        gridPanel.revalidate();
        gridPanel.repaint();

        // Création d'un programme d'actions
        Command[] program = generateRandomProgram(20);

        AfficheRobotTerminal affichageTerminal = new AfficheRobotTerminal();
        affichageTerminal.initialiser(terrain);
        Robot robot = new Robot(startPosition, terrain);
        // Chargement du programme
        robot.setProgram(program);

        // Afficher la fenêtre
        frame.setVisible(true);

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
            default -> fileName = "r-robot-bis.png";
        }

        // Charger l'image
        try {
            return new ImageIcon(basePath + fileName);
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement de l'icône : " + fileName);
            return null;
        }
    }






    /**
     * Fonction pour générer un programme d'actions aléatoire
     */
    private Command[] generateRandomProgram(int length) {
        Random random = new Random();
        Command[] program = new Command[length];

        for (int i = 0; i < length; i++) {
            int actionType = random.nextInt(2); // 0 pour MOVE, 1 pour WAIT
            if (actionType == 0) {
                // Générer une action de déplacement avec une direction aléatoire
                Direction direction = Direction.values()[random.nextInt(Direction.values().length)];
                program[i] = new ActionCommand(direction);
            } else {
                // Générer une action d'attente
                program[i] = new ActionCommand(ActionType.WAIT);
            }
        }

        return program;
    }

}
