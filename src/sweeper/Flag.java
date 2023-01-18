package sweeper;

 class Flag {
     private Matrix flagMap;
     private  int countOfClosedBoxes;
     void start(){
         flagMap = new Matrix(Box.CLOSED);
         countOfClosedBoxes = Ranges.getSize().x * Ranges.getSize().y;
     }
     Box get(Coordinate coordinate)
     {
         return flagMap.getter(coordinate);
     }

     public void setOpenedToBox(Coordinate coordinate) {
         flagMap.setter(coordinate,Box.OPENED);
         countOfClosedBoxes--;
     }

     public void setOpenFlagToBox(Coordinate coordinate) {
         flagMap.setter(coordinate,Box.FLAGED);
     }

     public void setClosedFlagToBox(Coordinate coordinate) {
         flagMap.setter(coordinate,Box.CLOSED);
     }

      void toggleFlagedToBox(Coordinate coordinate) {
         switch (flagMap.getter(coordinate)){
             case FLAGED : setClosedFlagToBox(coordinate); break;
             case CLOSED: setOpenFlagToBox(coordinate)    ;break;
         }
     }

     public int getCountOfClosedBoxes() {
         return countOfClosedBoxes;
     }

      void setBombedToBox(Coordinate coordinate) {
         flagMap.setter(coordinate,Box.BOMBED);
     }

      void setOpenedToCloseBomb(Coordinate coordinate) {
         if(flagMap.getter(coordinate) == Box.CLOSED){
             flagMap.setter(coordinate,Box.OPENED);
         }
     }

      void setNobombToFlagSafeBox(Coordinate coordinate) {
         if(flagMap.getter(coordinate) == Box.FLAGED){
             flagMap.setter(coordinate,Box.NOBOMB);
         }
     }

     int getCountOffFlagedBoxesAround(Coordinate coordinate){
         int count = 0;
         for (Coordinate around : Ranges.getCoordsAround(coordinate)){
             if(flagMap.getter(around) == Box.FLAGED){
                 count++;
             }
         }
         return count;
     }
 }
