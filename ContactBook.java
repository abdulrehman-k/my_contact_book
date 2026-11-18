import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ContactBook {
    private static final String FILE = "contacts.csv";
    private final List<Contact> contacts = new ArrayList<>();

    public ContactBook() {
        load();
    }

    public void add(Contact c) {
        contacts.add(c);
        sort();
        save();
    }

    public List<Contact> all() {
        return contacts;
    }

    public List<Contact> search(String keyword) {
        String k = keyword.toLowerCase();
        List<Contact> result = new ArrayList<>();
        for (Contact c : contacts) {
            if (c.getName().toLowerCase().contains(k)
                    || c.getPhone().contains(k)
                    || c.getEmail().toLowerCase().contains(k)) {
                result.add(c);
            }
        }
        return result;
    }

    public boolean delete(Contact c) {
        boolean removed = contacts.remove(c);
        if (removed) save();
        return removed;
    }

    public void save() {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE))) {
            for (Contact c : contacts) {
                out.println(c.toCsv());
            }
        } catch (IOException e) {
            System.out.println("Could not save: " + e.getMessage());
        }
    }

    private void load() {
        Path path = Paths.get(FILE);
        if (!Files.exists(path)) return;
        try {
            for (String line : Files.readAllLines(path)) {
                if (!line.isBlank()) contacts.add(Contact.fromCsv(line));
            }
            sort();
        } catch (IOException e) {
            System.out.println("Could not load: " + e.getMessage());
        }
    }

    private void sort() {
        contacts.sort(Comparator.comparing(c -> c.getName().toLowerCase()));
    }
}