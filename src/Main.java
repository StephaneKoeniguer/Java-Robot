import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws CharInconnu {

        // Choisir le mode (Terminal ou Graphique)
        System.out.println("Choisissez un mode :");
        System.out.println("1 - Mode Terminal");
        System.out.println("2 - Mode Graphique");

        // Lire l'entrée de l'utilisateur
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();

        if (choix == 1) {
            // Lancer le mode terminal
            gameTerminal();
        } else if (choix == 2) {
            // Lancer le mode graphique
            gameGraphique();
        } else {
            System.out.println("Choix invalide. Le programme va se fermer.");
        }

    }

    /**
     * Lancer le jeu en mode terminal
     */
    public static void gameTerminal() throws CharInconnu {

        TerminalGame game = new TerminalGame();
        game.start();
    }

    /**
     * Lancer le jeu en mode graphique
     */
    public static void gameGraphique() throws CharInconnu {
        GraphicalGame game = new GraphicalGame();
        game.start();
    }


}