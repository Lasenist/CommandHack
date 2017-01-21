package views.computer.interfaces;

import views.base.interfaces.IBaseController;
import views.commandprompt.interfaces.ICommandPromptController;

/**
 * Created by Lasen on 07/11/16.
 */
public interface ICmdLineOperatingSystemController extends IBaseController
{
    ICommandPromptController getCommandPrompt();
    void setCommandPrompt( ICommandPromptController commandPrompt );
}
