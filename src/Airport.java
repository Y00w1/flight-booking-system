public class Airport {
    private String codeIATA;
    private String name;
    private String city;
    private String country;

    public Airport(String codeIATA, String name, String city, String country) {
        this.codeIATA = codeIATA;
        this.name = name;
        this.city = city;
        this.country = country;
    }

    public String getCodeIATA() {
        return codeIATA;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
