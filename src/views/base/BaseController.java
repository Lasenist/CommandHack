package views.base;

import objects.Binding;
import views.base.interfaces.IBaseController;
import views.base.interfaces.IBaseViewModel;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Lasen on 06/11/16.
 */
public abstract class BaseController<TPropertyEnum, UViewModel extends IBaseViewModel<TPropertyEnum>> implements Observer, IBaseController
{
    private HashMap<TPropertyEnum, Binding> propertyHashMap = new HashMap<>();
    protected UViewModel viewModel;

    public BaseController(UViewModel viewModel)
    {
        this.viewModel = viewModel;
        viewModel.addObserver( this );
    }

    protected void registerProperty( TPropertyEnum propertyEnum, Binding binding )
    {
        propertyHashMap.put( propertyEnum, binding );
    }

    public void update()
    {
        propertyHashMap.values().forEach( Binding::update );
    }

    public void update( Observable o, Object arg )
    {
        TPropertyEnum propertyEnum = (TPropertyEnum) arg;

        if(propertyHashMap.containsKey( propertyEnum ))
        {
            Binding value = propertyHashMap.get( propertyEnum );
            value.setNewValue( viewModel.getProperty( propertyEnum ) );
        }
    }

    public UViewModel getViewModel()
    {
        return viewModel;
    }
}
