/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ConnectDatabase;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author PeeT
 */
public class StatSQL {

    
    
    public void StatSQL(){
        
    }

   

    public class StatPerson{

        ConnectDatabase.ConnectSQL Connection;

        public void setConnection(ConnectDatabase.ConnectSQL connection){
            this.Connection = connection;
        }
        
        public String getStatPerson(String beginAge, String endAge, String sex) throws SQLException{
            String query;
            if(endAge.equals("null")){
                query = "SELECT COUNT(*) AS personcount FROM person WHERE YEAR( FROM_DAYS( DATEDIFF( NOW( ) , birth ) ) ) >= '"+beginAge+"' AND sex = '"+sex+"'";
            }else{
                query = "SELECT COUNT(*) AS personcount FROM person WHERE YEAR( FROM_DAYS( DATEDIFF( NOW( ) , birth ) ) ) BETWEEN '"+beginAge+"' AND '"+endAge+"' AND sex = '"+sex+"'";
            }
            //System.out.println(query);
            ResultSet rs = Connection.getResultSet(query);
            String stat = "0";
            while(rs.next()){
                stat = rs.getString("personcount");
            }
            rs.close();
            return stat;
        }

    }

    public StatPerson getStatPerson(){
        StatPerson statPerson = new StatPerson();
        return statPerson;
    }

    public class StatChronic{
        ConnectDatabase.ConnectSQL Connection;

        public void setConnection(ConnectDatabase.ConnectSQL connection){
            this.Connection = connection;
        }

        public String getPersonchronicStat(String chronicCode) throws SQLException{
            String query = "SELECT COUNT(personchronic.pid) AS personchroniccount FROM personchronic,(SELECT diseasecode FROM `cdisease` WHERE codechronic = '"+chronicCode+"')t1 WHERE t1.diseasecode = personchronic.chroniccode";
            ResultSet rs = Connection.getResultSet(query);
            String personchronicStat = "0";
            while(rs.next()){
                personchronicStat = rs.getString("personchroniccount");
            }
            return personchronicStat;
        }

        public ResultSet getChronicName() throws SQLException{
            String query = "SELECT cdiseasechronic.groupname FROM cdiseasechronic";
            ResultSet rs = Connection.getResultSet(query);
            return rs;
        }
    }

    public StatChronic getStatChronic(){
        StatChronic statChronic = new StatChronic();
        return statChronic;
    }

    public class StatVisit{
        ConnectDatabase.ConnectSQL Connection;

        public void setConnection(ConnectDatabase.ConnectSQL connection){
            this.Connection = connection;
        }

        public int getAmountVisitAndroid(int month,String yearInUS) throws SQLException{
//            String query = "SELECT SUM(amount) AS sumamount FROM ffc_androidvisit WHERE MONTH(datesurvey) = '"+month+"' AND YEAR(datesurvey) = '"+yearInUS+"'";
//            System.out.println(query);
//            ResultSet rs = Connection.getResultSet(query);
            int visitamount = 0;
//            while(rs.next()){
//                visitamount = rs.getInt("sumamount");
//            }
            return visitamount;
        }

        public int getAmountVisitJHCIS(int month,String yearInUS) throws SQLException{
            String query = "SELECT count(visitno) AS sumamount FROM visit WHERE MONTH(visitdate) = '"+month+"' AND YEAR(visitdate) = '"+yearInUS+"'";
            ResultSet rs = Connection.getResultSet(query);
            int visitamount = 0;
            while(rs.next()){
                visitamount = rs.getInt("sumamount");
            }
            return visitamount;
        }
    }

    public StatVisit getStatvisit(){
        StatVisit statVisit = new StatVisit();
        return statVisit;
    }
}
