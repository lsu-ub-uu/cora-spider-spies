/*
 * Copyright 2022 Olov McKie
 *
 * This file is part of Cora.
 *
 *     Cora is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Cora is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Cora.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.uu.ub.cora.spider.spies;

import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.data.DataList;
import se.uu.ub.cora.data.spies.DataListSpy;
import se.uu.ub.cora.testutils.mcr.MethodCallRecorder;
import se.uu.ub.cora.testutils.mrv.MethodReturnValues;
import se.uu.ub.cora.testutils.spies.MCRSpy;

public class IncomingLinksReaderSpyTest {

	private static final String ADD_CALL = "addCall";
	private static final String ADD_CALL_AND_RETURN_FROM_MRV = "addCallAndReturnFromMRV";
	IncomingLinksReaderSpy incomingLinksReader;
	private MCRSpy MCRSpy;
	private MethodCallRecorder mcrForSpy;

	@BeforeMethod
	public void beforeMethod() {
		MCRSpy = new MCRSpy();
		mcrForSpy = MCRSpy.MCR;
		incomingLinksReader = new IncomingLinksReaderSpy();
	}

	@Test
	public void testMakeSureSpyHelpersAreSetUp() throws Exception {
		assertTrue(incomingLinksReader.MCR instanceof MethodCallRecorder);
		assertTrue(incomingLinksReader.MRV instanceof MethodReturnValues);
		assertSame(incomingLinksReader.MCR.onlyForTestGetMRV(), incomingLinksReader.MRV);
	}

	@Test
	public void testDefaultReadIncomingLinks() throws Exception {
		assertTrue(incomingLinksReader.readIncomingLinks("authToken", "type",
				"id") instanceof DataListSpy);
	}

	@Test
	public void testReadIncomingLinks() throws Exception {
		incomingLinksReader.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, DataListSpy::new);

		DataList retunedValue = incomingLinksReader.readIncomingLinks("authToken", "type", "id");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "authToken", "authToken");
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "type", "type");
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "id", "id");
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

}
