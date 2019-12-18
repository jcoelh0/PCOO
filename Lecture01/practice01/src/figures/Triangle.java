/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package figures;

import java.awt.Graphics;

/**
 *
 * @author jcoel
 */
public class Triangle extends Figure {

	public Triangle() {
		super(35,35,0,0);
	}

	@Override
	protected void internalDraw(Graphics g) {
		g.drawLine(35, 35, 40, 50);
		g.drawLine(40, 50, 45, 35);
		g.drawLine(45, 35, 35, 35);
	}

}
