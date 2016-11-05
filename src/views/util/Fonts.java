package views.util;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.awt.*;

/**
 * Created by Lasen on 28/10/16.
 */
public class Fonts
{
    public static UnicodeFont GetMonospacedFont(int size) throws SlickException
    {
        UnicodeFont unicodefont = new UnicodeFont( "res/FantasqueSansMono-Regular.ttf", size, false, false );
        unicodefont.getEffects().add( new ColorEffect( Color.white ) );
        unicodefont.addAsciiGlyphs();
        unicodefont.loadGlyphs();
        return unicodefont;
    }
}
