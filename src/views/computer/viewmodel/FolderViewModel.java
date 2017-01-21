package views.computer.viewmodel;

import views.base.BaseViewModel;
import views.computer.interfaces.IFolderViewModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lasen on 10/11/16.
 */
public class FolderViewModel extends BaseViewModel implements IFolderViewModel
{
    private String name;
    private HashMap<String, IFolderViewModel> folders;
    private IFolderViewModel parentFolder;

    public FolderViewModel(String name)
    {
        this.name = name;
        this.folders = new HashMap<>();
    }

    public FolderViewModel(String name, IFolderViewModel parentFolder)
    {
        this(name);
        this.parentFolder = parentFolder;
        this.folders = new HashMap<>();

        folders.put( "..", parentFolder );
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public HashMap<String, IFolderViewModel> getContainingFolders()
    {
        return folders;
    }

    @Override
    public ArrayList<String> getPath()
    {
        ArrayList<String> folderList = new ArrayList<>();

        if(parentFolder != null)
        {
            folderList = parentFolder.getPath();
            folderList.add( name );
        }
        else
        {
            folderList.add( name );
        }

        return folderList;
    }

    public void addFolder(IFolderViewModel folderViewModel)
    {
        this.folders.put( folderViewModel.getName(), folderViewModel );
    }

}
