package views.commandprompt.viewmodels;

import objects.Property;
import views.base.BaseViewModel;
import views.commandprompt.enums.CommandPromptProperties;
import views.commandprompt.interfaces.ICommandPromptViewModel;
import views.commandprompt.commands.interfaces.IShellCommand;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Lasen on 05/11/16.
 * View model for the command prompt
 */
public class CommandPromptViewModel extends BaseViewModel<CommandPromptProperties> implements ICommandPromptViewModel
{
    private Property<ArrayList<String>, CommandPromptProperties> outputList;
    private Property<String, CommandPromptProperties> inputPrefix;
    private Property<String, CommandPromptProperties> inputText;

    private HashMap<String,IShellCommand> shellCommands;

    public CommandPromptViewModel()
    {
        outputList = new Property<>( new ArrayList<>(), CommandPromptProperties.OUTPUT_LIST );
        inputPrefix = new Property<>( "lasen@127.0.0.1:$ ", CommandPromptProperties.INPUT_PREFIX );
        inputText = new Property<>( "", CommandPromptProperties.INPUT_TEXT );
        shellCommands = new HashMap<>();

        registerProperty( outputList.getPropertyEnum(), outputList );
        registerProperty( inputPrefix.getPropertyEnum(), inputPrefix );
        registerProperty( inputText.getPropertyEnum(), inputText );
    }

    @Override
    public void setOutputList( ArrayList<String> Output )
    {
        outputList.setValue( Output );
    }

    @Override
    public ArrayList<String> getOutputList()
    {
        return outputList.getValue();
    }

    @Override
    public void setInputText( String text )
    {
        this.inputText.setValue( text );
    }

    @Override
    public String getInputText()
    {
        return inputText.getValue();
    }

    @Override
    public void setInputPrefix( String text )
    {
        this.inputPrefix.setValue( text );
    }

    @Override
    public String getInputPrefix()
    {
        return inputPrefix.getValue();
    }

    @Override
    public void submitInput()
    {
        ArrayList<String> outputTextValue = outputList.getValue();
        outputTextValue.add( inputPrefix.getValue() + inputText.getValue() );
        //outputList.setValue( outputTextValue );

        String[] inputArray = inputText.getValue().split(" ");
        String command = inputArray[0];
        IShellCommand shellCommand = shellCommands.get( command );

        if(shellCommand == null)
        {
            outputTextValue.add( command + ": command not found" );
        }
        else
        {
            shellCommand.execute(inputArray);
        }
        inputText.setValue( "" );
    }

    @Override
    public void outputAutoComplete()
    {


    }

    public void addShellCommand(IShellCommand shellCommand)
    {
        shellCommands.put( shellCommand.getIdentifier(), shellCommand );
    }
}
