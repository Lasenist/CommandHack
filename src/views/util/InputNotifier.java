package views.util;

import org.newdawn.slick.ControlledInputReciever;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import java.util.ArrayList;

/**
 * Created by Lasen on 29/10/16.
 */
public class InputNotifier implements org.newdawn.slick.KeyListener
{
    private static ArrayList<org.newdawn.slick.KeyListener> subscribers = new ArrayList<>();

    public static InputNotifier getInputNotifier()
    {
        return new InputNotifier();
    }

    public void subscribe(KeyListener keyListener)
    {
        subscribers.add( keyListener );
    }

    public void unsubscribe(KeyListener keyListener)
    {
        subscribers.remove( keyListener );
    }

    public void keyPressed( int key, char c )
    {
        subscribers.stream().filter( ControlledInputReciever::isAcceptingInput ).forEach( item -> item.keyPressed( key, c ) );
    }

    public void keyReleased( int key, char c )
    {
        subscribers.stream().filter( ControlledInputReciever::isAcceptingInput ).forEach( item -> item.keyReleased( key, c ) );
    }

    public void setInput( Input input )
    {
        subscribers.stream().filter( ControlledInputReciever::isAcceptingInput ).forEach( item -> item.setInput( input ) );
    }

    public boolean isAcceptingInput()
    {
        return true;
    }

    public void inputEnded()
    {

    }

    public void inputStarted()
    {

    }
}
