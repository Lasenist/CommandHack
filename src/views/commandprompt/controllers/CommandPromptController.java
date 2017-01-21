package views.commandprompt.controllers;

import objects.Binding;
import views.base.BaseController;
import views.commandprompt.enums.CommandPromptProperties;
import views.commandprompt.interfaces.ICommandPromptController;
import views.commandprompt.interfaces.ICommandPromptViewModel;

import java.util.*;

/**
 * Created by Lasen on 05/11/16.
 * Managers propery changes from the view model and handing them to the ui.
 */
public class CommandPromptController extends BaseController<CommandPromptProperties, ICommandPromptViewModel> implements ICommandPromptController
{
    private ICommandPromptViewModel commandPromptModel;

    private Binding<ArrayList<String>> outputList;
    private Binding<String> inputPrefix;
    private Binding<String> inputText;
    private Binding<Integer> cursorOffset;
    private Binding<Integer> outputListOffset;

    public CommandPromptController( ICommandPromptViewModel commandPromptModel )
    {
        super(commandPromptModel);
        this.commandPromptModel = commandPromptModel;
        outputList = new Binding<>( commandPromptModel.getOutputList() );
        inputPrefix = new Binding<>( commandPromptModel.getInputPrefix() );
        inputText = new Binding<>( commandPromptModel.getInputText() );
        cursorOffset = new Binding<>( commandPromptModel.getCursorOffset() );
        outputListOffset = new Binding<>( commandPromptModel.getOutputListOffset() );

        registerProperty( CommandPromptProperties.OUTPUT_LIST, outputList );
        registerProperty( CommandPromptProperties.INPUT_TEXT, inputText );
        registerProperty( CommandPromptProperties.INPUT_PREFIX, inputPrefix );
        registerProperty( CommandPromptProperties.CURSOR_OFFSET, cursorOffset );
        registerProperty( CommandPromptProperties.OUTPUT_LIST_OFFSET, outputListOffset );
    }

    @Override
    public void setOutputList( ArrayList<String> output )
    {
        outputList.forceSetValue( output );
        commandPromptModel.setOutputList( output );
    }

    @Override
    public ArrayList<String> getOutputList()
    {
        return outputList.getValue();
    }

    @Override
    public void setInputText( String text )
    {
        inputText.forceSetValue( text );
        commandPromptModel.setInputText( text );
    }

    @Override
    public String getInputText()
    {
        return inputText.getValue();
    }

    @Override
    public void setInputPrefix( String text )
    {
        inputPrefix.forceSetValue( text );
        commandPromptModel.setInputPrefix( text );
    }

    @Override
    public String getInputPrefix()
    {
        return inputPrefix.getValue();
    }

    @Override
    public void setCursorOffset( int value )
    {
        cursorOffset.forceSetValue( value );
        commandPromptModel.setCursorOffset( value );
    }

    @Override
    public int getCursorOffset()
    {
        return cursorOffset.getValue();
    }

    @Override
    public void setOutputListOffset( int value )
    {
        outputListOffset.forceSetValue( value );
        commandPromptModel.setOutputListOffset( value );
    }

    @Override
    public int getOutputListOffset()
    {
        return outputListOffset.getValue();
    }

    @Override
    public void submitInput()
    {
        commandPromptModel.submitInput();
    }

    @Override
    public void outputAutoComplete()
    {
        commandPromptModel.outputAutoComplete();
    }
}
