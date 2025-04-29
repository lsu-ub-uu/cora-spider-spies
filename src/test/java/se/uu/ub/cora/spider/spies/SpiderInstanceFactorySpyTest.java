/*
 * Copyright 2022 Olov McKie
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
package se.uu.ub.cora.spider.spies;

import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.spider.record.IncomingLinksReader;
import se.uu.ub.cora.spider.record.RecordListReader;
import se.uu.ub.cora.spider.spies.binary.iiif.IiifReaderSpy;
import se.uu.ub.cora.testutils.mcr.MethodCallRecorder;
import se.uu.ub.cora.testutils.mrv.MethodReturnValues;
import se.uu.ub.cora.testutils.spies.MCRSpy;

public class SpiderInstanceFactorySpyTest {
	private static final String ADD_CALL = "addCall";
	private static final String ADD_CALL_AND_RETURN_FROM_MRV = "addCallAndReturnFromMRV";
	SpiderInstanceFactorySpy instanceFactory;
	private MCRSpy MCRSpy;
	private MethodCallRecorder mcrForSpy;

	@BeforeMethod
	public void beforeMethod() {
		MCRSpy = new MCRSpy();
		mcrForSpy = MCRSpy.MCR;
		instanceFactory = new SpiderInstanceFactorySpy();
	}

	@Test
	public void testMakeSureSpyHelpersAreSetUp() {
		assertTrue(instanceFactory.MCR instanceof MethodCallRecorder);
		assertTrue(instanceFactory.MRV instanceof MethodReturnValues);
		assertSame(instanceFactory.MCR.onlyForTestGetMRV(), instanceFactory.MRV);
	}

	@Test
	public void testDefaultDependencyProviderClassName() {
		assertTrue(instanceFactory.getDependencyProviderClassName() instanceof String);
	}

	@Test
	public void testGetDependencyProviderClassName() {
		instanceFactory.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, String::new);

		String returnedValue = instanceFactory.getDependencyProviderClassName();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultFactorRecordReader() {
		assertTrue(instanceFactory.factorRecordReader() instanceof RecordReaderSpy);
	}

	@Test
	public void testFactorRecordReader() {
		instanceFactory.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				RecordReaderSpy::new);

		var returnedValue = instanceFactory.factorRecordReader();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultFactorDecoratedRecordReader() {
		assertTrue(
				instanceFactory.factorDecoratedRecordReader() instanceof DecoratedRecordReaderSpy);
	}

	@Test
	public void testFactorDecoratedRecordReader() {
		instanceFactory.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				RecordReaderSpy::new);

		var returnedValue = instanceFactory.factorRecordReader();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultFactorIncomingLinksReader() {
		assertTrue(instanceFactory.factorIncomingLinksReader() instanceof IncomingLinksReader);
	}

	@Test
	public void testFactorIncomingLinksReader() {
		instanceFactory.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				IncomingLinksReaderSpy::new);

		var returnedValue = instanceFactory.factorIncomingLinksReader();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultFactorRecordListReader() {
		assertTrue(instanceFactory.factorRecordListReader() instanceof RecordListReader);
	}

	@Test
	public void testFactorRecordListReader() {
		instanceFactory.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				RecordListReaderSpy::new);

		var returnedValue = instanceFactory.factorRecordListReader();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultFactorRecordCreator() {
		assertTrue(instanceFactory.factorRecordCreator() instanceof RecordCreatorSpy);
	}

	@Test
	public void testFactorRecordCreator() {
		instanceFactory.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				RecordCreatorSpy::new);

		var returnedValue = instanceFactory.factorRecordCreator();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultFactorRecordUpdater() {
		assertTrue(instanceFactory.factorRecordUpdater() instanceof RecordUpdaterSpy);
	}

	@Test
	public void testFactorRecordUpdater() {
		instanceFactory.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				RecordUpdaterSpy::new);

		var returnedValue = instanceFactory.factorRecordUpdater();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultFactorRecordDeleter() {
		assertTrue(instanceFactory.factorRecordDeleter() instanceof RecordDeleterSpy);
	}

	@Test
	public void testFactorRecordDeleter() {
		instanceFactory.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				RecordDeleterSpy::new);

		var returnedValue = instanceFactory.factorRecordDeleter();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultFactorUploader() {
		assertTrue(instanceFactory.factorUploader() instanceof UploaderSpy);
	}

	@Test
	public void testFactorUploader() {
		instanceFactory.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, UploaderSpy::new);

		var returnedValue = instanceFactory.factorUploader();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultFactorDownloader() {
		assertTrue(instanceFactory.factorDownloader() instanceof DownloaderSpy);
	}

	@Test
	public void testFactorDownloader() {
		instanceFactory.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, DownloaderSpy::new);

		var returnedValue = instanceFactory.factorDownloader();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultFactorRecordSearcher() {
		assertTrue(instanceFactory.factorRecordSearcher() instanceof RecordSearcherSpy);
	}

	@Test
	public void testFactorRecordSearcher() {
		instanceFactory.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				RecordSearcherSpy::new);

		var returnedValue = instanceFactory.factorRecordSearcher();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultFactorRecordValidator() {
		assertTrue(instanceFactory.factorRecordValidator() instanceof RecordValidatorSpy);
	}

	@Test
	public void testFactorRecordValidator() {
		instanceFactory.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				RecordValidatorSpy::new);

		var returnedValue = instanceFactory.factorRecordValidator();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultFactorRecordListIndexer() {
		assertTrue(instanceFactory.factorRecordListIndexer() instanceof RecordListIndexerSpy);
	}

	@Test
	public void testFactorRecordListIndexer() {
		instanceFactory.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				RecordListIndexerSpy::new);

		var returnedValue = instanceFactory.factorRecordListIndexer();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultFactorIffReader() {
		assertTrue(instanceFactory.factorIiifReader() instanceof IiifReaderSpy);
	}

	@Test
	public void testFactorIffReader() {
		instanceFactory.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, IiifReaderSpy::new);

		var returnedValue = instanceFactory.factorIiifReader();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}
}
