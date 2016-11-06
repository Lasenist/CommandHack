package views.commandprompt.interfaces;

import java.util.ArrayList;

public interface ICommandPromptModel
{
    void setOutputList( ArrayList<String> Output );
    ArrayList<String> getOutputList();

    void setInputText( String Text );
    String getInputText();

    void setInputPrefix(String text);
    String getInputPrefix();

    void submitInput();


}
