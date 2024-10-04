package util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ResourceLoader {
    @Autowired
    private ApplicationContext context;

    public List<Map<String, Object>> loadTicketsToArrayList(String path) {
        Resource aClasspathTemplate = context.getResource("classpath:" + path);

        if (aClasspathTemplate.exists()) {
            try (InputStream inputStream = aClasspathTemplate.getInputStream();
                 InputStreamReader reader = new InputStreamReader(inputStream)) {

                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Map<String, Object>>>() {
                }.getType();
                return gson.fromJson(reader, listType);

            } catch (IOException e) {
                throw new RuntimeException("Error reading the resource", e);
            }
        } else {
            throw new RuntimeException("File not found: " + path);
        }
    }
}
