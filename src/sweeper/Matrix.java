package sweeper;

 class Matrix {
    private Box [] [] matrix;
    Matrix(Box defaultBox){
        matrix = new Box[Ranges.getSize().x][Ranges.getSize().y];
        for (Coordinate coordinate : Ranges.getAllCoordinate()) {
            matrix[coordinate.x] [coordinate.y] = defaultBox;
        }
    }
    Box getter (Coordinate coordinate){
        if(Ranges.inRange(coordinate)) {
            return matrix[coordinate.x][coordinate.y];
        } else {
            return  null;
        }
    }
   void  setter(Coordinate coordinate, Box box){
       if(Ranges.inRange(coordinate)) {
           matrix[coordinate.x][coordinate.y] = box;
       }
    }
}
