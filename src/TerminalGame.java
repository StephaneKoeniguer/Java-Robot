import java.util.Random;
import java.awt.Point;

public class TerminalGame {

    /**
     * Utilisation du terminal pour l'affichage
     */
    public void start() throws CharInconnu {

        // Initialisation du terrain et du robot
        char[][] terrain = {
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
                {'*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*'},
        };

        // Initialiser l'affichage terminal
        AfficheRobotTerminal affichageTerminal = new AfficheRobotTerminal();
        affichageTerminal.initialiser(terrain);

        // Initialisation du robot et affichage carte
        Point startPosition = new Point(9, 4);
        Robot robot = new Robot(startPosition, terrain);
        affichageTerminal.changerCase(startPosition.x, startPosition.y, TypeCase.ROBOT.getSymbol());
        affichageTerminal.afficher();

        // Création d'un programme d'actions
        Command[] program = generateRandomProgram(20);

        // Chargement du programme
        robot.setProgram(program);

        // Exécution du programme
        System.out.println("Début de l'exécution du programme...");

        // Exécuter toutes les commandes
        while (!robot.hasDrowned() &&
                robot.getMineralCount() == 0 &&
                robot.getPosition() != null &&
                !robot.isProgramComplete()) {

            // Avant de déplacer, enlever le robot de sa position précédente
            affichageTerminal.changerCase(robot.getPosition().x, robot.getPosition().y, TypeCase.EMPTY.getSymbol());

            // Exécuter la commande suivante
            robot.executeNext();

            // Mettre à jour la position du robot sur la grille
            affichageTerminal.changerCase(robot.getPosition().x, robot.getPosition().y, TypeCase.ROBOT.getSymbol());
            affichageTerminal.afficher();
        }

        // Si le robot a trouvé du minerais
        if (robot.getMineralCount() > 0 ) {
            // Afficher la position finale du robot et le nombre de minerais collectés
            System.out.println("Le robot a trouvé du minerais.");
            System.out.println("Position finale du robot : " + robot.getPosition());
            System.out.println("Minerais collectés : " + robot.getMineralCount());
        }

        // Si le robot a coulé, afficher un message
        if (robot.hasDrowned()) {
            System.out.println("Le robot a coulé");
            System.out.println("Position finale du robot : " + robot.getPosition());
            affichageTerminal.changerCase(robot.getPosition().x, robot.getPosition().y, TypeCase.EMPTY.getSymbol());
            affichageTerminal.afficher();
        }

        // Si le programme est terminé
        if (robot.isProgramComplete()) {
            System.out.println("Programme terminé");
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
