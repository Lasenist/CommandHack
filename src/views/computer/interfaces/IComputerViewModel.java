package views.computer.interfaces;

import views.base.interfaces.IBaseViewModel;
import views.commandprompt.interfaces.ICommandPromptViewModel;
import views.computer.enums.ComputerProperties;
import views.computer.viewmodel.User;
import views.computer.viewmodel.Connection;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lasen on 07/11/16.
 */
public interface IComputerViewModel extends IBaseViewModel<ComputerProperties>
{
    HashMap<String, IFolderViewModel> getFolders();

    IFolderViewModel getRootFolder();

    String getHostname();

    ArrayList<User> getUserList();

    Connection getNewConnection( User user );
}
