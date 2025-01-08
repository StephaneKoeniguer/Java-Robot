import java.util.Arrays;

public class AfficheRobotTerminal implements AfficheRobot {
    private char[][] grid;

    @Override
    public void initialiser(char[][] tab) throws CharInconnu {
        // Vérification des caractères valides
        for (char[] row : tab) {
            for (char c : row) {
                if (!isCharValid(c)) {
                    throw new CharInconnu();
                }
            }
        }
        // Initialisation de la grille
        this.grid = new char[tab.length][tab[0].length];
        for (int i = 0; i < tab.length; i++) {
            this.grid[i] = Arrays.copyOf(tab[i], tab[i].length);
        }
    }

    @Override
    public void changerCase(int x, int y, char c) throws CharInconnu {
        if (!isCharValid(c)) {
            throw new CharInconnu();
        }
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length) {
            throw new IndexOutOfBoundsException("Coordonnées en dehors de la grille.");
        }
        grid[x][y] = c;
    }

    @Override
    public void afficher() {
        for (char[] row : grid) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    @Override
    public void terminer() {
        System.out.println("Affichage terminé.");
    }

    private boolean isCharValid(char c) {
        try {
            TypeCase.fromSymbol(c);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}