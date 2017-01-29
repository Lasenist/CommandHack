package views.computer.interfaces;

import views.base.interfaces.IBaseViewModel;
import views.commandprompt.interfaces.ICommandPromptViewModel;
import views.computer.enums.CmdLineOperatingSystemProperties;

/**
 * Created by Lasen on 21/01/17.
 */
public interface ICmdLineOperatingSystemViewModel extends IBaseViewModel<CmdLineOperatingSystemProperties>
{
    ICommandPromptViewModel getCommandPromptProperty();

    void setCommandPromptProperty( ICommandPromptViewModel commandPromptViewModel );
}
