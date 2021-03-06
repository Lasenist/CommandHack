package main;

import org.newdawn.slick.util.Log;
import views.computer.controllers.CmdLineOperatingSystemController;
import views.computer.ui.CmdLineOperatingSystem;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import views.computer.viewmodel.CmdLineOperatingSystemViewModel;
import views.computer.viewmodel.ComputerViewModel;
import views.util.InputNotifier;
import views.util.ShaderProgram;

/**
 * Created by Lasen on 06/09/16.
 */
public class Game extends BasicGameState
{

    // ID we return to class 'Application'
    public static final int ID = 2;

    private CmdLineOperatingSystem operatingSystem;

    private boolean supported;
    private ShaderProgram program;
    private String log;
    private boolean shaderWorks, useShader = true;

    // init-method for initializing all resources
    @Override
    public void init( GameContainer gc, StateBasedGame sbg ) throws SlickException
    {
        gc.getInput().addMouseListener( Application.MOUSE_CURSOR );
        gc.getInput().enableKeyRepeat();
        gc.getInput().addKeyListener( InputNotifier
                .getInputNotifier() );

        ComputerViewModel computerViewModel = new ComputerViewModel( "testmachine" );
        CmdLineOperatingSystemViewModel commandLineOsViewModel = new CmdLineOperatingSystemViewModel( computerViewModel );
        CmdLineOperatingSystemController commandLineOsController = new CmdLineOperatingSystemController( commandLineOsViewModel );
        operatingSystem = new CmdLineOperatingSystem( commandLineOsController, Application.WIDTH, Application.HEIGHT );

        supported = ShaderProgram.isSupported();

        if ( supported )
        {
            //we turn off "strict mode" which means the 'tex0' uniform
            //doesn't NEED to exist
            ShaderProgram.setStrictMode( false );
            reload();
        }

    }

    private void reload()
    {
        if ( ! supported )
        {
            return;
        }

        //release the program and everything associated with it
        if ( program != null )
        {
            program.release();
        }

        try
        {
            program = ShaderProgram.loadProgram( "res/invert.vert", "res/invert.frag" );
            shaderWorks = true;

            //good idea to print/display the log anyways in case there are warnings..
            log = program.getLog();
            if ( log != null && log.length() != 0 )
            {
                Log.warn( log );
            }
        }
        catch ( Exception e )
        {
            log = e.getMessage();
            Log.error( log );
            shaderWorks = false;
        }
    }

    // render-method for all the things happening on-screen
    @Override
    public void render( GameContainer gc, StateBasedGame sbg, Graphics g ) throws SlickException
    {
        Application.MOUSE_CURSOR.render( g );
        operatingSystem.render( g );

    }

    // update-method with all the magic happening in it
    @Override
    public void update( GameContainer gc, StateBasedGame sbg, int arg2 ) throws SlickException
    {
        operatingSystem.update();
    }

    // Returning 'ID' from class 'Game'
    @Override
    public int getID()
    {
        return Game.ID;
    }
}
