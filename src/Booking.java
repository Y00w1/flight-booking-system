import java.util.List;

public class Booking {
    private int id;
    private Flight flight;
    private List<Seat> seats;
    private double netPrice;

    public Booking(int id, Flight flight, List<Seat> seats) {
        this.id = id;
        this.flight = flight;
        this.seats = seats;
        this.netPrice = calculateNetPrice(flight, seats);
    }
    private double calculateNetPrice(Flight flight, List<Seat> seats){
        return flight.getBasePrice() * seats.size();
    }
    public int getId() {
        return id;
    }
    public Flight getFlight() {
        return flight;
    }
    public List<Seat> getSeats() {
        return seats;
    }
    public double getNetPrice() {
        return netPrice;
    }
    @Override
    public String toString() {
        return "Booking: " + "\n" +
                "id: " + id + "\n" +
                "flight: " + flight.getId() + "\n"  +
                "seats: " + seats.size() + "\n" +
                "netPrice: " + netPrice + "\n";
    }
}
