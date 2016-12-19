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

    public CommandPromptController( ICommandPromptViewModel commandPromptModel )
    {
        super(commandPromptModel);
        this.commandPromptModel = commandPromptModel;
        outputList = new Binding<>( commandPromptModel.getOutputList() );
        inputPrefix = new Binding<>( commandPromptModel.getInputPrefix() );
        inputText = new Binding<>( commandPromptModel.getInputText() );

        registerProperty( CommandPromptProperties.OUTPUT_LIST, outputList );
        registerProperty( CommandPromptProperties.INPUT_TEXT, inputText );
        registerProperty( CommandPromptProperties.INPUT_PREFIX, inputPrefix );
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
    public void submitInput()
    {
        commandPromptModel.submitInput();
    }
}
