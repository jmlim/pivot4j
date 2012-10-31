/*
 * ====================================================================
 * This software is subject to the terms of the Common Public License
 * Agreement, available at the following URL:
 *   http://www.opensource.org/licenses/cpl.html .
 * You must accept the terms of that agreement to use this software.
 * ====================================================================
 */
package com.eyeq.pivot4j.transform.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.eyeq.pivot4j.transform.NonEmpty;

public class NonEmptyImplIT extends AbstractTransformTestCase<NonEmpty> {

	private String initialQuery = "SELECT {[Measures].[Unit Sales], [Measures].[Store Cost], [Measures].[Store Sales]} ON COLUMNS, "
			+ "{([Promotion Media].[All Media], [Product].[All Products])} ON ROWS FROM [Sales]";

	/**
	 * @return the initialQuery
	 * @see com.eyeq.pivot4j.transform.impl.AbstractTransformTestCase#getInitialQuery()
	 */
	protected String getInitialQuery() {
		return initialQuery;
	}

	/**
	 * @see com.eyeq.pivot4j.transform.impl.AbstractTransformTestCase#getType()
	 */
	@Override
	protected Class<NonEmpty> getType() {
		return NonEmpty.class;
	}

	@Test
	public void testTransform() {
		NonEmpty transform = getTransform();

		assertFalse("Initial query does not include NON EMPTY statement",
				transform.isNonEmpty());

		transform.setNonEmpty(true);

		assertTrue("Query does contain NON EMPTY statement",
				transform.isNonEmpty());
		assertEquals(
				"Unexpected MDX query after set NON EMPTY statement",
				"SELECT NON EMPTY {[Measures].[Unit Sales], [Measures].[Store Cost], [Measures].[Store Sales]} ON COLUMNS, "
						+ "NON EMPTY {([Promotion Media].[All Media], [Product].[All Products])} ON ROWS "
						+ "FROM [Sales]", getPivotModel().getCurrentMdx());

		getPivotModel().getCellSet();

		transform.setNonEmpty(false);

		assertFalse("Query does not contain NON EMPTY statement",
				transform.isNonEmpty());

		assertEquals("Unexpected MDX query after removing NON EMPTY statement",
				getInitialQuery(), getPivotModel().getCurrentMdx());
	}
}