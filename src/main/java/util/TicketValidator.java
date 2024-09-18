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

    public void validateBusTicket(BusTicket busTicket) {
        List<String> errorMessages = new ArrayList<>();

        validateStartDate(busTicket.getStartDate(), errorMessages);
        validateTicketType(busTicket.getTicketType(), errorMessages);
        validateTicketClass(busTicket.getTicketClass(), errorMessages);
        validatePrice(busTicket.getBusTicketPrice(), errorMessages);

        if (!errorMessages.isEmpty()) {
            throw new IllegalArgumentException(String.join(", ", errorMessages));
        }
    }

    private void validateStartDate(String date, List<String> errorMessages) {
        if (date == null) {
            calculateErrors("Invalid start date");
            errorMessages.add("Error: Invalid start date: null");
        } else {
            LocalDate checkingDate = LocalDate.parse(date);
            if (checkingDate.isBefore(LocalDate.now())) {
                calculateErrors("Invalid start date");
                errorMessages.add("Error: Invalid start date: " + date);
            }
        }
    }

    private void validateTicketType(BusTicket.TicketType ticketType, List<String> errorMessages) {
        if (ticketType == null) {
            calculateErrors("Invalid ticket type");
            errorMessages.add("Error: Invalid ticket type: null");
        } else {
            try {
                BusTicket.TicketType.valueOf(ticketType.name());
            } catch (IllegalArgumentException e) {
                calculateErrors("Invalid ticket type");
                errorMessages.add("Error: Invalid ticket type: " + ticketType);
            }
        }
    }

    private void validateTicketClass(BusTicket.TicketClass ticketClass, List<String> errorMessages) {
        if (ticketClass == null) {
            calculateErrors("Invalid ticket class");
            errorMessages.add("Error: Invalid ticket class: null");
        } else {
            try {
                BusTicket.TicketClass.valueOf(ticketClass.name());
            } catch (IllegalArgumentException e) {
                calculateErrors("Invalid ticket class");
                errorMessages.add("Error: Invalid ticket class: " + ticketClass);
            }
        }
    }

    private void validatePrice(int price, List<String> errorMessages) {
        if (!(price > 0 && price % 2 == 0)) {
            calculateErrors("Invalid price of ticket");
            errorMessages.add("Error: Invalid price of ticket: " + price);
        }
    }

    private void calculateErrors(String errorMessage) {
        statisticsErrors.put(errorMessage, statisticsErrors.getOrDefault(errorMessage, 0) + 1);
    }
}
