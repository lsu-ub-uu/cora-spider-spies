/*
 * Copyright 2024 Uppsala University Library
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
package se.uu.ub.cora.spider.spies.binary.iiif;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

import java.util.Collections;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.spider.binary.iiif.IiifResponse;
import se.uu.ub.cora.testutils.mcr.MethodCallRecorder;
import se.uu.ub.cora.testutils.mrv.MethodReturnValues;
import se.uu.ub.cora.testutils.spies.MCRSpy;

public class IiifReaderSpyTest {

	private static final String ADD_CALL = "addCall";
	private static final String ADD_CALL_AND_RETURN_FROM_MRV = "addCallAndReturnFromMRV";
	IiifReaderSpy iffReader;
	private MCRSpy MCRSpy;
	private MethodCallRecorder mcrForSpy;

	@BeforeMethod
	public void beforeMethod() {
		MCRSpy = new MCRSpy();
		mcrForSpy = MCRSpy.MCR;
		iffReader = new IiifReaderSpy();
	}

	@Test
	public void testMakeSureSpyHelpersAreSetUp() throws Exception {
		assertTrue(iffReader.MCR instanceof MethodCallRecorder);
		assertTrue(iffReader.MRV instanceof MethodReturnValues);
		assertSame(iffReader.MCR.onlyForTestGetMRV(), iffReader.MRV);
	}

	@Test
	public void testDefaultReadRecord() throws Exception {
		IiifResponse iiifResponse = iffReader.readIiif("someIdentifier", "someRequestUri",
				"someMethods", Collections.emptyMap());

		assertEquals(iiifResponse.status(), 200);
		assertEquals(iiifResponse.headers().get("content-type"), "plain/text");
		assertEquals(iiifResponse.body(), "body");
	}

	@Test
	public void testReadRecord() throws Exception {
		iffReader.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				() -> new IiifResponse(418, Map.of("someHeaderKey", "someHeaderValue"),
						"someBody"));

		IiifResponse iiifResponse = iffReader.readIiif("someIdentifier", "someRequestUri",
				"someMethods", Collections.emptyMap());

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "identifier", "someIdentifier");
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "requestedUri",
				"someRequestUri");
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "method", "someMethods");
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "headers",
				Collections.emptyMap());

		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, iiifResponse);
	}

}
