package views.commandprompt.commands;

import views.commandprompt.commands.interfaces.IFolderChangeListener;
import views.commandprompt.commands.interfaces.IShellCommand;
import views.commandprompt.interfaces.ICommandPromptViewModel;
import views.computer.interfaces.IComputerViewModel;
import views.computer.interfaces.IConnection;
import views.computer.interfaces.IFolderViewModel;

import java.util.*;

/**
 * Created by Lasen on 18/12/16.
 */
public class ChangeDirectoryShellCommand implements IShellCommand
{
    private IConnection connection;
    private ICommandPromptViewModel commandPromptViewModel;

    private String[] currentInput;


    public ChangeDirectoryShellCommand(IConnection connection, ICommandPromptViewModel commandPromptViewModel)
    {
        this.connection = connection;
        this.commandPromptViewModel = commandPromptViewModel;
    }

    @Override
    public String getIdentifier()
    {
        return "cd";
    }

    @Override
    public void execute( String[] inputArray )
    {
        this.currentInput = inputArray;
        if(inputArray.length == 2)
        {
            String[] folderSequence = inputArray[1].split( "/" );
            navigateToFolder( folderSequence );
        }
    }

    @Override
    public void autoComplete( String[] inputArray )
    {
        if(inputArray.length == 1)
        {
            IFolderViewModel folderViewModel = connection.getCurrentFolder();
            ArrayList<String> outputList = commandPromptViewModel.getOutputList();
            folderViewModel.getContainingFolders().keySet().forEach( outputList::add );
            commandPromptViewModel.setOutputList( outputList );
            commandPromptViewModel.setOutputListOffset(
                    commandPromptViewModel
                            .getOutputListOffset() -
                            folderViewModel
                                    .getContainingFolders()
                                    .size() );
        }
        else if(inputArray.length >= 2)
        {
            String[] folderSequence = inputArray[1].split( "/" );
            IFolderViewModel folder = getFolder( folderSequence );


            //TODO: auto complete paths
            IFolderViewModel folderViewModel = connection.getCurrentFolder();
            ArrayList<String> outputList = commandPromptViewModel.getOutputList();
            folder.getContainingFolders().keySet().forEach( outputList::add );
            commandPromptViewModel.setOutputList( outputList );
            commandPromptViewModel.setOutputListOffset( commandPromptViewModel.getOutputListOffset() - folderViewModel.getContainingFolders().size() );
        }
    }

    private void navigateToFolder( String[] pathSequence )
    {
        IFolderViewModel currentFolder = getFolder( pathSequence );

        if(currentFolder == null)
        {
            commandPromptViewModel.getOutputList().add( "cd: " + currentInput[1] + ": No such file or directory" );
        }
        else
        {
            connection.setCurrentFolder( currentFolder );
        }
    }

    private IFolderViewModel getFolder(String[] pathSequence)
    {
        HashMap<String, IFolderViewModel> folders;

        folders = Objects.equals( pathSequence[0], "" ) ? connection.getRootFolder().getContainingFolders() : connection.getCurrentFolder().getContainingFolders();

        IFolderViewModel currentFolder = null;
        Boolean isNavigationSuccessful = false;

        for(int i = 0; i < pathSequence.length; i++)
        {
            if(folders.containsKey( pathSequence[i] ))
            {
                if(i == pathSequence.length - 1)
                {
                    currentFolder = folders.get( pathSequence[i] );
                    isNavigationSuccessful = true;
                    break;
                }
                else
                {
                    currentFolder = folders.get( pathSequence[i] );
                    folders = currentFolder.getContainingFolders();
                }
            }
        }
        return isNavigationSuccessful ? currentFolder : null;
    }
}
