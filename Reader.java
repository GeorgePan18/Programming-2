import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

// class that reads doctors.xml

public class Reader {

    public void readDoctors(String filePath, DoctorCatalog catalog) {

        try {
            File file = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("doctor");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String ID = eElement.getElementsByTagName("ID").item(0).getTextContent();
                    String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    String speciality = eElement.getElementsByTagName("speciality").item(0).getTextContent();
                    String availability = eElement.getElementsByTagName("availability").item(0).getTextContent();

                    List<String> availabilityList = new ArrayList<>();
                    if (availability != null && !availability.isEmpty()) {
                        String[] weekdays = availability.split(",");
                        for (String day : weekdays) {
                            availabilityList.add(day.trim());  // Trim to remove extra spaces
                        }
                    }

                    catalog.addDoctor(ID, name, speciality, availabilityList);
                } 
            }
        } catch (Exception e) {
            System.out.println("There was an error accessing the file. Please make sure the file exists and you have permission to read it.");
        }
    }

}
