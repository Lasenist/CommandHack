package views.os.cmdline.interfaces;

import views.base.interfaces.IBaseViewModel;
import views.commandprompt.interfaces.ICommandPromptViewModel;
import views.os.cmdline.enums.ComputerProperties;
import views.os.cmdline.viewmodel.FolderViewModel;

import java.util.ArrayList;
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
