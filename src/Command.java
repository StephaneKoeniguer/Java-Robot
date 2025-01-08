public interface Command {
    /**
     * Exécute une commande pour un robot donné.
     *
     * @param robot Le robot sur lequel la commande doit être exécutée.
     */
    void execute(Robot robot);
}
