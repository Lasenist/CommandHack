package views.commandprompt.commands.interfaces;

/**
 * Created by Lasen on 18/12/16.
 */
public interface IShellCommand
{
    String getIdentifier();

    void execute( String[] inputArray );
}
