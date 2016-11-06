package views.commandprompt.viewmodel;

import objects.ObservableValue;
import views.commandprompt.CommandPromptProperties;
import views.commandprompt.interfaces.ICommandPromptModel;

import java.util.ArrayList;
import java.util.Observer;

/**
 * Created by Lasen on 05/11/16.
 */
public class CommandPromptViewModel implements
        ICommandPromptModel
{
    private ObservableValue<ArrayList<String>> outputList = new ObservableValue<>( new ArrayList<>(), CommandPromptProperties.OUTPUT_LIST );
    private ObservableValue<String> inputText = new ObservableValue<>( "", CommandPromptProperties.INPUT_TEXT );
    private ObservableValue<String> inputPrefix = new ObservableValue<>( "lasen@127.0.0.1$", CommandPromptProperties.INPUT_PREFIX );

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

    public CommandPromptViewModel()
    {

    }

    public void addObserver( Observer o )
    {
        outputList.addObserver( o );
        inputText.addObserver( o );
        inputPrefix.addObserver( o );
    }

    @Override
    public void submitInput()
    {
        ArrayList<String> outputTextValue = outputList.getValue();
        outputTextValue.add( inputPrefix.getValue() + inputText.getValue() );
        outputList.setValue( outputTextValue );
        inputText.setValue( "" );
    }
}
