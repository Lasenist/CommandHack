package objects;

/**
 * Created by Lasen on 07/11/16.
 */
public abstract class ConverterBinding<TValue, UConvertedValue> extends Binding<TValue>
{
    protected UConvertedValue convertedValue;
    protected Boolean isConvertedValueInvalid;

    public ConverterBinding( TValue defaultValue )
    {
        super( defaultValue );
    }

    public abstract UConvertedValue getConvertedValue();

    public void setNewValue( TValue value )
    {
        super.setNewValue( value );
        isConvertedValueInvalid = true;
    }
}
