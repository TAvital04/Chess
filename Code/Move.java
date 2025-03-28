public class Move 
{
    //Declare variables
    private Type type;
    private Coordinates pos;

    //Constructors
        public Move(int x, int y, Type type)
        {
            this.type = type;
            this.pos = new Coordinates(x, y);
        }
        public Move(Coordinates pos, Type type)
        {
            this.type = type;
            this.pos = pos;
        }

    //Enums
        public enum Type
        {
            NORMAL, ENPASSANT, CASTLE, CHECK;
        }

    //Getters/Setters
        public Type getType()
        {
            return this.type;
        }
        public void setType(Type type)
        {
            this.type = type;
        }

        public Coordinates getPos()
        {
            return this.pos;
        }
        public void setPos(Coordinates pos)
        {
            this.pos = pos;
        }
        public void setPos(int x, int y)
        {
            this.pos = new Coordinates(x, y);
        }
}
