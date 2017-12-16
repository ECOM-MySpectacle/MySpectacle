package org.applicationn.search.criteria;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a search filter.
 */
public abstract class Filter
{
	/**
	 * The filter identifier, as it appears in JSON.
	 */
	private final String id;
	/**
	 * The variables.
	 */
	private final Map<String, Object> vars = new HashMap<>();

	public Filter(String id)
	{
		this.id = id;
	}

	public final String getId()
	{
		return id;
	}

	/**
	 * @return the table var name.
	 */
	protected abstract String tableVarName();

	/**
	 * Returns the parsed SQL condition.
	 *
	 * @return The parsed SQL
	 */
	public abstract String condition();

	private String varName(String var)
	{
		return tableVarName() + "_" + var;
	}

	/**
	 * Helper method that returns the attribute preceded by the table var name.
	 *
	 * @param attr The attribute
	 * @return The string &lt;table var name&gt;.&lt;attr&gt;
	 */
	protected String attribute(String attr)
	{
		return tableVarName() + "." + attr;
	}

	protected String lower(String attr)
	{
		return "LOWER(" + attribute(attr) + ")";
	}

	/**
	 * Helper method that returns the full variable name {@code :<varName>}.
	 *
	 * @param var the name
	 * @return The full variable name
	 */
	protected String variable(String var)
	{
		return ":" + varName(var);
	}

	/**
	 * Set var {@code param} to {@code var}.
	 *
	 * @param param the name
	 * @param var   the object
	 */
	protected final void setVar(String param, Object var)
	{
		vars.put(varName(param), var);
	}

	public final Map<String, Object> getVars()
	{
		return vars;
	}
}
