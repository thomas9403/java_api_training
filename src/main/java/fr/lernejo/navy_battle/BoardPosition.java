package fr.lernejo.navy_battle;

public record BoardPosition(int x, int y) {

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass())
            return false;

        final BoardPosition other = (BoardPosition) o;
        return other.x == this.x && other.y == this.y;
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    //function getX()
    public int getX(){
        return this.x;
    }

    //function getY()
    public int getY(){
        return this.y;
    }
}
