package views;

import main.Application;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import views.mouse.IScrollListener;
import views.util.Fonts;
import views.util.ShapeUtil;


import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Lasen on 02/10/16.
 * Creates a grid of characters.
 */
public class CommandTextGrid implements Window.IWindowContent, IScrollListener, IFontGridView, KeyListener
{
    private UnicodeFont font;
    private int charHeight;
    private int charWidth;
    private int gridWidth;
    private int gridHeight;

    private float x;
    private float y;

    private int height;
    private int width;

    private boolean isListeningToMouseScroll;
    private int outputScrollOffset;

    private ArrayList<String> outputText;

    private String inputText;
    private String inputPrefix;
    private int cursorOffset;
    private int inputTextOffset;

    private boolean drawCursor;

    public CommandTextGrid() throws SlickException
    {
        this.outputText = new ArrayList<>(  );
        this.inputPrefix = "lasen@127.0.0.1$";
        this.inputText = "";

        this.outputText.add( "Kernel start...." );
        this.outputText.add( "Initialising system...." );
        this.outputText.add( "Reticulating splines..." );
        this.outputText.add( "Connecting to network..." );
        this.outputText.add( "Some text to show this scrolling text actuall works" );


        boolean canFitGridInWindow = false;
        int sizeOffset = 0;
        while ( !canFitGridInWindow )
        {
            UnicodeFont unicodefont = Fonts.GetMonospacedFont(14 + sizeOffset);

            if (  width % unicodefont.getWidth( "a" ) == 0 && height % unicodefont.getHeight( "a" ) == 0 )
            {
                canFitGridInWindow = true;
                font = unicodefont;
            }
            sizeOffset++;
        }

        Timer cursorBlinkTimer = new Timer( 1000, e -> drawCursor = ! drawCursor );
        cursorBlinkTimer.setRepeats( true );
        cursorBlinkTimer.start();

        System.out.println( 14 + sizeOffset );
    }


    public void render( Graphics g )
    {
        renderOutputText();
        renderInputTextRow(g);
    }

    private boolean isInsideOutputTextArea(final float x, final float y)
    {
        float bottomMargin = (2 * charHeight);
        return x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height - bottomMargin;
    }

    private boolean isInsideInputTextArea(final float x, final float y)
    {
        float bottomMargin = (2 * charHeight);
        return x >= this.x && x <= this.x + width && y >= this.y + height - bottomMargin && y <= this.y + height;
    }

    private void renderOutputText()
    {
        for ( int x = 0; x < gridWidth; x++ )
        {
            for ( int y = 0; y < gridHeight; y++ )
            {
                String line = "";
                if(outputText.size() > y)
                    line = outputText.get( y );
                String character = "";
                if(x < line.length())
                    character = String.valueOf( line.toCharArray()[x] );


                final float actualX = this.x + ( x * charWidth );
                final float actualY = this.y + ( y * charHeight ) + ( outputScrollOffset * charHeight );

                if(isInsideOutputTextArea( actualX, actualY))
                    font.drawString( actualX, actualY, character);
            }
        }
    }

    private void renderInputTextRow(Graphics g)
    {
        final String bottomRow = inputPrefix + inputText;

        for( int x = 0 ; x < bottomRow.length() + 1; x++)
        {
            final float actualX = this.x + ( x * charWidth );
            final float actualY = this.y + ( gridHeight * charHeight );

            boolean isAtCursorPosition = x - inputPrefix.length() == cursorOffset;

            String charToDraw = x < inputPrefix.length() ? String.valueOf( inputPrefix.charAt( x ) ) : null;
            if(charToDraw == null)
                charToDraw = x + inputTextOffset < inputPrefix.length() + inputText.length() ? String.valueOf( inputText.charAt( (x - inputPrefix.length() + inputTextOffset) ) ) : null;

            if(isAtCursorPosition && drawCursor)
            {
                g.draw( new Rectangle( actualX, actualY, charWidth, charHeight ) );
                g.fill( new Rectangle( actualX, actualY, charWidth - 1, charHeight - 1 ), new ShapeFill()
                {
                    public Color colorAt( Shape shape, float x, float y )
                    {
                        return Color.white;
                    }
                    public Vector2f getOffsetAt( Shape shape, float x, float y )
                    {
                        return new Vector2f( 0, 1 );
                    }
                } );

                if(charToDraw != null && isInsideInputTextArea( actualX, actualY ))
                    font.drawString( actualX, actualY, charToDraw, Color.black );
            }
            else
            {
                if(charToDraw != null && isInsideInputTextArea( actualX, actualY))
                    font.drawString( actualX, actualY, charToDraw );
            }
        }
    }

