import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final ContactBook book = new ContactBook();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Contact Book ===");
            System.out.println("1. Add contact");
            System.out.println("2. View all");
            System.out.println("3. Search");
            System.out.println("4. Edit contact");
            System.out.println("5. Delete contact");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            switch (sc.nextLine().trim()) {
                case "1" -> addContact();
                case "2" -> show(book.all());
                case "3" -> searchContacts();
                case "4" -> editContact();
                case "5" -> deleteContact();
                case "0" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void addContact() {
        System.out.print("Name: ");
        String name = sc.nextLine();
        if (name.isBlank()) {
            System.out.println("Name is required.");
            return;
        }
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        book.add(new Contact(name, phone, email));
        System.out.println("Contact saved.");
    }

    private static void show(List<Contact> list) {
        if (list.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }
        System.out.printf("%-4s %-20s %-15s %s%n", "#", "Name", "Phone", "Email");
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%-4d %s%n", i + 1, list.get(i));
        }
    }

    private static void searchContacts() {
        System.out.print("Search: ");
        show(book.search(sc.nextLine()));
    }

    private static Contact pick() {
        System.out.print("Search for the contact: ");
        List<Contact> found = book.search(sc.nextLine());
        if (found.isEmpty()) {
            System.out.println("No contacts found.");
            return null;
        }
        show(found);
        System.out.print("Enter number: ");
        try {
            int n = Integer.parseInt(sc.nextLine().trim());
            if (n >= 1 && n <= found.size()) return found.get(n - 1);
        } catch (NumberFormatException ignored) { }
        System.out.println("Invalid number.");
        return null;
    }

    private static void editContact() {
        Contact c = pick();
        if (c == null) return;
        System.out.print("New name (Enter to keep): ");
        String name = sc.nextLine();
        System.out.print("New phone (Enter to keep): ");
        String phone = sc.nextLine();
        System.out.print("New email (Enter to keep): ");
        String email = sc.nextLine();
        if (!name.isBlank()) c.setName(name);
        if (!phone.isBlank()) c.setPhone(phone);
        if (!email.isBlank()) c.setEmail(email);
        book.save();
        System.out.println("Contact updated.");
    }

    private static void deleteContact() {
        Contact c = pick();
        if (c == null) return;
        System.out.print("Delete " + c.getName() + "? (y/n): ");
        if (sc.nextLine().trim().equalsIgnoreCase("y")) {
            book.delete(c);
            System.out.println("Deleted.");
        }
    }
}