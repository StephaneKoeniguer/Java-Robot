import java.awt.Point;

public class Robot {
    private Point position;      // Position actuelle du robot (x, y)
    private Command[] program;   // Programme du robot (tableau de commandes)
    private int programIndex;    // Index de la prochaine commande à exécuter
    private char[][] terrain;    // Terrain sur lequel le robot se déplace
    private boolean drowned;     // Statut si le robot a coulé
    private int mineralCount;    // Compteur de minerais collectés

    // Constructeur
    public Robot(Point startPosition, char[][] terrain) {
        this.position = startPosition;
        this.program = new Command[0]; // Programme vide par défaut
        this.programIndex = 0;
        this.terrain = terrain;
        this.drowned = false; // Initialisation à false, le robot n'est pas noyé au départ
    }

    /**
     * Déplace le robot dans une direction donnée.
     *
     * @param direction La direction dans laquelle déplacer le robot.
     */
    public void move(Direction direction) {

        if (drowned) {
            System.out.println("Le robot a déjà coulé, programme arrêté.");
            return;
        }

        int newX = position.x;
        int newY = position.y;

        // Calculer la nouvelle position en fonction de la direction
        switch (direction) {
            case UP -> newX -= 1;
            case DOWN -> newX += 1;
            case LEFT -> newY -= 1;
            case RIGHT -> newY += 1;
        }

        // Vérifier les limites du terrain
        if (newX < 0 || newX >= terrain.length || newY < 0 || newY >= terrain[0].length) {
            System.out.println("Déplacement hors de la grille. Mouvement annulé.");
            return;
        }

        // Vérifier si la case cible contient un obstacle
        char targetCell = terrain[newX][newY];
        if (targetCell == TypeCase.ROCK.getSymbol()) {
            System.out.println("Obstacle rencontré (" + targetCell + "). Mouvement annulé.");
            return;
        }
        // Vérifier si la case cible contient de l'eau
        if (targetCell == TypeCase.WATER.getSymbol()) {
            drowned = true;
            return;
        }

        if (targetCell == TypeCase.MINERAL.getSymbol()) {
            mineralCount++; // Incrémenter le compteur de minerais
            position.setLocation(newX, newY);
            terrain[newX][newY] = ' '; // Enlever le minerai du terrain
            return;
        }

        // Déplacement autorisé
        position.setLocation(newX, newY);
        System.out.println("Robot déplacé vers : (" + newX + ", " + newY + ")");

    }

    /**
     * Exécute la prochaine commande du programme du robot.
     */
    public void executeNext() {
        if (programIndex >= program.length || drowned) {
            System.out.println("Le programme est terminé");
            return;
        }

        Command command = program[programIndex];
        programIndex++;

        // Exécuter la commande
        command.execute(this);
    }

    /**
     * Définit un nouveau programme pour le robot.
     *
     * @param prog Le tableau de commandes à exécuter.
     */
    public void setProgram(Command[] prog) {
        this.program = prog;
        this.programIndex = 0; // Réinitialiser l'index du programme
    }

    // Getter pour la position
    public Point getPosition() {
        return position;
    }

    public boolean hasDrowned() {
        return drowned; // Retourne vrai si le robot a coulé
    }

    // Getter pour le total du minerais
    public int getMineralCount() {
        return mineralCount;
    }

    public boolean isProgramComplete() {
        return programIndex >= program.length; // Retourne vrai si le programme est terminé
    }
}