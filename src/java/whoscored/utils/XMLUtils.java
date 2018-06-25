/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whoscored.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;
import whoscored.validate.MyErrorHandler;

/**
 *
 * @author haleduykhang
 */
public class XMLUtils {

    public static String marshalToString(Object obj, Class clazz, boolean format, String encoding) {
        StringWriter sw = null;
        try {
            sw = new StringWriter();
            JAXBContext jaxb = JAXBContext.newInstance(clazz);

            Marshaller marshal = jaxb.createMarshaller();
            marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, format);
            marshal.setProperty(Marshaller.JAXB_ENCODING, encoding);
            marshal.marshal(obj, sw);

            return sw.toString();
        } catch (JAXBException ex) {
            Logger.getLogger(XMLUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (sw != null) {
                    sw.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(XMLUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static boolean validateJAXBObject(Object obj, Class clazz, String schemaPath) {
        try {
            JAXBContext jc = JAXBContext.newInstance(clazz);
            JAXBSource source = new JAXBSource(jc, obj);

            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File(schemaPath));

            Validator validator = schema.newValidator();
            validator.setErrorHandler(new MyErrorHandler());
            validator.validate(source);
            return true;
        } catch (JAXBException ex) {
            System.out.println("ERROR : " + ex.getClass() + " - " + ex.getMessage());
        } catch (SAXException ex) {
            System.out.println("ERROR : " + ex.getClass() + " - " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("ERROR : " + ex.getClass() + " - " + ex.getMessage());
        }
        return false;
    }
}
