public class Move extends Coordinates 
{
    //Declare variables
    private Type type;

    //Constructors
        public Move(int x, int y, Type type)
        {
            super(x, y);
            this.type = type;
        }
        public Move(Coordinates pos, Type type)
        {
            super(pos);
            this.type = type;
        }

    //Enums
        public enum Type
        {
            NORMAL, ENPASSANT, CASTLE;
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
}
