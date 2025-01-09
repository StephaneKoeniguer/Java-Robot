import java.awt.Point;

public class TerminalGame {

    /**
     * Utilisation du terminal pour l'affichage
     */
    public void start() throws CharInconnu {

        // Initialisation du terrain
        char[][] terrain = ConfigTerrain.getTerrain();
        AfficheRobotTerminal affichageTerminal = new AfficheRobotTerminal();
        affichageTerminal.initialiser(terrain);

        // Initialisation du robot et du programme
        Robot robot = initializeRobotAndProgram(affichageTerminal, terrain, 20);

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
     * Initialise le robot et charge le program
     * @param affichageTerminal
     * @param terrain
     * @param programLength
     * @return
     * @throws CharInconnu
     */
    private Robot initializeRobotAndProgram(AfficheRobotTerminal affichageTerminal, char[][] terrain, int programLength) throws CharInconnu {

        // Initialisation du robot
        Point startPosition = new Point(9, 4);
        Robot robot = new Robot(startPosition, terrain);
        affichageTerminal.changerCase(startPosition.x, startPosition.y, TypeCase.ROBOT.getSymbol());
        affichageTerminal.afficher();

        // Création d'un programme d'actions
        Command[] program = ActionCommand.generateRandomProgram(programLength);

        // Chargement du programme
        robot.setProgram(program);

        return robot;
    }

}
