package entity;

public class Flight {
    private int id;
    private String number;
    private String from;
    private String to;
    private String data;
    private double price;

    public Flight(int id, String number, String from, String to, String data,
                  double price) {
        super();
        this.id = id;
        this.number = number;
        this.from = from;
        this.to = to;
        this.data = data;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
