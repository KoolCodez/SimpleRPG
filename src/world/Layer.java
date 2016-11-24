package world;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import world.Things.Thing;

public class Layer implements Serializable {
	private List<Thing> things;
	
	public Layer() {
		things = new ArrayList<Thing>();
	}
	public void add(Thing thing) {
		things.add(thing);
	}
	
	public void updateThings() {
		for (Thing t : things) {
			t.physUpdate();
		}
	}
	
	public List<Thing> getThings() {
		return things;
	}
}
