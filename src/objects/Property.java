package objects;

import java.util.Observable;

/**
 * Created by Lasen on 05/11/16.
 * Holds a value and notifies observers
 */
public class Property<TValue, UPropertyEnum> extends Observable
{
    private TValue value;
    private UPropertyEnum propertyEnum;

    public Property( TValue defaultValue, UPropertyEnum propertyEnum )
    {
        this.value = defaultValue;
        this.propertyEnum = propertyEnum;
    }

    public void setValue( TValue value)
    {
        this.value = value;
        setChanged();
        notifyObservers(propertyEnum);
    }

    public TValue getValue()
    {
        return value;
    }

    public UPropertyEnum getPropertyEnum()
    {
        return propertyEnum;
    }
}
