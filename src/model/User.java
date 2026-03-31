package model;

public class User{
    private String id;
    private String fullName;
    private String email;
    private String password;
    private Role role;

    public User(String id, String fullName,String email, String password, Role role){
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    public String getId(){
        return id;
    }
    public String getFullName(){
        return fullName;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public Role getRole(){
         return role;
    }
    @Override
    public String toString() {
        return "ID: " + id +
                "\nName: " + fullName +
                "\nEmail: " + email +
                "\nRole: " + role;
    }
}