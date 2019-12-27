import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class TessSetup {

    Tesseract tess;

    public TessSetup() {
        tess = new Tesseract();
        tess.setDatapath("tessdata");
        tess.setLanguage("eng");
    }

    public boolean tessScanFile(String enemyFilename, String[] enemyToAttack, String remAtkFilename) {
        boolean toAttack = false;
        try {
            String enemyName = tess.doOCR(new File(enemyFilename + ".png"));
            String remainingAttacks = tess.doOCR(new File(remAtkFilename + ".png"));

            for(String e : enemyToAttack) {

                e = normalizeText(e);
                enemyName = normalizeText(enemyName);
                if(enemyName.contains(e)) {
                    toAttack = true;
                    break;
                }
            }
//          Check if remaining attacks is 0
//          TODO figure out how to stop main loop.
            if (remainingAttacks.equals("0/40") && toAttack) {
                toAttack = false;
            }
        } catch (TesseractException e) {
            System.out.println("Error with reading data: /n " + e.getMessage());
        } finally {
            return toAttack;
        }
    }

    private String normalizeText(String str) {
//      TODO this will have issue with teams with same name different caps will fix this when the issue appears :(
//      TODO Also need to add some server functionality. Different servers can have exact same names.
        str = str.toLowerCase();
        str = str.trim();
        str = str.replace(" ", "");
        return str;
    }

}
