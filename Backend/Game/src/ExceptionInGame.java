class UnexecutableCommandException extends Exception{
    public UnexecutableCommandException(String message) {
        super(message);
    }
}
class DeadException extends Exception{
    public DeadException(String message){super(message);}
}
