/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package XML;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.*;


/**
 *
 * @author PeeT
 */
public class XMLWriter{

    protected String pathFile;

    public void setPathXml(String pathXml){
        this.pathFile = pathXml;
    }

    public String getPathFile(){
        return this.pathFile;
    }

    Document doc;
    public void setAttribute(/*String tagName, String attributeName, String data*/){
        try {
            String filePath = "./FFC-Xml/FFC-Stat-population.xml";
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filePath);
            //Node company = doc.getFirstChild();
            NodeList visitStat = doc.getElementsByTagName("VisitStat");

            //NamedNodeMap attr = staff.getAttributes();
            //Node nodeAttr = attr.getNamedItem("sex");
            //nodeAttr.setTextContent(data);

            //NodeList list = staff.getChildNodes();

            for (int i = 0; i < visitStat.getLength(); i++) {
               Node node = visitStat.item(i);
               Element firstPersonElement = (Element)node;
                    //-------
                    
                    NodeList firstNameList1 = firstPersonElement.getElementsByTagName("month");
                    for(int j=0; j<firstNameList1.getLength(); j++){
                    System.out.println(firstNameList1.getLength());
                    Element firstNameElement = (Element)firstNameList1.item(j);

                    NodeList textFNList = firstNameElement.getChildNodes();
                    System.out.println("First Name : " +
                           ((Node)textFNList.item(0)).getNodeValue().trim());
                    }
               // get the salary element, and update the value
             /* if (node.getAttributes()) {
                    node.setTextContent("500");
               }*/
               // System.out.println(node.getNodeName().toString());
            }

        }catch (ParserConfigurationException pce) {
		pce.printStackTrace();
       }  catch (IOException ioe) {
                ioe.printStackTrace();
       } catch (SAXException sae) {
                sae.printStackTrace();
       }
    }
    
    public void appendNewVillage(String villageName){
        try {
            //File xml = new File("./FFC-Xml/FFC-Stat-populationn.xml");
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root elements
		this.doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("Hospital");
		doc.appendChild(rootElement);

		// stat elements
		Element stat = doc.createElement("Stat");
		rootElement.appendChild(stat);

                stat.setAttribute("VillageName", villageName);
                stat.setAttribute("population", "1");

                ///////////////////////////////////////////////////////////
                ///////////////////// Visit Stat //////////////////////
                ///////////////////////////////////////////////////////////

                Element visitStat = doc.createElement("VisitStat");
		stat.appendChild(visitStat);
		
		Element android = doc.createElement("android");
		//visitStat.appendChild(doc.createTextNode("yong"));
		visitStat.appendChild(android);

		Element androidyear = doc.createElement("Year");
		android.appendChild(androidyear);

                androidyear.setAttribute("format", "th");
                androidyear.setAttribute("year", "2555");
                String[] month = {"January","February","March","April","May","June","July","August","September","October","November","December"};
                String[] visitstatAndroid = this.getVisitstatAndroid();
                Element totalVisitStatAndroid = doc.createElement("total");
                totalVisitStatAndroid.appendChild(doc.createTextNode(String.valueOf(this.sumstat(visitstatAndroid))));
                androidyear.appendChild(totalVisitStatAndroid);
                this.createNewElement(androidyear, "month", visitstatAndroid, "name", month);


                Element jhcis = doc.createElement("JHCIS");
		//visitStat.appendChild(doc.createTextNode("yong"));
		visitStat.appendChild(jhcis);

                Element jhcisyear = doc.createElement("Year");
		jhcis.appendChild(jhcisyear);
                String[] visitstatJHCIS = this.getVisitstatJHCIS(visitstatAndroid);
                Element totalVisitStatJHCIS = doc.createElement("total");
                totalVisitStatJHCIS.appendChild(doc.createTextNode(String.valueOf(this.sumstat(visitstatJHCIS))));
                jhcisyear.appendChild(totalVisitStatJHCIS);
                this.createNewElement(jhcisyear, "month", visitstatJHCIS, "name", month);

                jhcisyear.setAttribute("format", "th");
                jhcisyear.setAttribute("year", "2555");

                ///////////////////////////////////////////////////////////
                ///////////////////// person Stat //////////////////////
                ///////////////////////////////////////////////////////////



                String yearRange[] = {"0-4","5-9","10-14","15-19","20-24","25-29","30-34","35-39","40-44","45-49",
                                      "50-54","55-59","60-64","65-69","70-74","75"};

                Element personStatmale = doc.createElement("PersonStat");

		stat.appendChild(personStatmale);

                personStatmale.setAttribute("sex", "male");
                String[] personstatmale = this.getPersonStatMale();
                Element totalpersonstatmale = doc.createElement("total");
                totalpersonstatmale.appendChild(doc.createTextNode(String.valueOf(this.sumstat(personstatmale))));
                personStatmale.appendChild(totalpersonstatmale);
                this.createNewElement(personStatmale, "age", personstatmale, "range", yearRange);

                Element personStatfemale = doc.createElement("PersonStat");
		stat.appendChild(personStatfemale);
                personStatfemale.setAttribute("sex", "female");
                String[] personstatfemale = this.getPersonStatFemale();
                Element totalpersonstatfemale = doc.createElement("total");
                totalpersonstatfemale.appendChild(doc.createTextNode(String.valueOf(this.sumstat(personstatfemale))));
                personStatfemale.appendChild(totalpersonstatfemale);
                this.createNewElement(personStatfemale, "age", personstatfemale, "range", yearRange);


                ///////////////////////////////////////////////////////////
                ///////////////////// personchronic Stat //////////////////////
                ///////////////////////////////////////////////////////////

                Element personChronicStat = doc.createElement("PersonChronicStat");

		stat.appendChild(personChronicStat);
                String[] chronicstat = this.getChronicStat();
                Element totalchronicstat = doc.createElement("total");
                totalchronicstat.appendChild(doc.createTextNode(String.valueOf(this.sumstat(chronicstat))));
                personChronicStat.appendChild(totalchronicstat);
                this.createNewElement(personChronicStat, "Chronic",chronicstat , "name", this.getchronicName());

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();

		Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(this.pathFile));

		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);

		transformer.transform(source, result);

		System.out.println("File saved!");

        } catch (SQLException ex) {
            Logger.getLogger(XMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(XMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }  catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

    public void createNewElement(Element parentElement,String newElementName, String[] value, String attrName, String[] attrValue){
        int count;
        for(count=0;count<value.length;count++){
            Element newElement = doc.createElement(newElementName);
            newElement.setAttribute(attrName, attrValue[count]);
            newElement.appendChild(doc.createTextNode(value[count]));
            parentElement.appendChild(newElement);
        }
    }

    public void addAttribute(){

    }

    public String[] getPersonStatMale() throws SQLException{
        ConnectDatabase.StatSQL stat = new ConnectDatabase.StatSQL();
        ConnectDatabase.StatSQL.StatPerson statPerson = stat.getStatPerson();
        if(Service.Service.connectionSQL.connection.isClosed()){
            System.out.println("closeeeeeeee");
        }else{
            System.out.println("Opennnnnnnnn");
        }
        statPerson.setConnection(Service.Service.connectionSQL);
        String[] personStatNum = new String[16];
        personStatNum[0] = statPerson.getStatPerson("0", "4", "1");
        personStatNum[1] = statPerson.getStatPerson("5", "9", "1");
        personStatNum[2] = statPerson.getStatPerson("10", "14", "1");
        personStatNum[3] = statPerson.getStatPerson("15", "19", "1");
        personStatNum[4] = statPerson.getStatPerson("20", "24", "1");
        personStatNum[5] = statPerson.getStatPerson("25", "29", "1");
        personStatNum[6] = statPerson.getStatPerson("30", "34", "1");
        personStatNum[7] = statPerson.getStatPerson("35", "39", "1");
        personStatNum[8] = statPerson.getStatPerson("40", "44", "1");
        personStatNum[9] = statPerson.getStatPerson("45", "49", "1");
        personStatNum[10] = statPerson.getStatPerson("50", "54", "1");
        personStatNum[11] = statPerson.getStatPerson("55", "59", "1");
        personStatNum[12] = statPerson.getStatPerson("60", "64", "1");
        personStatNum[13] = statPerson.getStatPerson("65", "69", "1");
        personStatNum[14] = statPerson.getStatPerson("70", "74", "1");
        personStatNum[15] = statPerson.getStatPerson("75", "null", "1");
        return personStatNum;
    }

    public String[] getPersonStatFemale() throws SQLException{
        ConnectDatabase.StatSQL stat = new ConnectDatabase.StatSQL();
        ConnectDatabase.StatSQL.StatPerson statPerson = stat.getStatPerson();
        String[] personStatNum = new String[16];
        statPerson.setConnection(Service.Service.connectionSQL);
        personStatNum[0] = statPerson.getStatPerson("0", "4", "2");
        personStatNum[1] = statPerson.getStatPerson("5", "9", "2");
        personStatNum[2] = statPerson.getStatPerson("10", "14", "2");
        personStatNum[3] = statPerson.getStatPerson("15", "19", "2");
        personStatNum[4] = statPerson.getStatPerson("20", "24", "2");
        personStatNum[5] = statPerson.getStatPerson("25", "29", "2");
        personStatNum[6] = statPerson.getStatPerson("30", "34", "2");
        personStatNum[7] = statPerson.getStatPerson("35", "39", "2");
        personStatNum[8] = statPerson.getStatPerson("40", "44", "2");
        personStatNum[9] = statPerson.getStatPerson("45", "49", "2");
        personStatNum[10] = statPerson.getStatPerson("50", "54", "2");
        personStatNum[11] = statPerson.getStatPerson("55", "59", "2");
        personStatNum[12] = statPerson.getStatPerson("60", "64", "2");
        personStatNum[13] = statPerson.getStatPerson("65", "69", "2");
        personStatNum[14] = statPerson.getStatPerson("70", "74", "2");
        personStatNum[15] = statPerson.getStatPerson("75", "null", "2");
        return personStatNum;
    }

    public String[] getChronicStat() throws SQLException{
        ConnectDatabase.StatSQL stat = new ConnectDatabase.StatSQL();
        ConnectDatabase.StatSQL.StatChronic statChronic = stat.getStatChronic();
        statChronic.setConnection(Service.Service.connectionSQL);

        String[] chronicStat = new String[16];
        chronicStat[0] = statChronic.getPersonchronicStat("01");
        chronicStat[1] = statChronic.getPersonchronicStat("02");
        chronicStat[2] = statChronic.getPersonchronicStat("03");
        chronicStat[3] = statChronic.getPersonchronicStat("04");
        chronicStat[4] = statChronic.getPersonchronicStat("05");
        chronicStat[5] = statChronic.getPersonchronicStat("06");
        chronicStat[6] = statChronic.getPersonchronicStat("07");
        chronicStat[7] = statChronic.getPersonchronicStat("08");
        chronicStat[8] = statChronic.getPersonchronicStat("09");
        chronicStat[9] = statChronic.getPersonchronicStat("10");
        chronicStat[10] = statChronic.getPersonchronicStat("11");
        chronicStat[11] = statChronic.getPersonchronicStat("12");
        chronicStat[12] = statChronic.getPersonchronicStat("13");
        chronicStat[13] = statChronic.getPersonchronicStat("14");
        chronicStat[14] = statChronic.getPersonchronicStat("15");
        chronicStat[15] = statChronic.getPersonchronicStat("16");
        return chronicStat;
    }
    
    public String[] getchronicName() throws SQLException{
        ConnectDatabase.StatSQL stat = new ConnectDatabase.StatSQL();
        ConnectDatabase.StatSQL.StatChronic statChronic = stat.getStatChronic();
        
        statChronic.setConnection(Service.Service.connectionSQL);
        ResultSet rs = statChronic.getChronicName();
        int nameCount = 0;

        ArrayList<String> chronicGroupNameList = new ArrayList<String>();

        while(rs.next()){
            chronicGroupNameList.add(rs.getString("groupname"));
        }
        String[] personchronicStat = new String[chronicGroupNameList.size()];
        for(String chronicname : chronicGroupNameList){
            personchronicStat[nameCount] = chronicname;
            nameCount++;
        }
        return personchronicStat;
    }

    public String[] getVisitstatAndroid() throws SQLException{
        ConnectDatabase.StatSQL stat = new ConnectDatabase.StatSQL();
        ConnectDatabase.StatSQL.StatVisit statvisit = stat.getStatvisit();
        statvisit.setConnection(Service.Service.connectionSQL);
        String[] visitstat = new String[12];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy",java.util.Locale.US);
        Date d = new Date();
        for(int count=0;count<visitstat.length;count++){
            visitstat[count] = String.valueOf(statvisit.getAmountVisitAndroid(count+1, sdf.format(d).toString()));
        }
        return visitstat;
    }

    public String[] getVisitstatJHCIS(String[] visitstatAndroid) throws SQLException{
        ConnectDatabase.StatSQL stat = new ConnectDatabase.StatSQL();
        ConnectDatabase.StatSQL.StatVisit statvisit = stat.getStatvisit();
        statvisit.setConnection(Service.Service.connectionSQL);
        String[] visitstat = new String[12];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy",java.util.Locale.US);
        Date d = new Date();
        for(int count=0;count<visitstat.length;count++){
            visitstat[count] = String.valueOf(statvisit.getAmountVisitJHCIS(count+1, sdf.format(d).toString()) - Integer.valueOf(visitstatAndroid[count]));
        }
        return visitstat;
    }

    public int sumstat(String[] stat){
        int sumstat = 0;
        for(String statnum : stat){
            sumstat += Integer.valueOf(statnum);
        }
        return sumstat;
    }
    
}
