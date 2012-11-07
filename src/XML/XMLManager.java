/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package XML;
//import ConnectDatabase.StatSQL;
/**
 *
 * @author PeeT
 */
public class XMLManager {
    public void ffcStatWrite(){
        //StatSQL stat = new StatSQL();
        //StatSQL.StatPerson statPerson = stat.getStatPerson();
        //statPerson.getStatPerson("0","4","1");

        XMLWriter xmlWriter = new XMLWriter();

        xmlWriter.setPathXml("./FFC/FFC-Xml/FFC-Stat-population.xml");
        xmlWriter.appendNewVillage("หมู่บ้านเพนกวิน");
    }
}
