package objects;

import java.util.Observable;

/**
 * Created by Lasen on 05/11/16.
 * Holds a value and notifies observers
 */
public class ObservableValue<T> extends Observable
{
    private T value;
    private Enum name;

    public ObservableValue( T defaultValue, Enum Name )
    {
        this.value = defaultValue;
        this.name = Name;
    }

    public void setValue(T value)
    {
        this.value = value;
        setChanged();
        notifyObservers(name);
    }

    public T getValue()
    {
        return value;
    }
}
