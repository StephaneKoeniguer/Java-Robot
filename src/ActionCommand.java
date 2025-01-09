import java.util.Random;

public class ActionCommand implements Command {
    private final ActionType actionType;
    private final Direction direction; // Optionnel, utilisé uniquement pour les déplacements.

    // Constructeur pour une action de déplacement
    public ActionCommand(Direction direction) {
        this.actionType = ActionType.MOVE;
        this.direction = direction;
    }

    // Constructeur pour une action d'attente
    public ActionCommand(ActionType actionType) {
        if (actionType != ActionType.WAIT) {
            throw new IllegalArgumentException("Ce constructeur est réservé aux actions WAIT.");
        }
        this.actionType = actionType;
        this.direction = null; // Pas de direction pour WAIT
    }

    @Override
    public void execute(Robot robot) {
        switch (actionType) {
            case MOVE -> {
                if (direction != null) {
                    robot.move(direction);
                    System.out.println("Déplacement du robot vers : " + direction);
                }
            }
            case WAIT -> System.out.println("Le robot attend.");
        }
    }

    /**
     * Fonction pour générer un programme d'actions aléatoire
     */
    public static Command[] generateRandomProgram(int length) {
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