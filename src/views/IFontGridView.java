package views;

import java.util.ArrayList;

public interface IFontGridView
{
    void SetOutputText(ArrayList<String> Output);
    ArrayList<String> GetOutputText();

    void SetInputText(String Text);
    String GetInputText();

    void setInputPrefix(String text);
    String getInputPrefix();
}
