public class Coordinates {
    //Declare variables
        private int x, y;

    //Constructor
        public Coordinates(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
        public Coordinates(Coordinates pos)
        {
            this.x = pos.getX();
            this.y = pos.getY();
        }

    //Methods
        public boolean equals(Coordinates pos)
        {
            if(this.getX() == pos.getX() && this.getY() == pos.getY())
            {
                return true;
            }
            
            return false;
        }
        
    //Getters/Setters
        public int getX()
        {
            return this.x;
        }
        public void setX(int x)
        {
            this.x = x;
        }

        public int getY()
        {
            return this.y;
        }
        public void setY(int y)
        {
            this.y = y;
        }
}
