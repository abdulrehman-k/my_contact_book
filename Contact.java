public class Contact {
    private String name;
    private String phone;
    private String email;

    public Contact(String name, String phone, String email) {
        this.name = clean(name);
        this.phone = clean(phone);
        this.email = clean(email);
    }

    // Commas would break the CSV format, so replace them
    private static String clean(String s) {
        return s.replace(",", " ").trim();
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }

    public void setName(String name) { this.name = clean(name); }
    public void setPhone(String phone) { this.phone = clean(phone); }
    public void setEmail(String email) { this.email = clean(email); }

    public String toCsv() {
        return name + "," + phone + "," + email;
    }

    public static Contact fromCsv(String line) {
        String[] p = line.split(",", -1);
        return new Contact(p[0], p.length > 1 ? p[1] : "", p.length > 2 ? p[2] : "");
    }

    @Override
    public String toString() {
        return String.format("%-20s %-15s %s", name, phone, email);
    }
}