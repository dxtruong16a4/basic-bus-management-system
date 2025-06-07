package utility.Suggestion;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class SuggestionFileUtil {

    public static List<String> readSuggestions(String filePath, String key) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            System.out.println("File does not exist: " + filePath);
            return Collections.emptyList();
        }
        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            String[] parts = line.split("=", 2);
            if (parts.length == 2 && parts[0].trim().equalsIgnoreCase(key)) {
                List<String> result = Arrays.stream(parts[1].split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());
                return result;
            }
        }
        return Collections.emptyList();
    }

    public static void writeSuggestions(String filePath, String key, List<String> suggestions) throws IOException {
        List<String> lines = new ArrayList<>();
        boolean found = false;
        Path path = Paths.get(filePath);

        List<String> recentSuggestions = suggestions.size() > 3
                ? suggestions.subList(suggestions.size() - 3, suggestions.size())
                : new ArrayList<>(suggestions);

        if (Files.exists(path)) {
            lines = Files.readAllLines(path);
            for (int i = 0; i < lines.size(); i++) {
                String[] parts = lines.get(i).split("=", 2);
                if (parts.length == 2 && parts[0].trim().equalsIgnoreCase(key)) {
                    lines.set(i, key + "=" + String.join(",", recentSuggestions));
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            lines.add(key + "=" + String.join(",", recentSuggestions));
        }
        Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}