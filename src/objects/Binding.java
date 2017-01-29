package objects;

/**
 * Created by Lasen on 06/11/16.
 */
public class Binding<TValue>
{
    private TValue value;
    private TValue newValue;

    public Binding( TValue defaultValue )
    {
        this.value = defaultValue;
        this.newValue = defaultValue;
    }

    public void setNewValue( TValue value)
    {
        this.newValue = value;
    }

    public void forceSetValue( TValue value)
    {
        setNewValue( value );
        this.value = value;
    }

    public TValue getValue()
    {
        return value;
    }

    public void update()
    {
        this.value = newValue;
    }

    public boolean hasValueChanged()
    {
        return value != newValue;
    }
}
