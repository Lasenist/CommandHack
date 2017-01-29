package views.commandprompt.viewmodels;

import objects.Property;
import views.base.BaseViewModel;
import views.commandprompt.enums.CommandPromptProperties;
import views.commandprompt.interfaces.ICommandPromptViewModel;
import views.computer.interfaces.IComputerViewModel;
import views.computer.interfaces.events.ILoginListener;
import views.computer.viewmodel.User;
import views.computer.viewmodel.Connection;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Lasen on 21/01/17.
 */
public class LoginCommandPromptViewModel extends BaseViewModel<CommandPromptProperties> implements ICommandPromptViewModel
{
    private Property<ArrayList<String>, CommandPromptProperties> outputList;
    private Property<String, CommandPromptProperties> inputPrefix;
    private Property<String, CommandPromptProperties> inputText;
    private Property<Integer, CommandPromptProperties> cursorOffset;
    private Property<Integer, CommandPromptProperties> outputListOffset;

    private IComputerViewModel computerToLoginTo;

    private Boolean isEnteringPassword = false;
    private String username;

    private ArrayList<ILoginListener> loginListeners;
    private Connection connection;

    private Thread loginThread;

    public LoginCommandPromptViewModel(IComputerViewModel computerToLoginTo)
    {
        outputList = new Property<>( new ArrayList<>(), CommandPromptProperties.OUTPUT_LIST );
        inputPrefix = new Property<>( computerToLoginTo.getHostname() + " login: ", CommandPromptProperties.INPUT_PREFIX );
        inputText = new Property<>( "", CommandPromptProperties.INPUT_TEXT );
        cursorOffset = new Property<>( 0, CommandPromptProperties.CURSOR_OFFSET );
        outputListOffset = new Property<>( 0, CommandPromptProperties.OUTPUT_LIST_OFFSET );

        this.computerToLoginTo = computerToLoginTo;

        this.loginListeners = new ArrayList<>();

        registerProperty( outputList.getPropertyEnum(), outputList );
        registerProperty( inputPrefix.getPropertyEnum(), inputPrefix );
        registerProperty( inputText.getPropertyEnum(), inputText );
        registerProperty( cursorOffset.getPropertyEnum(), cursorOffset );
        registerProperty( outputListOffset.getPropertyEnum(), outputListOffset );

        outputText( "CmdOs 1.6 - " + computerToLoginTo.getHostname() );
        outputText( "" );
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

    private void outputText(String text)
    {
        ArrayList<String> outputList = getOutputList();
        outputList.add( text );
        setOutputListOffset( getOutputListOffset() - 1 );
    }

    private void outputCurrentInputText()
    {
        outputText(
                inputPrefix
                        .getValue() +
                        inputText
                                .getValue() );
    }

    @Override
    public void submitInput()
    {
        if(loginThread == null || !loginThread.isAlive())
        {
            if(isEnteringPassword)
            {
                outputText( getInputPrefix() + getInputText() );
                isEnteringPassword = false;

                ArrayList<User> users = computerToLoginTo.getUserList();

                Boolean isSuccessfulLogin = false;

                for(User user : users)
                {
                    if( Objects.equals( user.getName(), username ) && Objects.equals( user.getPassword(), getInputText() ) )
                    {
                        connection = computerToLoginTo.getNewConnection(user);
                        isSuccessfulLogin = true;
                        break;
                    }
                }

                final Boolean finalIsSuccessfulLogin = isSuccessfulLogin;

                inputPrefix.setValue( "" );
                inputText.setValue( "" );

                loginThread = new Thread( () ->
                {
                    try
                    {
                        Thread.sleep( 2000 );
                    }
                    catch ( InterruptedException e )
                    {
                        e.printStackTrace();
                    }
                    outputText( "" );
                    outputText( finalIsSuccessfulLogin ? "Login successful" : "Login Incorrect." );
                    inputPrefix.setToDefaultValue();
                    loginListeners.forEach( loginListener -> loginListener.loginAttempt( finalIsSuccessfulLogin, connection ) );
                } );
                loginThread.start();
            }
            else
            {
                username = getInputText();
                isEnteringPassword = true;
                outputCurrentInputText();
                setInputPrefix( "Password: " );
                inputText.setValue( "" );
                setCursorOffset( 0 );
            }
        }
    }

    @Override
    public void outputAutoComplete()
    {

    }

    public void addLoginListener(ILoginListener loginListener)
    {
        this.loginListeners.add( loginListener );
    }
}
