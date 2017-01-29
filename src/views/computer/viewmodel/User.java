package views.computer.viewmodel;

/**
 * Created by Lasen on 21/01/17.
 */
public class User
{
    private String name;
    private String password;

    public String getName()
    {
        return name;
    }

    public String getPassword()
    {
        return password;
    }

    public User(String user, String password )
    {
        this.name = user;
        this.password = password;
    }
}
