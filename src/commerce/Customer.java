import java.util.List;
import java.util.UUID;
public class Customer {


    private UUID id;
    private String username;
    private String email;

    private List<Address> address;

    //type name;


    public Customer(UUID id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public Customer(UUID id, String username, String email, List<Address> address) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public List<Address> getAddress() {
        return address;
    }

    public UUID getId() {
        return id;
    }
}
