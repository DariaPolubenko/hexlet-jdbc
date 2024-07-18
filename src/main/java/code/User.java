package code;

public class User {
    private Long id;
    private String username;
    private String phone;

    User() {
    }

    User (String username, String phone) {
        this.username = username;
        this.phone = phone;
    }


    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    public Long getId() {
        return id;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
