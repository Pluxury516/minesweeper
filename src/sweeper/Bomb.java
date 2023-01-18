package sweeper;

 class Bomb {
    private Matrix bombMap;
    private int totalBombs;
    Bomb(int totalBombs){
        this.totalBombs = totalBombs;
        fixBombCount();
    }
    void start(){
        bombMap = new Matrix(Box.ZERO);
        for (int i = 0; i < totalBombs; i++) {
            placeBomb();
        }

    }

    Box get (Coordinate coordinate){
        return bombMap.getter(coordinate);
    }

    private void fixBombCount (){
        int maxBombs = Ranges.getSize().x * Ranges.getSize().y / 2;
                if(totalBombs > maxBombs)
                    totalBombs = maxBombs;
    }
    private void placeBomb(){
        while (true) {
            Coordinate coordinate = Ranges.getRandomCoordinate();
            if(Box.BOMB == bombMap.getter(coordinate))
                continue;
            bombMap.setter(coordinate, Box.BOMB);
            incNumberAroundBomb(coordinate);
            break;
        }
    }

    private void incNumberAroundBomb(Coordinate coordinate){
        for (Coordinate around : Ranges.getCoordsAround(coordinate)){
            if(Box.BOMB != bombMap.getter(around))
            bombMap.setter(around,bombMap.getter(around).getNextNumberBox());
        }
    }

     public int getTotalBombs() {
        return totalBombs;
     }
 }
