package views.computer.viewmodel;

import views.computer.interfaces.IComputerViewModel;
import views.computer.interfaces.IFolderViewModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lasen on 17/01/17.
 */
public class RootFolderViewModel implements IFolderViewModel
{
    private HashMap<String, IFolderViewModel> containingFolders;

    public RootFolderViewModel(IComputerViewModel computerViewModel)
    {
       this.containingFolders = computerViewModel.getFolders();
    }

    @Override
    public String getName()
    {
        return "root";
    }

    @Override
    public HashMap<String, IFolderViewModel> getContainingFolders()
    {
        return containingFolders;
    }

    @Override
    public ArrayList<String> getPath()
    {
        return new ArrayList<>();
    }
}
