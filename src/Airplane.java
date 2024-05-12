import java.util.HashMap;
import java.util.Map;

public class Airplane {
    private String registrationCode;
    private String name;
    private Map<String, Seat> seats;

    public Airplane(String registrationCode, String name, int rowCount, int columnCount) {
        this.registrationCode = registrationCode;
        this.name = name;
        this.seats = generateSeats(rowCount, columnCount);
    }

    // Generate seats for the airplane based on rows and columns
    private Map<String, Seat> generateSeats(int rows, int columns){
        Map<String, Seat> seats = new HashMap<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Seat seat = new Seat(i, j);
                seats.put(seat.getCode(), seat);
            }
        }
        return seats;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public String getName() {
        return name;
    }

    public Map<String, Seat> getSeats() {
        return seats;
    }
}
