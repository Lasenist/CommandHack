package views.window;

import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import main.Application;
import views.IComponent;
import views.util.Fonts;
import views.util.InputNotifier;
import views.util.ShapeUtil;

/**
 * Created by Lasen on 08/09/16.
 */
public class Window implements
        IComponent, KeyListener, MouseListener
{
    private float x;
    private float y;
    public float width;
    public float height;
    private String headerTitle;

    private Rectangle windowOutline;
    private Rectangle header;
    private Rectangle headerFill;
    private IWindowContent component;
    private UnicodeFont font;
    private boolean hasFocus;
    private boolean beingDragged;

    public Window( IWindowContent Component, float x, float y ) throws SlickException
    {
        this.height = 343;
        this.width = 677;
        this.x = x;
        this.y = y;
        this.windowOutline = new Rectangle( this.x, this.y, this.width, this.height );
        this.header = new Rectangle( this.x, this.y, this.width, 25 );
        this.headerFill = new Rectangle( this.x, this.y, this.width - 1, 25 - 1 );
        this.headerTitle = Component.GetTitleHeader();
        this.component = Component;
        font = Fonts.GetMonospacedFont(16);

        this.component.setWidth( this.width );
        this.component.setHeight( this.windowOutline.getHeight() - this.header.getHeight() );
        this.component.update();

        InputNotifier.getInputNotifier().subscribe( this );
        Application.MOUSE_CURSOR.addMouseListener( this );
    }

    public void update()
    {
        this.windowOutline.setX( this.x );
        this.windowOutline.setY( this.y );
        this.header.setX( this.x );
        this.header.setY( this.y );
        this.headerFill.setX( this.x );
        this.headerFill.setY( this.y );
        this.component.setX( this.x );
        this.component.setY( this.y + this.header.getHeight() );
        this.component.setWidth( this.width );
        this.component.setHeight( this.windowOutline.getHeight() - this.header.getHeight() );
        this.component.update();
    }

    public void render( final Graphics g )
    {
        g.draw( this.windowOutline );

        g.draw( this.header );
        g.fill( this.headerFill, new ShapeFill()
        {
            public Color colorAt( Shape shape, float x, float y )
            {
                if(hasFocus)
                    return Color.white;
                return Color.black;
            }

            public Vector2f getOffsetAt( Shape shape, float x, float y )
            {
                return new Vector2f( 0, 1 );
            }
        } );
        int headerTitleHeight = g.getFont().getHeight( headerTitle );
        final int middleOfHeader = (int)this.header.getHeight() / 2;
        final int halfHeaderTitleHeight = headerTitleHeight / 2;
        font.drawString( (int)this.x + 10, (int)this.y + middleOfHeader - halfHeaderTitleHeight, headerTitle,
                hasFocus ? Color.black : Color.white );


        component.render( g );
    }

    public void keyPressed( int key, char c )
    {
        this.component.keyPressed( key, c );
    }

    public void keyReleased( int key, char c )
    {

    }

    public void setInput( Input input )
    {
        this.component.setInput(input);
    }

    public boolean isAcceptingInput()
    {
        return hasFocus;
    }

    public void inputEnded()
    {

    }

    public void inputStarted()
    {

    }

    public void mouseWheelMoved( int change )
    {

    }

    public void mouseClicked( int button, int x, int y, int clickCount )
    {
        this.hasFocus = ShapeUtil.isContaineeInsideContainer( windowOutline, Application.MOUSE_CURSOR.mouseHitbox );
        this.beingDragged = ShapeUtil.isContaineeInsideContainer( header, Application.MOUSE_CURSOR.mouseHitbox );
    }

    public void mousePressed( int button, int x, int y )
    {
        this.hasFocus = ShapeUtil.isContaineeInsideContainer( windowOutline, Application.MOUSE_CURSOR.mouseHitbox );
        this.beingDragged = ShapeUtil.isContaineeInsideContainer( header, Application.MOUSE_CURSOR.mouseHitbox );
    }

    public void mouseReleased( int button, int x, int y )
    {
        this.beingDragged = false;
    }

    public void mouseMoved( int oldx, int oldy, int newx, int newy )
    {

    }

    public void mouseDragged( int oldx, int oldy, int newx, int newy )
    {
        if(hasFocus && beingDragged)
        {
            this.x = this.x + ( newx - oldx );
            this.y = this.y + ( newy - oldy );
        }
    }

    public interface IWindowContent extends IComponent
    {
        String GetTitleHeader();
        void setX( float x );
        void setY( float y );
        void setHeight( float height );
        void setWidth( float width );
        void keyPressed(int key, char c);
        void setInput( Input input );
    }
}

