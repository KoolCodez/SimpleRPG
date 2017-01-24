package pillars;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.sun.glass.events.KeyEvent;

import graphics.Display;

public class Control {
	private Display display;
	boolean pressed = false;
	public Control(Display d) {
		display = d;
		Action ac = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!pressed) {
					pressed = true;
					Core.myThing.enactForce(40, 90);
				}
			}
		};
		d.addAction(ac, KeyEvent.VK_A, false);
		
		Action acS = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pressed = false;
				
			}
		};
		d.addAction(acS, KeyEvent.VK_A, true);
		
		Action ac2 = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!pressed) {
					pressed = true;
					Core.myThing.enactForce(40, 90);
				}
			}
		};
		d.addAction(ac2, KeyEvent.VK_D, false);
		
		Action acS2 = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pressed = false;
				
			}
		};
		d.addAction(acS2, KeyEvent.VK_D, true);
	}
}
