package views;

import org.newdawn.slick.Graphics;

/**
 * Created by Lasen on 08/09/16.
 */
public interface IComponent
{
    void render( final Graphics g );

    void update();

}
