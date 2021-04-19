package ocha.itolab.hidden.core.data;

import java.util.*;
import ocha.itolab.hidden.core.tool.*;

public class IndividualSet {
	int numTotal, numNumeric, numCategory, numBoolean, classId, dimselectMode;
	String name[];
	public String filename;
	public String message;
	int type[];
	public ArrayList<OneIndividual> plots;
	public NumericSet numerics;
	public CategorySet categories;
	public BooleanSet booleans;
	
	public static int TYPE_NUMERIC = 1;
	public static int TYPE_CATEGORY = 2;
	public static int TYPE_BOOLEAN = 3;
	
	public static int DIMSELECT_CORRELATION = 1;
	public static int DIMSELECT_CLASS_PURELITY = 2;
	
	
	
	/**
	 * Constructor
	 */
	public IndividualSet(int numn, int numc, int numb, int id) {
		numNumeric = numn;
		numCategory = numc;
		numBoolean = numb;
		numTotal = numn + numc + numb;
		classId = id;
		dimselectMode = DIMSELECT_CORRELATION;
		name = new String[numTotal];
		type = new int[numTotal];
		plots = new ArrayList();
	}
	
	
	public int getNumNumeric() {
		return numNumeric;
	}
	

	public int getNumCategory() {
		return numCategory;
	}

    
	public int getNumBoolean() {
		return numBoolean;
	}

    
	public int getNumTotal() {
		return numTotal;
	}
	

	public void setValueName(int i, String n) {
		name[i] = n;
	}
	

	public String getValueName(int i) {
		return name[i];
	}
	

	public void setValueType(int i, int t) {
		type[i] = t;
	}
	

	public int getValueType(int i) {
		return type[i];
	}
	

	public void setClassId(int id) {
		classId = id;
		if(id >= numCategory + numBoolean)
			classId = -1;
	}
	

	public int getClassId() {
		return classId;
	}
	

	public void setDimSelectMode(int mode) {
		dimselectMode = mode;
	}
	

	public int getDimSelectMode() {
		return dimselectMode;
	}
	

	public OneIndividual addOneIndividual() {
		OneIndividual p = new OneIndividual(numNumeric, numCategory, numBoolean, plots.size());
		plots.add(p);
		return p;
	}

	
	public int getNumIndividual() {
		return plots.size();
	}
	
	
	public OneIndividual getOneIndividual(int id) {
		return (OneIndividual)plots.get(id);
	}
	
	
	public void finalize() {
		
		//System.out.println("Setup numeric information ...");
		numerics = new NumericSet(this);
		NumericDissimilarity.calculate(this);
		
		for(int i = 0; i < numNumeric; i++) {
			numerics.min[i] = 1.0e+30;   numerics.max[i] = -1.0e+30;
		}
		for(int j = 0; j < plots.size(); j++) {
			OneIndividual p = (OneIndividual)plots.get(j);
			p.id = j;
			for(int i = 0; i < numNumeric; i++) {
				numerics.min[i] = (numerics.min[i] > p.numeric[i]) ? p.numeric[i] : numerics.min[i];
				numerics.max[i] = (numerics.max[i] < p.numeric[i]) ? p.numeric[i] : numerics.max[i];
			}
		}
		
		//System.out.println("Setup category information ...");
		categories = new CategorySet(this);

		//System.out.println("Setup boolean information ...");
		booleans = new BooleanSet(this);
		BooleanCoOccurrence.calculate(this);
	}
	
}

