package views.os.cmdline.viewmodel;

import objects.Property;
import views.base.BaseViewModel;
import views.commandprompt.commands.interfaces.TestShellCommand;
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
    private IFolderViewModel rootFolder;

    private Property<ICommandPromptViewModel, ComputerProperties> commandPromptViewModelProperty;

    public ComputerViewModel()
    {
        CommandPromptViewModel cmdPromptVM = new CommandPromptViewModel();
        cmdPromptVM.addShellCommand( ShellCommands.createChangeDirectory(this, cmdPromptVM) );

        commandPromptViewModelProperty = new Property<>( cmdPromptVM, ComputerProperties.COMMAND_PROMPT );

        folders = new HashMap<>();
        rootFolder = new RootFolderViewModel( this );

        FolderViewModel folderViewModel = new FolderViewModel( "test", rootFolder );
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
    public void setCurrentFolder( IFolderViewModel folderViewModel )
    {
        this.currentFolder = folderViewModel;

        String path = "";
        Boolean hasPath = false;
        for(String folder : currentFolder.getPath())
        {
            path = path + "/" + folder;
            hasPath = true;
        }
        path = hasPath ? path : "/";
        getCommandPromptProperty().setInputPrefix( "lasen@localhost:" +  path + "$" );
    }

    @Override
    public IFolderViewModel getCurrentFolder()
    {
        return currentFolder != null ? currentFolder : rootFolder;
    }

    @Override
    public IFolderViewModel getRootFolder()
    {
        return rootFolder;
    }
}
