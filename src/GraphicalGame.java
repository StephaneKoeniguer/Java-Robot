import javax.swing.*;
import java.awt.*;

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

        Point startPosition = new Point(5, 5);
        JLabel robotCell = gridLabels[startPosition.x][startPosition.y];
        ImageIcon robotIcon = getIconForCell('A');

        if (robotIcon != null) {
            robotCell.setIcon(robotIcon); // Mettre à jour l'icône de la case avec celle du robot
        }

        // Mettre à jour la grille pour afficher les modifications
        gridPanel.revalidate();
        gridPanel.repaint();

        // Afficher la fenêtre
        frame.setVisible(true);
    }


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

}
