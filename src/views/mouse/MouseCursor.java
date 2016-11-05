package views.mouse;

import views.IComponent;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.geom.Circle;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;


/**
 * Created by Lasen on 10/09/16.
 */
public class MouseCursor implements IComponent, MouseListener
{
    public Circle mouseHitbox;
    private boolean isMouseButtonOnePressed;

    private ArrayList<IScrollListener> scrollListeners;
    private ArrayList<MouseListener> mouseListeners;


    public MouseCursor()
    {
        this.mouseHitbox = new Circle( - 5, - 5, 0.5f );
        this.scrollListeners = new ArrayList<>();
        this.mouseListeners = new ArrayList<>();
    }

    public void render( Graphics g )
    {
        g.draw( this.mouseHitbox );
    }

    public void update()
    {

    }

    public void mouseWheelMoved( int change )
    {
        for(IScrollListener listener : scrollListeners)
        {
            listener.ScrollChanged( change );
        }
    }

    public void mouseClicked( int button, int x, int y, int clickCount )
    {
        for(MouseListener listener : mouseListeners)
        {
            listener.mouseClicked( button, x, y, clickCount );
        }
    }

    public void mousePressed( int button, int x, int y )
    {
        if ( button == 0 )
        {
            this.isMouseButtonOnePressed = true;
        }

        for(MouseListener listener : mouseListeners)
        {
            listener.mousePressed( button, x, y );
        }
    }

    public void mouseReleased( int button, int x, int y )
    {
        this.isMouseButtonOnePressed = false;

        for(MouseListener listener : mouseListeners)
        {
            listener.mouseReleased( button, x, y );
        }
    }

    public void mouseMoved( int oldx, int oldy, int newx, int newy )
    {
        this.mouseHitbox.setX( newx );
        this.mouseHitbox.setY( newy );

        for(MouseListener listener : mouseListeners)
        {
            listener.mouseMoved( oldx, oldy, newx, newy );
        }
    }

    public void mouseDragged( int oldx, int oldy, int newx, int newy )
    {
        this.mouseHitbox.setX( newx );
        this.mouseHitbox.setY( newy );

        for(MouseListener listener : mouseListeners)
        {
            listener.mouseDragged( oldx, oldy, newx, newy );
        }
    }

    public void setInput( Input input )
    {
        for(MouseListener listener : mouseListeners)
        {
            listener.setInput( input );
        }
    }

    public boolean isAcceptingInput()
    {
        return true;
    }

    public void inputEnded()
    {
        mouseListeners.forEach( org.newdawn.slick.MouseListener::inputEnded );
    }

    public void inputStarted()
    {
        mouseListeners.forEach( org.newdawn.slick.MouseListener::inputStarted );
    }

    public boolean getIsMouseButtonOnePressed()
    {
        return this.isMouseButtonOnePressed;
    }

    public void addScrollListener(IScrollListener listener)
    {
        this.scrollListeners.add( listener );
    }

    public void removeScrollListener(IScrollListener listener)
    {
        this.scrollListeners.remove( listener );
    }

    public void addMouseListener(MouseListener listener)
    {
        this.mouseListeners.add( listener );
    }

    public void removeMouseListener(MouseListener listener)
    {
        this.mouseListeners.remove( listener );
    }
}