    public void update()
    {
        this.charWidth = font.getWidth( "a" );
        this.charHeight = font.getHeight( "a" );
        this.gridWidth = width / charWidth;
        this.gridHeight = (height / charHeight) - 1;

        if( ShapeUtil.isContaineeInsideContainer( new Rectangle( this.x, this.y, this.width, this.height ),
                Application.MOUSE_CURSOR.mouseHitbox))
        {
            if(!isListeningToMouseScroll)
            {
                Application.MOUSE_CURSOR.addScrollListener( this );
                isListeningToMouseScroll = true;
            }
        }
        else
        {
            if(isListeningToMouseScroll)
            {
                Application.MOUSE_CURSOR.removeScrollListener( this );
                isListeningToMouseScroll = false;
            }
        }
    }

    private void incrementCursorOffset()
    {
        if(cursorOffset < inputText.length() - inputTextOffset && cursorOffset < gridWidth - inputPrefix.length())
        {
            cursorOffset++;
        }
        else if (cursorOffset == (gridWidth - inputPrefix.length()) && inputPrefix.length() + inputText.length() - inputTextOffset > gridWidth)
        {
            inputTextOffset++;
        }
    }

    public String GetTitleHeader()
    {
        return "Command Prompt";
    }

    public void setX( float x )
    {
        this.x = x + 5;
    }

    public void setY( float y )
    {
        this.y = y + 5;
    }

    public void setHeight( float height )
    {
        this.height = (int) height - 10;
    }

    public void setWidth( float width )
    {
        this.width = (int) width - 10;
    }

    public void keyPressed( int key, char c )
    {
        if( Character.isLetterOrDigit( c ) || c == ' ')
        {
            String beforeCursor = inputText.substring( 0, cursorOffset + inputTextOffset );
            String afterCursor = cursorOffset < inputText.length() ?
                    inputText.substring( cursorOffset + inputTextOffset, inputText.length() ) : "";

            inputText =  beforeCursor + c + afterCursor;
            incrementCursorOffset();
        }
        else if(key == Input.KEY_BACK)
        {
            if(this.inputText.length() > 0)
            {
                if(cursorOffset > 0)
                {
                    String beforeCursor = inputText.substring( 0, cursorOffset + inputTextOffset - 1 );
                    String afterCursor = cursorOffset < inputText.length() ?
                            inputText.substring( cursorOffset + inputTextOffset, inputText.length() ) : "";

                    inputText = beforeCursor + afterCursor;
                    cursorOffset--;
                }
            }
        }
        else if (key == Input.KEY_DELETE)
        {
            if(cursorOffset < inputText.length())
            {
                String beforeCursor = inputText.substring( 0, cursorOffset );
                String afterCursor = cursorOffset < inputText.length() ?
                        this.inputText.substring( cursorOffset + 1, inputText.length() ) : "";

                inputText = beforeCursor + afterCursor;
            }
        }
        else if (key == Input.KEY_LEFT)
        {
            if(cursorOffset > 0)
            {
                cursorOffset--;
            }
            else if (inputTextOffset > 0)
            {
                inputTextOffset--;
            }
            drawCursor = true;
        }
        else if (key == Input.KEY_RIGHT)
        {
            incrementCursorOffset();
            drawCursor = true;
        }
        else if (key == Input.KEY_HOME)
        {
            cursorOffset = 0;
            inputTextOffset = 0;
            drawCursor = true;
        }
        else if (key == Input.KEY_END)
        {
            cursorOffset = inputText.length() > gridWidth - inputPrefix.length() ? gridWidth - inputPrefix.length() : inputText.length();

            inputTextOffset = inputText.length() - (gridWidth - inputPrefix.length());

            //There's probably a better way to do this, but this stops the above inputTextOffset setting
            // from causing errors when calling render() when the text isn't long enough to need inputTextOffset being set.
            if(inputTextOffset < 0)
                inputTextOffset = 0;

            drawCursor = true;
        }
    }

    public void keyReleased( int key, char c )
    {

    }

    public void setInput( Input input )
    {

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

    public void ScrollChanged( int change )
    {
        if ( change > 0 )
        {
            if(outputScrollOffset > -outputText.size())
            {
                outputScrollOffset--;
            }
        }
        else if ( change < 0 )
        {
            if(outputScrollOffset <= 0  )
                outputScrollOffset++;
        }
    }

    @Override
    public void SetOutputText( ArrayList<String> Output )
    {
        this.outputText = Output;
    }

    @Override
    public ArrayList<String> GetOutputText()
    {
        return outputText;
    }

    @Override
    public void SetInputText( String Text )
    {
        this.inputText = Text;
    }

    @Override
    public String GetInputText()
    {
        return inputText;
    }

    @Override
    public void setInputPrefix(String text)
    {
        this.inputText = text;
    }

    @Override
    public String getInputPrefix()
    {
        return inputText;
    }
}
