package views.commandprompt.commands.interfaces;

/**
 * Created by Lasen on 12/01/17.
 */
public class TestShellCommand implements IShellCommand
{
    private String identifier;

    @Override
    public String getIdentifier()
    {
        return identifier;
    }

    @Override
    public void execute( String[] inputArray )
    {

    }

    @Override
    public void autoComplete( String[] inputArray )
    {

    }

    public TestShellCommand(String identifier)
    {
        this.identifier = identifier;
    }
}
