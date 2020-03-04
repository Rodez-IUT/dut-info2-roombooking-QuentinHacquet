package xmlws.roombooking.xmltools;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Class for objects responsible of RoomBooking xml files parsing
 * SAX version
 */
public class RoomBookingSaxParser implements RoomBookingParser {

    RoomBooking roomBooking = new RoomBooking();
    String tmpLocalName;

    /**
     * Parse an xml file provided as an input stream
     *
     * @param inputStream the input stream corresponding to the xml file
     */
    public RoomBooking parse(InputStream inputStream) {

        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            SAXParser saxParser = spf.newSAXParser();
            saxParser.parse(inputStream, new RoomBookingHandler());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roomBooking;
    }

    private class RoomBookingHandler extends DefaultHandler {

        public void startElement(String namespaceURI,
                                 String localName,
                                 String qName,
                                 Attributes atts)
                throws SAXException {
            System.out.println("In element: "+localName);
            tmpLocalName = localName;
        }

        public void characters(char ch[], int start, int length)
                throws SAXException {
            System.out.println(new String(ch, start, length));
            String data = new String(ch, start, length);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

            if (data.charAt(0) != '\n') {
                if (tmpLocalName.equals("label")) {
                    roomBooking.setRoomLabel(data);
                }
                if (tmpLocalName.equals("username")) {
                    roomBooking.setUsername(data);
                }
                if (tmpLocalName.equals("startDate")) {
                    try {
                        roomBooking.setStartDate(sdf.parse(data));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (tmpLocalName.equals("endDate")) {
                    try {
                        roomBooking.setEndDate(sdf.parse(data));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
