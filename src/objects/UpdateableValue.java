package objects;

/**
 * Created by Lasen on 06/11/16.
 */
public class UpdateableValue<T>
{
    private T value;
    private Enum name;
    private T newValue;

    public UpdateableValue(T defaultValue, Enum name)
    {
        this.value = defaultValue;
        this.name = name;
    }

    public void setValue(T newValue)
    {
        this.newValue = newValue;
    }

    public T getValue()
    {
        return value;
    }

    public void update()
    {
        this.value = newValue;
    }

}
