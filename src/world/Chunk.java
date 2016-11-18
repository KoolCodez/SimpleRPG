package world;

import java.io.Serializable;
import java.util.List;

import world.Things.Thing;

public class Chunk implements Serializable {
	private Layer[] layers;
	public Chunk() {
		layers = new Layer[World.WORLD_DEPTH];
	}
	
	public void addToLayer(Thing thing, int layer) {
		layers[layer].add(thing);
	}
	
	public void updateLayers() {
		for (Layer l : layers) {
			l.updateThings();
		}
	}
	
	public List<Thing> getThingsInLayer(int layer) {
		return layers[layer].getThings();
	}
}
