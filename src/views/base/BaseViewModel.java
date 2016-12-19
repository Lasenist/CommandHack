package views.base;

import objects.Property;
import views.base.interfaces.IBaseController;
import views.base.interfaces.IBaseViewModel;

import java.util.HashMap;
import java.util.Observer;

/**
 * Created by Lasen on 07/11/16.
 * Base class for view models
 */
public class BaseViewModel<T> implements IBaseViewModel<T>
{
    private HashMap<T, Property> propertyHashMap = new HashMap<>();

    protected void registerProperty( T propertyEnum, Property property )
    {
        propertyHashMap.put( propertyEnum, property );
    }

    public void addObserver( IBaseController o )
    {
        for( Property value : propertyHashMap.values() )
        {
            value.addObserver( o );
        }
    }

    @Override
    public Object getProperty( T propertyEnum )
    {
        return propertyHashMap.get( propertyEnum ).getValue();
    }
}
