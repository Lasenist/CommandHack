package views.computer.viewmodel;

import objects.Property;
import views.base.BaseViewModel;
import views.commandprompt.interfaces.ICommandPromptViewModel;
import views.commandprompt.viewmodels.CommandPromptViewModel;
import views.commandprompt.viewmodels.LoginCommandPromptViewModel;
import views.computer.enums.CmdLineOperatingSystemProperties;
import views.computer.interfaces.ICmdLineOperatingSystemViewModel;
import views.computer.interfaces.IComputerViewModel;
import views.computer.interfaces.IConnection;
import views.computer.interfaces.events.ILoginListener;

/**
 * Created by Lasen on 21/01/17.
 */
public class CmdLineOperatingSystemViewModel extends BaseViewModel<CmdLineOperatingSystemProperties> implements ICmdLineOperatingSystemViewModel, ILoginListener
{
    private Property<ICommandPromptViewModel, CmdLineOperatingSystemProperties> commandPromptViewModelProperty;

    public CmdLineOperatingSystemViewModel(IComputerViewModel computerViewModel)
    {
        LoginCommandPromptViewModel loginCommandPromptViewModel = new LoginCommandPromptViewModel( computerViewModel );
        loginCommandPromptViewModel.addLoginListener( this );
        commandPromptViewModelProperty = new Property<>( loginCommandPromptViewModel, CmdLineOperatingSystemProperties.COMMAND_PROMPT );
        registerProperty( CmdLineOperatingSystemProperties.COMMAND_PROMPT, commandPromptViewModelProperty );
    }

    @Override
    public ICommandPromptViewModel getCommandPromptProperty()
    {
        return commandPromptViewModelProperty.getValue();
    }

    @Override
    public void setCommandPromptProperty( ICommandPromptViewModel commandPromptViewModel )
    {
        this.commandPromptViewModelProperty.setValue( commandPromptViewModel );
    }

    @Override
    public void loginAttempt( Boolean isSuccessful, IConnection connection )
    {
        if(isSuccessful)
        {
            CommandPromptViewModel commandPromptViewModel = new CommandPromptViewModel( connection );
            commandPromptViewModel.setOutputList( getCommandPromptProperty().getOutputList() );
            commandPromptViewModel.setOutputListOffset( getCommandPromptProperty().getOutputListOffset() );
            commandPromptViewModel.setInputText( getCommandPromptProperty().getInputText() );
            commandPromptViewModel.setCursorOffset( getCommandPromptProperty().getCursorOffset() );
            setCommandPromptProperty( commandPromptViewModel );
        }
    }
}
