class UnexecutableCommandException extends Exception{
    public UnexecutableCommandException(String message) {
        super(message);
    }
}
class DeadException extends Exception{
    public DeadException(String message){super(message);}
}
 class GameOverException extends Exception{
    public GameOverException(String message) {
        super(message);
    }
}
class GameWinException extends Exception{
    public GameWinException(String message) {
        super(message);
    }
}