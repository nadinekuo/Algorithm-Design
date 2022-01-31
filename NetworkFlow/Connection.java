import java.util.Objects;

class Connection {

    int x;

    int y;

    public Connection(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Connection that = (Connection) o;
        return x == that.x && y == that.y;  // Compares direction too!
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
