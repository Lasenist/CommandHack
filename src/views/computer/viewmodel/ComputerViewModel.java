package views.computer.viewmodel;

import objects.Property;
import views.base.BaseViewModel;
import views.commandprompt.interfaces.ICommandPromptViewModel;
import views.commandprompt.viewmodels.LoginCommandPromptViewModel;
import views.computer.enums.ComputerProperties;
import views.computer.interfaces.IComputerViewModel;
import views.computer.interfaces.IFolderViewModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lasen on 07/11/16.
 */
public class ComputerViewModel extends BaseViewModel<ComputerProperties> implements IComputerViewModel
{
    private String hostname;
    private HashMap<String, IFolderViewModel> folders;
    private IFolderViewModel rootFolder;
    private ArrayList<User> userList;

    public ComputerViewModel(String hostname)
    {
        this.hostname = hostname;

        folders = new HashMap<>();
        rootFolder = new RootFolderViewModel( this );

        FolderViewModel folderViewModel = new FolderViewModel( "test", rootFolder );
        folderViewModel.addFolder( new FolderViewModel( "testing", folderViewModel ) );
        folders.put( folderViewModel.getName(), folderViewModel );

        this.userList = new ArrayList<>();
        userList.add( new User( "admin", "testing" ) );
    }

    public HashMap<String, IFolderViewModel> getFolders()
    {
        return folders;
    }

    @Override
    public IFolderViewModel getRootFolder()
    {
        return rootFolder;
    }

    @Override
    public String getHostname()
    {
        return hostname;
    }

    @Override
    public ArrayList<User> getUserList()
    {
        return userList;
    }

    @Override
    public Connection getNewConnection( User user )
    {
        return new Connection(user, this);
    }
}
