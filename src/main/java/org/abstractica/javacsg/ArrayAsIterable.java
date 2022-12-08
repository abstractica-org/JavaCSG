package org.abstractica.javacsg;

public class ArrayAsIterable
{
	public static <T> Iterable<T> asIterable(final T[] array)
	{
		return new Iterable<T>()
		{
			@Override
			public java.util.Iterator<T> iterator()
			{
				return new java.util.Iterator<T>()
				{
					private int index = 0;

					@Override
					public boolean hasNext()
					{
						return index < array.length;
					}

					@Override
					public T next()
					{
						return array[index++];
					}

					@Override
					public void remove()
					{
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}
}
