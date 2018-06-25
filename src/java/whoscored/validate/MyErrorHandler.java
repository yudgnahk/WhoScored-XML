/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whoscored.validate;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author haleduykhang
 */
public class MyErrorHandler implements ErrorHandler{

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        System.out.println("\nWARNING");
        exception.printStackTrace();
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        System.out.println("\nERROR");
        exception.printStackTrace();
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        System.out.println("\nFATAL ERROR");
        exception.printStackTrace();
    }
    
}
