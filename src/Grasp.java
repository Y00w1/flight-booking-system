import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Grasp {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private static Map<String, Airport> airports = new HashMap<>();
    private static Map<String, Airplane> airplanes = new HashMap<>();
    private static Map<String, Flight> flights = new HashMap<>();

    public static void main(String[] args) throws IOException {
        initializeDummyData();

        JOptionPane.showMessageDialog(null, "Welcome to the Flight Booking System!");

        JPanel panel = getPanelFlightsButtons();

        JTextArea textAreaAvailableFlights = new JTextArea(generateAvailableFlightsMessage());
        textAreaAvailableFlights.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textAreaAvailableFlights);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.NORTH);
        mainPanel.add(panel, BorderLayout.CENTER);

        while (true) {
            JOptionPane.showMessageDialog(null, mainPanel, "Select a Flight", JOptionPane.PLAIN_MESSAGE);
            panel.removeAll();
            panel.revalidate();

            panel.add(getPanelFlightsButtons());
            textAreaAvailableFlights.setText(generateAvailableFlightsMessage());

            int option = JOptionPane.showConfirmDialog(null, "Do you want to make another booking?", "Continue", JOptionPane.YES_NO_OPTION);
            if (option != JOptionPane.YES_OPTION) {
                break;
            }
        }
    }

    private static JPanel getPanelFlightsButtons() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        for (Flight flight : flights.values()) {
            JButton button = new JButton(flight.getId());
            button.addActionListener(e -> {
                Flight selectedFlight = flights.get(((JButton) e.getSource()).getText());
                JOptionPane.showMessageDialog(null, "You selected Flight " + selectedFlight.getId());
                selectedFlight.addBooking(generateFlightBooking(selectedFlight));
            });
            panel.add(button);
        }
        return panel;
    }

    private static Booking generateFlightBooking(Flight selectedFlight){
        List<Seat> selectedSeats = selectSeats(selectedFlight);
        Booking booking = new Booking((selectedFlight.getBookings().size()+1), selectedFlight, selectedSeats);
        displayBookingConfirmation(booking);
        return booking;
    }

    private static List<Seat> selectSeats(Flight selectedFlight){
        List<Seat> selectedSeats = new ArrayList<>();
        int availableSeatsCount = selectedFlight.countAvailableSeats();
        if (availableSeatsCount == 0) {
            JOptionPane.showMessageDialog(null, "No available seats in the selected flight.", "Error", JOptionPane.ERROR_MESSAGE);
            return selectedSeats;
        }

        String seatsInput = JOptionPane.showInputDialog(null, "Enter the number of seats you want to book (available seats: " + availableSeatsCount + "):", "Seat Selection", JOptionPane.QUESTION_MESSAGE);
        int numSeats = Integer.parseInt(seatsInput);

        if (numSeats > availableSeatsCount) {
            JOptionPane.showMessageDialog(null, "Number of seats exceeds available seats in the flight.", "Error", JOptionPane.ERROR_MESSAGE);
            return selectedSeats;
        }
        selectedFlight.getSeats().values().stream()
                .filter(Seat::isAvailable)
                .limit(numSeats)
                .forEach(seat -> {
                    seat.setAvailable(false);
                    selectedSeats.add(seat);
                });
        return selectedSeats;
    }

    private static void displayBookingConfirmation(Booking booking){
        JOptionPane.showMessageDialog(null, "Your booking has been successfully made!\n" + booking.toString());
    }

    private static String generateAvailableFlightsMessage(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        StringBuilder message = new StringBuilder("Available Flights:\n\n");
        for (Flight flight : flights.values()) {
            String departureTimeFormatted = flight.getDepartureTime().format(formatter);
            String arrivalTimeFormatted = flight.getArrivalTime().format(formatter);
            message.append(String.format("Flight ID: %s\n", flight.getId()));
            message.append(String.format("Departure Airport: %s %s %s\n", flight.getDeparture().getCodeIATA(), flight.getDeparture().getCity(), flight.getDeparture().getName()));
            message.append(String.format("Destination Airport: %s %s %s\n", flight.getDestination().getCodeIATA(), flight.getDestination().getCity(), flight.getDestination().getName()));
            message.append(String.format("Departure Time: %s\n", departureTimeFormatted));
            message.append(String.format("Arrival Time: %s\n", arrivalTimeFormatted));
            message.append(String.format("Base price: $%s\n", flight.getBasePrice()));
            message.append(String.format("Available Seats: %d\n\n", flight.countAvailableSeats()));
        }
        return message.toString();
    }

    private static void initializeDummyData(){
        initializeAirports();
        initializeAirplanes();
        initializeFlights();
    }
    private static void initializeAirports(){
        String[][] airportData = {
                {"AXM", "El Edén", "Armenia", "Colombia"},
                {"JFK", "John F. Kennedy International Airport", "New York", "USA"},
                {"LHR", "Heathrow Airport", "London", "UK"}
        };
        for (String[] data : airportData) {
            airports.put(data[0], new Airport(data[0], data[1], data[2], data[3]));
        }
    }
    private static void initializeAirplanes(){
        String[][] airplaneData = {
                {"G-XLEE", "Airbus A220", "10", "6"},
                {"N12345", "Boeing 737", "12", "8"},
                {"A1B2C3", "Embraer E190", "8", "4"}
        };
        for (String[] data : airplaneData) {
            airplanes.put(data[0], new Airplane(data[0], data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3])));
        }
    }
    private static void initializeFlights(){
        String[][] flightData = {
                {"F001", "AXM", "JFK", "2024-05-12T08:00:00", "2024-05-12T16:00:00", "G-XLEE", "500.00"},
                {"F002", "LHR", "JFK", "2024-05-13T09:00:00", "2024-05-13T15:00:00", "N12345", "600.00"}
        };
        for (String[] data : flightData) {
            Airport departureAirport = airports.get(data[1]);
            Airport destinationAirport = airports.get(data[2]);
            Airplane airplane = airplanes.get(data[5]);
            LocalDateTime departureTime = LocalDateTime.parse(data[3]);
            LocalDateTime arrivalTime = LocalDateTime.parse(data[4]);
            double basePrice = Double.parseDouble(data[6]);
            flights.put(data[0], new Flight(data[0], destinationAirport, departureAirport, departureTime, arrivalTime, airplane, basePrice));
        }
    }
}
