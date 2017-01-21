package views.commandprompt.viewmodels;

import objects.Property;
import views.base.BaseViewModel;
import views.commandprompt.enums.CommandPromptProperties;
import views.commandprompt.interfaces.ICommandPromptViewModel;
import views.commandprompt.commands.interfaces.IShellCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;


/**
 * Created by Lasen on 05/11/16.
 * View model for the command prompt
 */
public class CommandPromptViewModel extends BaseViewModel<CommandPromptProperties> implements ICommandPromptViewModel
{
    private Property<ArrayList<String>, CommandPromptProperties> outputList;
    private Property<String, CommandPromptProperties> inputPrefix;
    private Property<String, CommandPromptProperties> inputText;
    private Property<Integer, CommandPromptProperties> cursorOffset;
    private Property<Integer, CommandPromptProperties> outputListOffset;

    private HashMap<String,IShellCommand> shellCommands;

    public CommandPromptViewModel()
    {
        outputList = new Property<>( new ArrayList<>(), CommandPromptProperties.OUTPUT_LIST );
        inputPrefix = new Property<>( "lasen@localhost:/$ ", CommandPromptProperties.INPUT_PREFIX );
        inputText = new Property<>( "", CommandPromptProperties.INPUT_TEXT );
        cursorOffset = new Property<>( 0, CommandPromptProperties.CURSOR_OFFSET );
        outputListOffset = new Property<>( 0, CommandPromptProperties.OUTPUT_LIST_OFFSET );

        shellCommands = new HashMap<>();

        registerProperty( outputList.getPropertyEnum(), outputList );
        registerProperty( inputPrefix.getPropertyEnum(), inputPrefix );
        registerProperty( inputText.getPropertyEnum(), inputText );
        registerProperty( cursorOffset.getPropertyEnum(), cursorOffset );
        registerProperty( outputListOffset.getPropertyEnum(), outputListOffset );
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
    public void setCursorOffset( int value )
    {
        this.cursorOffset.setValue( value );
    }

    @Override
    public int getCursorOffset()
    {
        return cursorOffset.getValue();
    }

    @Override
    public void setOutputListOffset( int value )
    {
        outputListOffset.setValue( value );
    }

    @Override
    public int getOutputListOffset()
    {
        return outputListOffset.getValue();
    }

    private void outputCurrentInputText()
    {
        ArrayList<String> outputTextValue = getOutputList();
        outputTextValue.add( getInputPrefix() + getInputText() );
        setOutputListOffset( getOutputListOffset() - 1 );
    }

    @Override
    public void submitInput()
    {
        outputCurrentInputText();

        String[] inputArray = getInputText().split(" ");
        String command = inputArray[0];
        IShellCommand shellCommand = shellCommands.get( command );

        if(shellCommand == null)
        {
            getOutputList().add(
                    command +
                            ": command not found" );
        }
        else
        {
            shellCommand.execute( inputArray );
        }
        inputText.setValue( "" );
    }

    @Override
    public void outputAutoComplete()
    {
        String[] inputArray = getInputText().split(" ");
        String command = inputArray[0];
        IShellCommand shellCommand = shellCommands.get( command );

        if(shellCommand != null)
        {
            if(inputText.getValue().toCharArray()[getInputText().length() - 1] != ' ')
            {
                inputText.setValue( getInputText() + " " );
                cursorOffset.setValue( getCursorOffset() + 1 );
            }
            else
            {
                outputCurrentInputText();
                shellCommand.autoComplete( inputArray );
            }
        }
        else
        {
            ArrayList<String> suggestions = new ArrayList<>();

            suggestions.addAll( shellCommands
                    .values()
                    .stream()
                    .filter( aCommand -> aCommand
                            .getIdentifier()
                            .startsWith( command ) )
                    .map( IShellCommand::getIdentifier )
                    .collect( Collectors
                            .toList() ) );

            if(suggestions.size() == 1)
            {
                outputCurrentInputText();
                String input = suggestions.get( 0 ).trim();
                inputText.setValue( input + " " );
                cursorOffset.setValue( input.length() + 1 );
            }
            else if (suggestions.size() != 0)
            {
                outputCurrentInputText();
                ArrayList<String> outputList = getOutputList();
                outputList.addAll( suggestions );
                this.outputList.setValue( outputList );
                this.outputListOffset.setValue( getOutputListOffset() - suggestions.size() );
            }
        }
    }

    public void addShellCommand(IShellCommand shellCommand)
    {
        shellCommands.put( shellCommand.getIdentifier(), shellCommand );
    }
}
