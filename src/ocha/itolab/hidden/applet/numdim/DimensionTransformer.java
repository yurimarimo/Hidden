package ocha.itolab.hidden.applet.numdim;

import java.lang.Math;



public class DimensionTransformer {

	double viewShift[] = new double[3];
	double viewRotate[] = new double[16];
	double viewScaleX, viewScaleY;
	double viewShiftBak[] = new double[3];
	double viewScaleBakX, viewScaleBakY;
	double Xrotate, Yrotate, XrotateBak, YrotateBak;

	double tableMin[] = new double[3];
	double tableMax[] = new double[3];
	double tableCenter[] = new double[3];
	double tableSize;

	double shiftX, shiftZ;

	/**
	 * Constructor
	 */
	public DimensionTransformer() {
		viewReset();
	}

	public void viewReset() {
		for (int i = 0; i < 16; i++) {
			if (i % 5 == 0)
				viewRotate[i] = 1.0;
			else
				viewRotate[i] = 0.0;
		}
		viewScaleX = viewScaleBakX = 1.0;
		viewScaleY = viewScaleBakY = 1.0;
		viewShift[0] = viewShiftBak[0] = 0.0;
		viewShift[1] = viewShiftBak[1] = 0.0;
		viewShift[2] = viewShiftBak[2] = 0.0;
		Xrotate = XrotateBak = 0.0;
		Yrotate = YrotateBak = 0.0;

	}



	public void mousePressed() {
		viewScaleBakX = viewScaleX;
		viewScaleBakY = viewScaleY;
		viewShiftBak[0] = viewShift[0];
		viewShiftBak[1] = viewShift[1];
		viewShiftBak[2] = viewShift[2];
		XrotateBak = Xrotate;
		YrotateBak = Yrotate;
	}


	public void drag(int x, int y, int width, int height, int dragMode) {
		
		if (dragMode == 1) { // ZOOM
			
			if (x > 0) {
				viewScaleX =
					viewScaleBakX * (1 + (double) (2 * x) / (double) width);
			} else {
				viewScaleX = viewScaleBakX * (1 + (double) x / (double) width);
			}
			if (viewScaleX < 0.2)
				viewScaleX = 0.2;
			
			if (y > 0) {
				viewScaleY =
					viewScaleBakY * (1 + (double) (2 * y) / (double) height);
			} else {
				viewScaleY = viewScaleBakY * (1 + (double) y / (double) height);
			}
			if (viewScaleY < 0.2)
				viewScaleY = 0.2;

			viewShift[0] = viewShiftBak[0] * viewScaleX / viewScaleBakX;
			viewShift[1] = viewShiftBak[1] * viewScaleY / viewScaleBakY;

		}
		if (dragMode == 2) { // SHIFT
			
			 float diffX = (float)x * 3.0f / width;
             float diffY = (-3.0f) * (float)y / height;
           
            viewShift[0] = viewShiftBak[0] + diffX;
 			viewShift[1] = viewShiftBak[1] + diffY;
		}
		if (dragMode == 3) { // ROTATE
			Xrotate = XrotateBak + (double) x * Math.PI / (double) width;
			Yrotate = YrotateBak + (double) y * Math.PI / (double) height;
			double cosX = Math.cos(Yrotate);
			double sinX = Math.sin(Yrotate);
			double cosY = Math.cos(Xrotate);
			double sinY = Math.sin(Xrotate);

			viewRotate[0] = cosY;
			viewRotate[1] = 0;
			viewRotate[2] = -sinY;
			viewRotate[4] = sinX * sinY;
			viewRotate[5] = cosX;
			viewRotate[6] = sinX * cosY;
			viewRotate[8] = cosX * sinY;
			viewRotate[9] = -sinX;
			viewRotate[10] = cosX * cosY;
		}

	}

	
	public double getTableSize() {
		return tableSize;
	}
	

	public void setTableSize(double g) {
		tableSize = g;
	}


	public double getViewScaleX() {
		return viewScaleX;
	}
	
	public double getViewScaleY() {
		return viewScaleY;
	}
	

	public void setViewScaleX(double v) {
		viewScaleX = v;
	}
	

	public void setViewScaleY(double v) {
		viewScaleY = v;
	}


	public double getTableCenter(int i) {
		return tableCenter[i];
	}
	

	public void setTableCenter(double g, int i) {
		tableCenter[i] = g;
	}


	public double getViewRotate(int i) {
		return viewRotate[i];
	}
	

        public void setViewRotate(double v, int i) {
	    viewRotate[i] = v;
	}


	public double getViewShift(int i) {
		return viewShift[i];
	}
	
	public void setViewShift(double v, int i) {
		viewShift[i] = v;
	}


	public double getViewRotateX() {
		return Xrotate;
	}
	
	public double getViewRotateY() {
		return Yrotate;
	}
	

	public double getCenter(int i) {
		return tableCenter[i];
	}
	
	public double getSize() {
		return tableSize;
	}
}
