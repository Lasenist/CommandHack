package views.computer.interfaces;

import views.base.interfaces.IBaseViewModel;
import views.commandprompt.interfaces.ICommandPromptViewModel;
import views.computer.enums.ComputerProperties;

import java.util.HashMap;

/**
 * Created by Lasen on 07/11/16.
 */
public interface IComputerViewModel extends IBaseViewModel<ComputerProperties>
{

    ICommandPromptViewModel getCommandPromptProperty();
    void setCommandPromptProperty( ICommandPromptViewModel commandPrompt );

    HashMap<String, IFolderViewModel> getFolders();

    void setCurrentFolder( IFolderViewModel folderViewModel );

    IFolderViewModel getCurrentFolder();
    IFolderViewModel getRootFolder();
}
