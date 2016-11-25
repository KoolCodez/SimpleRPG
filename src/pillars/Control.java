package pillars;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.sun.glass.events.KeyEvent;

public class Control {
	private Display display;
	public Control(Display d) {
		display = d;
		Action ac = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Core.myThing.enactForce(40, 90);
			}
		};
		d.addAction(ac, KeyEvent.VK_A, false);
		Action ac2 = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Core.myThing.enactForce(40, 90);
				
			}
		};
		d.addAction(ac2, KeyEvent.VK_D, false);
	}
}
