package views.commandprompt.commands;

import views.commandprompt.commands.interfaces.IShellCommand;
import views.commandprompt.interfaces.ICommandPromptViewModel;
import views.os.cmdline.interfaces.IComputerViewModel;
import views.os.cmdline.interfaces.IFolderViewModel;

import java.util.*;

/**
 * Created by Lasen on 18/12/16.
 */
public class ChangeDirectoryShellCommand implements IShellCommand
{
    private IComputerViewModel computerViewModel;
    private ICommandPromptViewModel commandPromptViewModel;

    private String[] currentInput;

    public ChangeDirectoryShellCommand(IComputerViewModel computerViewModel, ICommandPromptViewModel commandPromptViewModel)
    {
        this.computerViewModel = computerViewModel;
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
            IFolderViewModel folderViewModel = computerViewModel.getCurrentFolder();
            ArrayList<String> outputList = commandPromptViewModel.getOutputList();
            folderViewModel.getContainingFolders().keySet().forEach( outputList::add );
            commandPromptViewModel.setOutputList( outputList );
            commandPromptViewModel.setOutputListOffset( commandPromptViewModel.getOutputListOffset() - folderViewModel.getContainingFolders().size() );
        }
        else if(inputArray.length >= 2)
        {
            String[] folderSequence = inputArray[1].split( "/" );
            IFolderViewModel folder = getFolder( folderSequence );


            //TODO:
            IFolderViewModel folderViewModel = computerViewModel.getCurrentFolder();
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
            computerViewModel.setCurrentFolder( currentFolder );
        }
    }

    private IFolderViewModel getFolder(String[] pathSequence)
    {
        HashMap<String, IFolderViewModel> folders = null;

        folders = Objects.equals( pathSequence[0], "" ) ? computerViewModel.getRootFolder().getContainingFolders() : computerViewModel.getCurrentFolder().getContainingFolders();

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
