package views.os.cmdline.viewmodel;

import objects.Property;
import views.base.BaseViewModel;
import views.commandprompt.interfaces.ICommandPromptViewModel;
import views.commandprompt.viewmodels.CommandPromptViewModel;
import views.commandprompt.commands.ShellCommands;
import views.os.cmdline.enums.ComputerProperties;
import views.os.cmdline.interfaces.IComputerViewModel;
import views.os.cmdline.interfaces.IFolderViewModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lasen on 07/11/16.
 */
public class ComputerViewModel extends BaseViewModel<ComputerProperties> implements IComputerViewModel
{
    private HashMap<String, IFolderViewModel> folders;
    private IFolderViewModel currentFolder;

    private Property<ICommandPromptViewModel, ComputerProperties> commandPromptViewModelProperty;

    public ComputerViewModel()
    {
        CommandPromptViewModel cmdPromptVM = new CommandPromptViewModel();
        cmdPromptVM.addShellCommand( ShellCommands.createChangeDirectory(this, cmdPromptVM) );

        commandPromptViewModelProperty = new Property<>( cmdPromptVM, ComputerProperties.COMMAND_PROMPT );

        folders = new HashMap<>();
        FolderViewModel folderViewModel = new FolderViewModel( "test" );
        folderViewModel.addFolder( new FolderViewModel( "testing", folderViewModel ) );
        folders.put( folderViewModel.getName(), folderViewModel );

        registerProperty( ComputerProperties.COMMAND_PROMPT, commandPromptViewModelProperty );

    }

    @Override
    public ICommandPromptViewModel getCommandPromptProperty()
    {
        return commandPromptViewModelProperty.getValue();
    }

    @Override
    public void setCommandPromptProperty( ICommandPromptViewModel commandPromptViewModel )
    {
        this.commandPromptViewModelProperty.setValue( commandPromptViewModel );
    }

    public HashMap<String, IFolderViewModel> getFolders()
    {
        return folders;
    }

    @Override
    public void navigateToFolder( IFolderViewModel folder )
    {
        String path = "";
        if(folder != null)
        {
            ArrayList<String> folderList = folder.getPath();
            IFolderViewModel currentFolder = null;
            HashMap<String, IFolderViewModel> currentFolderToSearch = folders;
            for(String folderName : folderList)
            {
                if(currentFolderToSearch.containsKey( folderName ))
                {
                    currentFolder = currentFolderToSearch.get( folderName );
                    currentFolderToSearch = currentFolder.getContainingFolders();
                    path = path + "/" + currentFolder.getName();
                }
                else
                {
                    System.out.println( "Unable to find folder" );
                }
            }
            this.currentFolder = currentFolder;
        }
        else
        {
            this.currentFolder = null;
        }
        commandPromptViewModelProperty.getValue().setInputPrefix( "lasen@127.0.0.1:" + path + "$ " );
    }

    @Override
    public IFolderViewModel getCurrentFolder()
    {
        return currentFolder;
    }
}
