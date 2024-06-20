package simple1;

public class MemberTo {
    private String name;
    private String password;

    public MemberTo() {

    }

    public String getName() {
        System.out.println("getName 호출");
        return name;
    }

    public void setName(String name) {
        System.out.println("setName 호출");
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}