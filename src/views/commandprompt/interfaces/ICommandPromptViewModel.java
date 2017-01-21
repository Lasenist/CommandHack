package views.commandprompt.interfaces;

import views.base.interfaces.IBaseViewModel;
import views.commandprompt.enums.CommandPromptProperties;

import java.util.ArrayList;

public interface ICommandPromptViewModel extends IBaseViewModel<CommandPromptProperties>
{
    void setOutputList( ArrayList<String> Output );
    ArrayList<String> getOutputList();

    void setInputText( String Text );
    String getInputText();

    void setInputPrefix( String text );
    String getInputPrefix();

    void setCursorOffset( int value );
    int getCursorOffset();

    void setOutputListOffset( int value );
    int getOutputListOffset();

    void submitInput();
    void outputAutoComplete();
}
