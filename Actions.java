public class Actions {
    JavaSwing swing = new JavaSwing();

    public void call() {

    }

    public void check() {

    }

    public void raise(int raise) {
        swing.playerMoney -= raise;
        swing.potMoney += raise;
        
    }

    public void fold() {

    }

    public void nextTurn() {

    }
}
