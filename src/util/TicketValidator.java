package util;

import model.BusTicket;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketValidator {
    Map<String, Integer> statisticsErrors = new HashMap<>();

    public String getMostFrequentError() {
        if (statisticsErrors.isEmpty()) {
            return "No errors recorded";
        }
        String mostFrequentError = null;
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : statisticsErrors.entrySet()) {
            String error = entry.getKey();
            int count = entry.getValue();
            if (count > maxCount) {
                mostFrequentError = error;
                maxCount = count;
            }
        }

        return mostFrequentError;
    }

    public boolean validateBusTicket(BusTicket busTicket) {
        List<String> errorMessages = new ArrayList<>();

        if (busTicket.getStartDate() == null || !isValidDate(busTicket.getStartDate())) {
            calculateErrors("Invalid start date");
            errorMessages.add("Error: Invalid start date: " + busTicket.getStartDate());
        }

        if (busTicket.getTicketType() == null) {
            calculateErrors("Invalid ticket type");
            errorMessages.add("Error: Invalid ticket type: null");
        } else {
            try {
                BusTicket.TicketType.valueOf(busTicket.getTicketType().name());
            } catch (IllegalArgumentException e) {
                calculateErrors("Invalid ticket type");
                errorMessages.add("Error: Invalid ticket type: " + busTicket.getTicketType());
            }
        }

        if (busTicket.getTicketClass() == null) {
            calculateErrors("Invalid ticket class");
            errorMessages.add("Error: Invalid ticket class: null");
        } else {
            try {
                BusTicket.TicketClass.valueOf(busTicket.getTicketClass().name());
            } catch (IllegalArgumentException e) {
                calculateErrors("Invalid ticket class");
                errorMessages.add("Error: Invalid ticket class: " + busTicket.getTicketClass());
            }
        }
        if (!isValidPrice(busTicket.getBusTicketPrice())) {
            calculateErrors("Invalid price of ticket");
            errorMessages.add("Error: Invalid price of ticket: " + busTicket.getBusTicketPrice());
        }

        if (!errorMessages.isEmpty()) {
            throw new IllegalArgumentException(String.join(", ", errorMessages));
        }

        return true;
    }

    private boolean isValidDate(String date) {
        LocalDate checkingDate = LocalDate.parse(date);
        return !(checkingDate.isBefore(LocalDate.now()));
    }

    private void calculateErrors(String errorMessage) {
        statisticsErrors.put(errorMessage, statisticsErrors.getOrDefault(errorMessage, 0) + 1);
    }

    private boolean isValidPrice(int busTicketPrice) {
        return busTicketPrice > 0 && busTicketPrice % 2 == 0;
    }
}
