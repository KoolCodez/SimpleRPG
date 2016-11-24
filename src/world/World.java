package world;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import Utility.PointMap;
import Utility.TextureUtil;
import world.Things.Thing;

public class World {
	
	private PointMap<Chunk> chunks;
	public static final int WORLD_DEPTH = 5;
	public static final int MILLIS_PER_TICK = 20;
	public Thread ticker;
	
	public World() {
		chunks = new PointMap<Chunk>();
		createTicker();
	}
	
	public void addThing(int x, int y, Thing thing, int layer) {
		Chunk c = chunks.get(x, y);
		if (c == null) {
			c = new Chunk();
			chunks.put(x, y,  c);
		}
		c.addToLayer(thing, layer);
	}
	
	private void createTicker() {
		World world = this;
		ticker = new Thread() {
			@Override
			public void run() {
				while (true) {
					synchronized (world) {
						world.notifyAll();
					}
					updatePhysics();
					try {
						sleep(MILLIS_PER_TICK);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		ticker.start();
	}
	
	public void updatePhysics() {
		List<Thing> things = getAllThings();
		for (Thing t : things) {
			t.physUpdate();
		}
	}
	
	public List<Thing> getAllThings() {
		List<Thing> thingsList = new ArrayList<>();
		List<Chunk> chunkList = chunks.getAllEntries();
		for (Chunk c : chunkList) {
			for (int i = 0; i < WORLD_DEPTH; i++) {
				List<Thing> things = c.getThingsInLayer(i);
				thingsList.addAll(things);
			}
		}
		return thingsList;
	}
	
	public boolean checkAllCollisions(Thing thing, int layer) {
		List<Chunk> chunkList = chunks.getAllEntries();
		for (Chunk c : chunkList) {
			List<Thing> things = c.getThingsInLayer(layer);
			for (Thing temp : things) {
				if (intersects(thing, temp)) {
					return false;
				}
			}
		}
		return true;
	}
	public boolean intersects(Thing obj1, Thing obj2) {
		boolean intersects = false;
		Rectangle2D rect1 = obj1.getRect();
		Rectangle2D rect2 = obj2.getRect();
		if (rect1.intersects(rect2)) {
			return intersectsSmallScale(obj1, obj2);
		} else {
			return false;
		}
	}

	private boolean intersectsSmallScale(Thing obj1, Thing obj2) {
		boolean[][] arr1 = TextureUtil.getVisiblePixels(obj1.getImage());
		boolean[][] arr2 = TextureUtil.getVisiblePixels(obj2.getImage());
		int x1 = obj1.getPoint().x;
		int x2 = obj2.getPoint().x;
		int y1 = obj1.getPoint().y;
		int y2 = obj2.getPoint().y;
		int xMax = Math.max(arr1.length + x1, arr2.length + x2);
		int yMax = Math.max(arr1[0].length + y1, arr2[0].length + y2);
		int xMin = Math.min(x1, x2);
		int yMin = Math.min(y1,  y2);
		for (int x = xMin; x < xMax; x++) {
			for (int y = yMin; y < yMax; y++) {
				boolean inter1 = false;
				boolean inter2 = false;
				if (x - x1 > 0 && x - x1 < arr1.length && 
						y - y1 > 0 && y - y1 < arr1[0].length) {
					inter1 = arr1[x - x1][y - y1];
				}
				if (x - x2 > 0 && x - x2 < arr2.length && 
						y - y2 > 0 && y - y2 < arr2[0].length) {
					inter2 = arr2[x - x2][y - y2];
				}
				if (inter1 && inter2) {
					return true;
				}
			}
		}
		return false;
	}
}
