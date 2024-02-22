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

import se.uu.ub.cora.spider.binary.iiif.IiifReader;
import se.uu.ub.cora.spider.data.ResourceInputStream;
import se.uu.ub.cora.testutils.mcr.MethodCallRecorder;
import se.uu.ub.cora.testutils.mrv.MethodReturnValues;

public class IiifReaderSpy implements IiifReader {

	public MethodCallRecorder MCR = new MethodCallRecorder();
	public MethodReturnValues MRV = new MethodReturnValues();

	public IiifReaderSpy() {
		MCR.useMRV(MRV);
		MRV.setDefaultReturnValuesSupplier("readyBinary",
				() -> ResourceInputStream.withNameSizeInputStream(null, 0, null, null));
	}

	@Override
	public ResourceInputStream readImage(String identifier, String region, String size,
			String rotation, String quality, String format) {
		return (ResourceInputStream) MCR.addCallAndReturnFromMRV("identifier", identifier, "region",
				region, "size", size, "rotation", rotation, "quality", quality, "format", format);
	}
}
