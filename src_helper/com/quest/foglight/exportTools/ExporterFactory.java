package com.quest.foglight.exportTools;

import com.quest.foglight.exportTools.interfaces.CartridgeExporter;
import com.quest.nitro.model.topology.TopologyObject;

public class ExporterFactory {

	
	public static CartridgeExporter createCartridgeExporter(TopologyObject carDefinition) {
		return new CartridgeExporterImpl(carDefinition);
	}
}
