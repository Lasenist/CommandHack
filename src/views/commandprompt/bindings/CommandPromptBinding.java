package views.commandprompt.bindings;

import objects.ConverterBinding;
import views.commandprompt.controllers.CommandPromptController;
import views.commandprompt.interfaces.ICommandPromptController;
import views.commandprompt.interfaces.ICommandPromptViewModel;
import views.commandprompt.viewmodels.CommandPromptViewModel;

/**
 * Created by Lasen on 07/11/16.
 * Used to convert the command prompt view models into a controller for views to manipulate
 */
public class CommandPromptBinding extends ConverterBinding<ICommandPromptViewModel, ICommandPromptController>
{

    public CommandPromptBinding( ICommandPromptViewModel defaultValue )
    {
        super( defaultValue );
    }

    @Override
    public ICommandPromptController getConvertedValue()
    {
        if(convertedValue == null)
        {
            convertedValue = new CommandPromptController( getValue() );
            getValue().addObserver( convertedValue );
        }
        return convertedValue;
    }

    public static ICommandPromptViewModel getOriginalValue( ICommandPromptController commandPromptController )
    {
        ICommandPromptViewModel result = null;
        if(commandPromptController instanceof CommandPromptViewModel )
        {
            //noinspection ConstantConditions -- cast should be fine due to the check above
            CommandPromptController cmdPrmptCont = (CommandPromptController) commandPromptController;
            result = cmdPrmptCont.getViewModel();
        }
        return result;
    }
}
