package org.applicationn.search.criteria;

/**
 * Represents a search filter.
 */
public abstract class Filter
{
	/**
	 * The filter identifier, as it appears in JSON.
	 */
	private final String id;

	public Filter(String id)
	{
		this.id = id;
	}

	public final String getId()
	{
		return id;
	}

	/**
	 * Helper method that returns the attribute preceded by the table var name.
	 *
	 * @param attr The attribute
	 * @return The string &lt;table var name&gt;.&lt;attr&gt;
	 */
	protected abstract String attribute(String attr);

	/**
	 * Returns the parsed SQL condition.
	 *
	 * @return The parsed SQL
	 */
	public abstract String condition();
}
