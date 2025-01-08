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
}