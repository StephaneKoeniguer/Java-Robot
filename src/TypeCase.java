public enum TypeCase {
    EMPTY(' '),    // Case vide
    ROCK('*'),     // Roche
    MINERAL('$'),  // Minerai
    WATER('#'),    // Eau
    ROBOT('A');    // Robot

    private final char symbol;

    TypeCase(char symbol)
    {
        this.symbol = symbol;
    }

    public char getSymbol()
    {
        return symbol;
    }

    public static TypeCase fromSymbol(char symbol) throws IllegalArgumentException
    {
        for (TypeCase type : TypeCase.values()) {
            if (type.symbol == symbol) {
                return type;
            }
        }
        // Si aucun type ne correspond à la valeur fournie, on lève une exception.
        throw new IllegalArgumentException("Caractère non reconnu : " + symbol);
    }

}