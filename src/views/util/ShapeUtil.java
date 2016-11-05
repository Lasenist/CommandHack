package views.util;

import org.newdawn.slick.geom.Shape;

/**
 * Created by Lasen on 16/10/16.
 */
public class ShapeUtil
{
    public static boolean isContaineeInsideContainer( final Shape container, final Shape containee )
    {
        for ( int i = 0; i < containee.getPointCount(); i++ )
        {
            float[] pt = containee.getPoint( i );
            if ( ! container.contains( pt[0], pt[1] ) )
            {
                return false;
            }
        }
        return true;
    }
}
