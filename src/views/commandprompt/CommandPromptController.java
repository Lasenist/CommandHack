package views.commandprompt;

import views.commandprompt.interfaces.ICommandPromptController;
import views.commandprompt.interfaces.ICommandPromptModel;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Lasen on 05/11/16.
 */
public class CommandPromptController implements
        ICommandPromptController, Observer
{
    private ICommandPromptModel commandPromptModel;

    private ArrayList<String> outputList;
    private String inputText;
    private String inputPrefix;

    private ArrayList<String> newOutputList;
    private String newInputText;
    private String newInputPrefix;

    private boolean isUpdateNeeded;

    public CommandPromptController( ICommandPromptModel commandPromptModel )
    {
        this.commandPromptModel = commandPromptModel;
        outputList = commandPromptModel.getOutputList();
        inputText = commandPromptModel.getInputText();
        inputPrefix = commandPromptModel.getInputPrefix();

        newOutputList = commandPromptModel.getOutputList();
        newInputText = commandPromptModel.getInputText();
        newInputPrefix = commandPromptModel.getInputPrefix();
    }

    @Override
    public void setOutputList( ArrayList<String> output )
    {
        outputList = output;
        commandPromptModel.setOutputList( output );
    }

    @Override
    public ArrayList<String> getOutputList()
    {
        return outputList;
    }

    @Override
    public void setInputText( String text )
    {
        inputText = text;
        commandPromptModel.setInputText( text );
    }

    @Override
    public String getInputText()
    {
        return inputText;
    }

    @Override
    public void setInputPrefix( String text )
    {
        inputPrefix = text;
        commandPromptModel.setInputPrefix( text );
    }

    @Override
    public String getInputPrefix()
    {
        return inputPrefix;
    }

    @Override
    public void submitInput()
    {
        commandPromptModel.submitInput();
    }


    public void update()
    {
        if(isUpdateNeeded)
        {
            outputList = newOutputList;
            inputText = newInputText;
            inputPrefix = newInputPrefix;
            isUpdateNeeded = false;
        }
    }

    public void update( Observable o, Object arg )
    {
        String propertyName = arg.toString();

        switch(propertyName)
        {
            case "OutputText": newOutputList = commandPromptModel.getOutputList();
                break;
            case "InputText": newInputText = commandPromptModel.getInputText();
                break;
            case "InputPrefix": newInputPrefix = commandPromptModel.getInputPrefix();
                break;
        }

        isUpdateNeeded = true;
    }
}
