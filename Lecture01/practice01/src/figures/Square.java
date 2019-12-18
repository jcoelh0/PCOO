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
public class Square extends Figure {

	public Square() {
		super(15,35,50,50);
	}

	@Override
	protected void internalDraw(Graphics g) {
		g.drawRect(15, 35, 30, 30);
	}
}
