package sweeper;

public class Game {
   private Bomb bomb;
   private Flag flag;
    private gameState state;
    public gameState getState() {
        return state;
    }
    public Game (int cols, int rows, int bombs){
        Ranges.setSize(new Coordinate(cols,rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public void startGame (){
       bomb.start();
       flag.start();
       state = gameState.PLAYED;
    }
    public Box getBox (Coordinate coordinate){
        if(flag.get(coordinate) == Box.OPENED) {
            return bomb.get(coordinate);
        }
        else {
            return flag.get(coordinate);
        }
    }
    public void pressLeftButton(Coordinate coordinate){
        if(gameOver()) return;
        openBox(coordinate);
        checkWinner();
    }

    private boolean gameOver() {
        if(state == gameState.PLAYED){
            return false;
        } else {
            startGame();
            return true;
        }
    }

    private void checkWinner(){
        if (state == gameState.PLAYED){
            if(flag.getCountOfClosedBoxes() == bomb.getTotalBombs()){
                state = gameState.WINNER;
            }
        }
    }



    private void openBox(Coordinate coordinate){
        switch (flag.get(coordinate)) {
            case OPENED: setOpenedToClosedBoxesAroundNumber(coordinate); return;
            case FLAGED: return;
            case CLOSED: switch (bomb.get(coordinate)){
                case ZERO: openBoxesAround(coordinate); return;
                case BOMB: openBombs(coordinate); return;
                default:flag.setOpenedToBox(coordinate); return;
            }
        }
    }

   private void setOpenedToClosedBoxesAroundNumber(Coordinate coordinate){
        if(bomb.get(coordinate) != Box.BOMB){
            if(flag.getCountOffFlagedBoxesAround(coordinate) == bomb.get(coordinate).getNumber()){
                for(Coordinate around : Ranges.getCoordsAround(coordinate)){
                    if(flag.get(around) == Box.CLOSED){
                        openBox(around);
                    }
                }
            }
        }

    }

    private void openBombs(Coordinate bobmed) {
        state = gameState.BOMBED;
        flag.setBombedToBox(bobmed);
        for (Coordinate coordinate : Ranges.getAllCoordinate()) {
            if(bomb.get(coordinate) == Box.BOMB){
                flag.setOpenedToCloseBomb(coordinate);
            } else {
                flag.setNobombToFlagSafeBox(coordinate);
            }
        }
    }

    private void openBoxesAround(Coordinate coordinate) {
        flag.setOpenedToBox(coordinate);
        for (Coordinate around : Ranges.getCoordsAround(coordinate)){
            openBox(around);
        }
    }

    public void pressRightButton(Coordinate coordinate){
        if(gameOver()) return;
        flag.toggleFlagedToBox(coordinate);

    }
}
