package views.computer.controllers;

import views.base.BaseController;
import views.commandprompt.bindings.CommandPromptBinding;
import views.commandprompt.interfaces.ICommandPromptController;
import views.commandprompt.interfaces.ICommandPromptViewModel;
import views.computer.enums.ComputerProperties;
import views.computer.interfaces.ICmdLineOperatingSystemController;
import views.computer.interfaces.IComputerViewModel;

/**
 * Created by Lasen on 06/11/16.
 */
public class CmdLineOperatingSystemController
        extends BaseController<ComputerProperties, IComputerViewModel>
        implements
        ICmdLineOperatingSystemController
{
    private CommandPromptBinding commandPrompt;

    public CmdLineOperatingSystemController( IComputerViewModel viewModel )
    {
        super( viewModel );
        commandPrompt = new CommandPromptBinding( viewModel.getCommandPromptProperty() );
    }

    @Override
    public ICommandPromptController getCommandPrompt()
    {
        return commandPrompt.getConvertedValue();
    }

    @Override
    public void setCommandPrompt( ICommandPromptController commandPromptController )
    {
        ICommandPromptViewModel commandPromptViewModel = CommandPromptBinding.getOriginalValue( commandPromptController );
        this.commandPrompt.forceSetValue( commandPromptViewModel );
        viewModel.setCommandPromptProperty( commandPromptViewModel );
    }
}
