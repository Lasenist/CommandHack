package main;

import views.mouse.MouseCursor;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Lasen on 05/09/16.
 */
public class Application extends StateBasedGame
{

    // Game state identifiers
    public static final int SPLASHSCREEN = 0;
    public static final int MAINMENU = 1;
    public static final int GAME = 2;

    // Application Properties
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    public static final int FPS = 60;
    public static final double VERSION = 1.0;

    public static MouseCursor MOUSE_CURSOR = new MouseCursor();

    // Class Constructor
    public Application( String appName )
    {
        super( appName );
    }


    // Initialize your game states (calls init method of each gamestate, and set's the state ID)
    public void initStatesList( GameContainer gc ) throws SlickException
    {
        // The first state added will be the one that is loaded first, when the application is launched
        this.addState( new Game() );
        enterState( GAME );
    }

    // Main Method
    public static void main( String[] args )
    {
        try
        {
            AppGameContainer app = new AppGameContainer( new Application( "Command Hack " + VERSION ) );
            app.setDisplayMode( WIDTH, HEIGHT, false );
            app.setTargetFrameRate( FPS );
            app.setVSync( true );
            app.setShowFPS( false );
            app.start();

        }
        catch ( SlickException e )
        {
            e.printStackTrace();
        }
    }


}

