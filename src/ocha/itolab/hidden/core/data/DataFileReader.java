package ocha.itolab.hidden.core.data;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.StringTokenizer;


public class DataFileReader {
	BufferedReader breader;
	String filename;
	IndividualSet ps = null;
	int numdim = 0;
	String directory = "";
	int phase = 1, numNumeric = 0, numCategory = 0, numBoolean = 0, classId = -1;
	
	void open(String filename) {
		this.filename = filename;
		numdim = 0;
		
		try {
			File file = new File(filename);
			breader = new BufferedReader(new FileReader(file));
			breader.ready();
			directory = file.getParent() + "/";
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	

	public void close() {
		try {
			breader.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	
	public String[] readLine() {
		String wordarray[] = null;
		
		try {
			
			String line = breader.readLine();
			if (line == null) return null;
			
			if(line.length() <= 0) {
				wordarray = new String[1];
				wordarray[0] = "";
				return wordarray;
			}
			
			StringTokenizer tokenBuffer = new StringTokenizer(line, ",");
			wordarray = new String[tokenBuffer.countTokens()];
			for(int i = 0; i < wordarray.length; i++) {
				wordarray[i] = tokenBuffer.nextToken();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		return wordarray;
	}
	
	
	public IndividualSet read(String filename) {
		System.out.println("Reading " + filename + " ...");
		
		open(filename);
		
		while(true) {
			String w[] = readLine();
			if(w == null) break;
			if(w[0].length() <= 0) continue;
			
			if(phase >= 3) {
				OneIndividual p = ps.addOneIndividual();
				int in = 0, ic = 0, ib = 0;
				
				for(int i = 0; i < w.length; i++) {
					if(ps.getValueType(i) == ps.TYPE_NUMERIC) {
						p.numeric[in++] = Double.parseDouble(w[i]);
					}
					else if(ps.getValueType(i) == ps.TYPE_BOOLEAN) {
						p.bool[ib] = false;
						if(w[i].startsWith("true") == true) p.bool[ib] = true;
						if(w[i].startsWith("1") == true) p.bool[ib] = true;
						ib++;
					}
					else {
						p.category[ic++] = w[i];
					}
				}
				
			}
				
			else if(phase == 2) {
							
				for(int i = 0; i < w.length; i++) {
					ps.setValueName(i, w[i]);
				}
				phase++;  continue;
			}
			
			else {

				for(int i = 0; i < w.length; i++) {
					if(w[i].startsWith("Numeric") == true)
						numNumeric++;
					else if(w[i].startsWith("Boolean") == true)
						numBoolean++;
					else {
						numCategory++;
					}
				}
				System.out.println("   length=" + w.length + " numeric=" + numNumeric + " category=" + numCategory);
				ps = new IndividualSet(numNumeric, numCategory, numBoolean, classId);
				for(int i = 0; i < w.length; i++) {
					if(w[i].startsWith("Numeric") == true) 
						ps.setValueType(i, ps.TYPE_NUMERIC);
					else if(w[i].startsWith("Boolean") == true)
						ps.setValueType(i, ps.TYPE_BOOLEAN);
					else
						ps.setValueType(i, ps.TYPE_CATEGORY);
				}
				
				
				phase++;  continue;
			}
			
		}
		
		ps.filename = this.filename;
		ps.finalize();
		return ps;
	}
	
	
}
