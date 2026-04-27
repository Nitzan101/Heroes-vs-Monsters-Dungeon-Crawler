package Tiles;

public class Bar {

    private int pool;
    private int amount;


    public Bar(int pool) {
        this.pool = pool;
        this.amount = pool;
    }

    public int getPool() { return pool; }
    public int getAmount() { return amount; }

    public void setPool(int pool) { this.pool = pool; }
    public void setAmount(int amount) { this.amount = amount; }
}
