package views.computer.interfaces.events;

import views.computer.interfaces.IConnection;

/**
 * Created by Lasen on 21/01/17.
 */
public interface ILoginListener
{
    void loginAttempt(Boolean isSuccessful, IConnection connection);
}
