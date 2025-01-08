public interface AfficheRobot {
    void initialiser(char[][] tab) throws CharInconnu ;
    void changerCase(int x , int y, char c) throws CharInconnu ;
    void afficher() ;
    void terminer() ;

}
