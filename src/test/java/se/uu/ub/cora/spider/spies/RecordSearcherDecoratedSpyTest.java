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
import se.uu.ub.cora.data.spies.DataGroupSpy;
import se.uu.ub.cora.data.spies.DataListSpy;
import se.uu.ub.cora.spider.record.RecordSearcherDecorated;
import se.uu.ub.cora.testutils.mcr.MethodCallRecorder;
import se.uu.ub.cora.testutils.mrv.MethodReturnValues;
import se.uu.ub.cora.testutils.spies.MCRSpy;

public class RecordSearcherDecoratedSpyTest {
	private static final String ADD_CALL_AND_RETURN_FROM_MRV = "addCallAndReturnFromMRV";
	RecordSearcherDecoratedSpy recordSearcher;
	private MCRSpy MCRSpy;
	private MethodCallRecorder mcrForSpy;

	@BeforeMethod
	public void beforeMethod() {
		MCRSpy = new MCRSpy();
		mcrForSpy = MCRSpy.MCR;
		recordSearcher = new RecordSearcherDecoratedSpy();
	}

	@Test
	public void testName() {
		assertTrue(recordSearcher instanceof RecordSearcherDecorated);
	}

	@Test
	public void testMakeSureSpyHelpersAreSetUp() {
		assertTrue(recordSearcher.MCR instanceof MethodCallRecorder);
		assertTrue(recordSearcher.MRV instanceof MethodReturnValues);
		assertSame(recordSearcher.MCR.onlyForTestGetMRV(), recordSearcher.MRV);
	}

	@Test
	public void testDefaultSearchRecord() {
		assertTrue(recordSearcher.searchDecorated("authToken", "searchId",
				null) instanceof DataListSpy);
	}

	@Test
	public void testSearchRecord() {
		recordSearcher.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, DataListSpy::new);
		DataGroupSpy searchData = new DataGroupSpy();

		DataList retunedValue = recordSearcher.searchDecorated("authToken", "searchId", searchData);

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "authToken", "authToken");
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "searchId", "searchId");
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "searchData", searchData);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

}
