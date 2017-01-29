package views.computer.viewmodel;

import views.commandprompt.commands.interfaces.IFolderChangeListener;
import views.computer.interfaces.IComputerViewModel;
import views.computer.interfaces.IConnection;
import views.computer.interfaces.IFolderViewModel;

import java.util.ArrayList;

/**
 * Created by Lasen on 21/01/17.
 */
public class Connection implements IConnection
{
    private User user;
    private IComputerViewModel computer;
    private IFolderViewModel currentFolder;

    private ArrayList<IFolderChangeListener> folderChangeListeners;

    public Connection( User user , IComputerViewModel computerViewModel)
    {
        this.user = user;
        this.computer = computerViewModel;
        this.folderChangeListeners = new ArrayList<>();
    }

    @Override
    public String getUsername()
    {
        return user.getName();
    }

    @Override
    public String getComputerHostname()
    {
        return computer.getHostname();
    }

    @Override
    public String getCurrentPath()
    {
        String path = "";
        Boolean hasPath = false;
        for(String folder : getCurrentFolder().getPath())
        {
            path = path + "/" + folder;
            hasPath = true;
        }
        path = hasPath ? path : "/";
        return path;
    }

    @Override
    public IFolderViewModel getCurrentFolder()
    {
        return currentFolder != null ? currentFolder : computer.getRootFolder();
    }

    @Override
    public void setCurrentFolder( IFolderViewModel currentFolder )
    {
        this.currentFolder = currentFolder;
        folderChangeListeners.forEach( folderChangeListener -> folderChangeListener.folderChanged( currentFolder ) );

    }

    @Override
    public IFolderViewModel getRootFolder()
    {
        return computer.getRootFolder();
    }

    @Override
    public void addFolderChangeListener(IFolderChangeListener folderChangeListener)
    {
        this.folderChangeListeners.add( folderChangeListener );
    }
}
