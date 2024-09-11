import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.BusTicket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class FileWork {
    public List<BusTicket>readFromFile(String fileName){
        File file = new File(fileName);
        Gson gson = new Gson();
        try {
            FileReader reader = new FileReader(file);
            Type busTicketListType = new TypeToken<List<BusTicket>>(){}.getType();
            List<BusTicket> busTickets = gson.fromJson(reader, busTicketListType);

            for (BusTicket busTicket : busTickets) {
                System.out.println(busTicket);
            }

            return busTickets;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
