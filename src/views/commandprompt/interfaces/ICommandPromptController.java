package views.commandprompt.interfaces;

import views.base.interfaces.IBaseController;

import java.util.ArrayList;

/**
 * Created by Lasen on 05/11/16.
 */
public interface ICommandPromptController extends IBaseController
{
    void setOutputList( ArrayList<String> Output );
    ArrayList<String> getOutputList();

    void setInputText( String Text );
    String getInputText();

    void setInputPrefix(String text);
    String getInputPrefix();

    void submitInput();
}
