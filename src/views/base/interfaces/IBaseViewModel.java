package views.base.interfaces;

import java.util.Observer;

/**
 * Created by Lasen on 06/11/16.
 */
public interface IBaseViewModel<T>
{
    void addObserver( IBaseController baseController );
    Object getProperty(T propertyEnum);
}
