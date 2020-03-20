/**
 * The TessSetup class is used to setup and run Tess4j
 * It determines if we should attack and if the main game loop
 * should keep running.
 * 
 * @param isRunning Value determining if main game loop should keep running
 */
package model;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import java.io.File;

public class TessSetup {
	
	private boolean isRunning = true;

	Tesseract tess;

	public TessSetup() {
		tess = new Tesseract();
		tess.setDatapath("tessdata");
		tess.setLanguage("eng");
	}

	public boolean tessScanFile(String[] enemyArr, String enemyFilename, String remAtkFilename) {
		  /**
		   * Method takes the stored images from folder and scans them for;
		   * 1. Has more attacks so keep attacking
		   * 2. Is current selected to be attacked or not
		   * @param enemyArr An array with the teams given to be attacked if encountered
		   * @param enemyFilename Filename of the img that would check the enemy name
		   * @param remAtkFilename Filename of the img that would check for how many attacks are left
		   * @return boolean True = Keep attacking, False = stop attacking
		   */
		boolean toAttack = false;
		try {
			String enemyName = tess.doOCR(new File(enemyFilename + ".png"));
			String remainingAttacks = tess.doOCR(new File(remAtkFilename + ".png"));
			
			String[] splitVals = remainingAttacks.split("/");
			if (Integer.valueOf(splitVals[0]) == 0) {
				setRunning(false);
				return toAttack;
			} else {
				for (String e : enemyArr) {
					e = normalizeText(e);
					enemyName = normalizeText(enemyName);
					if (enemyName.contains(e)) {
						toAttack = true;
						break;
					}
				}
			}
			
		} catch (TesseractException e) {
			System.out.println("Error with reading data: /n " + e.getMessage());
		}
		return toAttack;
	}

	private String normalizeText(String str) {
//      TODO this will have issue with teams with same name different caps will fix this when the issue appears :(
//      TODO Also need to add some server functionality. Different servers can have exact same names.
		str = str.toLowerCase().replace(" ", "");
		return str;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
}
