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

import java.util.function.Supplier;

import se.uu.ub.cora.spider.binary.Downloader;
import se.uu.ub.cora.spider.binary.ResourceInputStream;
import se.uu.ub.cora.testutils.mcr.MethodCallRecorder;
import se.uu.ub.cora.testutils.mrv.MethodReturnValues;

public class DownloaderSpy implements Downloader {
	public MethodCallRecorder MCR = new MethodCallRecorder();
	public MethodReturnValues MRV = new MethodReturnValues();
	private ResourceInputStream resourceInputStream = ResourceInputStream
			.withNameSizeInputStream(null, 0, null, null);

	public DownloaderSpy() {
		MCR.useMRV(MRV);
		MRV.setDefaultReturnValuesSupplier("download",
				(Supplier<ResourceInputStream>) () -> resourceInputStream);
	}

	@Override
	public ResourceInputStream download(String authToken, String type, String id, String resource) {
		return (ResourceInputStream) MCR.addCallAndReturnFromMRV("authToken", authToken, "type",
				type, "id", id, "resource", resource);
	}
}
