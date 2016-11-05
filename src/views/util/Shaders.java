package views.util;

import org.newdawn.slick.SlickException;

/**
 * Created by Lasen on 29/10/16.
 */
public class Shaders
{
    private static ShaderProgram invertShader;

    public static ShaderProgram getInvertShader()
    {
        try
        {
            if(invertShader == null)
            {
                invertShader = ShaderProgram.loadProgram( "res/invert.vert", "res/invert.frag" );
                invertShader.bind();
                invertShader.setUniform1f( "tex0", 0 );
                invertShader.unbind();
            }
        }
        catch ( SlickException e )
        {
            e.printStackTrace();
        }
        return invertShader;
    }
}
