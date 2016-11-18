package world.materials;

import java.util.ArrayList;

public class Material {
	public enum Attribute {FLAMMABLE, WEAK, STRONG}
	private ArrayList<Attribute> attributes;
	protected Texture texture;
	public Material(Texture t) {
		attributes = new ArrayList<Attribute>();
		texture = t;
	}
	
	public Material(Texture t, ArrayList<Attribute> atr) {
		attributes = atr;
		texture = t;
	}
}
