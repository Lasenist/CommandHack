package views.commandprompt.commands;

import views.commandprompt.commands.interfaces.IShellCommand;
import views.commandprompt.interfaces.ICommandPromptViewModel;
import views.os.cmdline.interfaces.IComputerViewModel;
import views.os.cmdline.interfaces.IFolderViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

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

            if(folderSequence.length == 0)
            {
                computerViewModel.navigateToFolder( null );
            }
            else if( Objects.equals( folderSequence[0], "" ))
            {
                navigateToFolderFromFullPath( folderSequence );
            }
            else
            {
                ArrayList<String> fullFolderSequence = new ArrayList<>();
                fullFolderSequence.add( "" );
                IFolderViewModel currentFolder = computerViewModel.getCurrentFolder();

                if(currentFolder != null)
                {
                    fullFolderSequence.addAll( currentFolder.getPath().stream().collect( Collectors.toList() ) );
                    Collections.addAll( fullFolderSequence, folderSequence );
                }
                else
                {
                    Collections.addAll( fullFolderSequence, folderSequence );
                }

                navigateToFolderFromFullPath( fullFolderSequence.toArray( new String[fullFolderSequence
                        .size()] ) );
            }
        }
    }

    private void navigateToFolderFromFullPath( String[] folderSequence )
    {
        Boolean isNavigateSuccesfull = false;
        HashMap<String, IFolderViewModel> folders = computerViewModel.getFolders();
        for(int i = 1; i < folderSequence.length; i++)
        {
            if(folders.containsKey( folderSequence[i] ))
            {
                if(i == folderSequence.length - 1)
                {
                    IFolderViewModel folderViewModel = folders.get( folderSequence[i] );
                    computerViewModel.navigateToFolder(folderViewModel);
                    isNavigateSuccesfull = true;
                    break;
                }
                else
                {
                    folders = folders.get( folderSequence[i] ).getContainingFolders();
                }
            }
        }

        if(!isNavigateSuccesfull)
        {
            commandPromptViewModel.getOutputList().add( "cd: " + currentInput[1] + ": No such file or directory" );
        }
    }
}
