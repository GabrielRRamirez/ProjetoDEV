
package Util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author gabri
 */
public class FormatacaoNumero extends PlainDocument{
    
     @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        super.insertString(offs, str.toUpperCase().replaceAll("[^0-9|^ ,]", ""), a); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void replace(int offs, String str, AttributeSet a) throws BadLocationException {
        super.insertString(offs, str.toUpperCase().replaceAll("[^0-9|,]", ""), a); //To change body of generated methods, choose Tools | Templates.
    }
    
}
