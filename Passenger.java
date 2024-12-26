package airReservation;

import java.util.ArrayList;
import java.util.Scanner;

public class Passenger {
    static ArrayList<Flight> list = new ArrayList<>();
    static ArrayList<Reservation> reserv = new ArrayList<>();
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        list.add(new Flight(18, "mumbai", 150));
        list.add(new Flight(45, "benguluru", 100));
        list.add(new Flight(96, "USA", 200));

        while (true) {
            System.out.println("\n------AirLine Reservation System-----");
            System.out.println("1.Display Available Seats");
            System.out.println("2.Book a Flight");
            System.out.println("3.View Reservation");
            System.out.println("4.Cancel Booking");
            System.out.println("5.Exit");
            System.out.println("Choose an option");

            int choice = getValidIntegerInput();
            switch (choice) {
                case 1: {
                    displayAvailableFlights();
                    break;
                }
                case 2: {
                    bookFlight();
                    break;
                }
                case 3: {
                    viewReservation();
                    break;
                }
                case 4: {
                    cancelBooking();
                    break;
                }
                case 5: {
                    System.out.println("Exiting the System");
                    scan.close();
                    return;
                }
                default: {
                    System.out.println("Invalid option, please try again.");
                }
            }
        }
    }

    private static void cancelBooking() {
        System.out.println("Enter the name of the passenger to cancel the flight:");
        String passengerName = scan.next();
        Reservation reservationToCancel = null;

        for (Reservation r : reserv) {
            if (r.getName().equalsIgnoreCase(passengerName)) {
                reservationToCancel = r;
                break;
            }
        }

        if (reservationToCancel != null) {
            Flight flight = reservationToCancel.getFlight();
            flight.setAvailableSeats(flight.getAvailableSeats() + 1);
            reserv.remove(reservationToCancel);
            System.out.println("Reservation is cancelled for the passenger.");
        } else {
            System.out.println("No reservation found for the name " + passengerName);
        }
    }

    private static void viewReservation() {
        if (reserv.isEmpty()) {
            System.out.println("No reservation made yet!");
        } else {
            System.out.println("Reservation Details:");
            for (Reservation r : reserv) {
                System.out.println("Passenger Name: " + r.getName());
                System.out.println("Flight Number: " + r.getFlight().getFlightNumber());
                System.out.println("Destination: " + r.getFlight().getDesignation());
                System.out.println("---------------------------");
            }
        }
    }

    private static void bookFlight() {
        displayAvailableFlights();
        System.out.println("Enter the flight number to book a flight:");
        int flightNumber = getValidIntegerInput();
        Flight selectedFlight = null;

        for (Flight flight : list) {
            if (flight.getFlightNumber() == flightNumber) {
                selectedFlight = flight;
                break;
            }
        }

        if (selectedFlight == null) {
            System.out.println("Invalid flight number, try again.");
            return;
        }

        if (selectedFlight.getAvailableSeats() > 0) {
            System.out.println("Enter your name:");
            String passengerName = scan.next();
            Reservation reservation = new Reservation(passengerName, selectedFlight);
            reserv.add(reservation);
            selectedFlight.decreaseAvailableSeats();
            System.out.println("Booking successful!");
        } else {
            System.out.println("Sorry, no available seats in the selected flight.");
        }
    }

    private static void displayAvailableFlights() {
        System.out.println("\n---Available Flights---");
        for (Flight f : list) {
            System.out.println("Flight Number: " + f.getFlightNumber() + ", Destination: " + f.getDesignation() + ", Available Seats: " + f.getAvailableSeats());
        }
    }

    private static int getValidIntegerInput() {
        while (!scan.hasNextInt()) {
            System.out.println("Enter a valid number:");
            scan.next(); // Consume the invalid input
        }
        return scan.nextInt(); // Return the valid integer input
    }
}
