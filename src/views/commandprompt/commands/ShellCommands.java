package views.commandprompt.commands;


import views.commandprompt.commands.interfaces.IShellCommand;
import views.commandprompt.interfaces.ICommandPromptViewModel;
import views.computer.interfaces.IComputerViewModel;
import views.computer.interfaces.IConnection;

/**
 * Created by Lasen on 18/12/16.
 */
public class ShellCommands
{

    public static IShellCommand createChangeDirectory(IConnection connection, ICommandPromptViewModel commandPromptViewModel)
    {
        return new ChangeDirectoryShellCommand(connection, commandPromptViewModel);
    }

}
