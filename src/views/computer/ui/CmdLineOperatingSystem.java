package views.computer.ui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import views.IComponent;
import views.commandprompt.ui.CommandPrompt;
import views.computer.interfaces.ICmdLineOperatingSystemController;
import views.util.InputNotifier;

/**
 * Created by Lasen on 06/11/16.
 */
public class CmdLineOperatingSystem implements IComponent
{
    private ICmdLineOperatingSystemController
            controller;

    private CommandPrompt commandPrompt;

    public CmdLineOperatingSystem( ICmdLineOperatingSystemController controller, int screenWidth, int screenHeight ) throws SlickException
    {
        this.controller = controller;

        commandPrompt = new CommandPrompt( controller.getCommandPrompt() );
        commandPrompt.setWidth( screenWidth );
        commandPrompt.setHeight( screenHeight );

        InputNotifier.getInputNotifier().subscribe( commandPrompt );
    }

    @Override
    public void render( Graphics g )
    {
        this.commandPrompt.render( g );
    }

    @Override
    public void update()
    {
        this.controller.update();
        this.commandPrompt.update();
    }
}
