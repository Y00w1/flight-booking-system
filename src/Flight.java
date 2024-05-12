import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Flight {
    private String id;
    private Airport destination;
    private Airport departure;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Airplane airplane;
    private Map<String, Seat> seats;
    private double basePrice;
    private List<Booking> bookings;

    public Flight(String id, Airport destination, Airport departure, LocalDateTime departureTime, LocalDateTime arrivalTime, Airplane airplane, double basePrice) {
        this.id = id;
        this.destination = destination;
        this.departure = departure;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.airplane = airplane;
        this.seats = airplane.getSeats();
        this.basePrice = basePrice;
        this.bookings = new ArrayList<>();
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }
    public int countAvailableSeats(){
        return (int) seats.values().stream().filter(Seat::isAvailable).count();
    }
    public String getId() {
        return id;
    }
    public Airport getDestination() {
        return destination;
    }
    public Airport getDeparture() {
        return departure;
    }
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }
    public Airplane getAirplane() {
        return airplane;
    }
    public Map<String, Seat> getSeats() {
        return seats;
    }
    public double getBasePrice() {
        return basePrice;
    }
    public List<Booking> getBookings() {
        return bookings;
    }
}
