/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectDatabase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author PeeT
 * @version 2.1
 */
public class SqlQuery {
    
    private String houseNo;
   /* public String personChronicQuery = "SELECT t1.* FROM house,(SELECT person.* FROM personchronic,_tmpicd10tmv4,person "
            + "WHERE _tmpicd10tmv4.diag_chronic = ? AND _tmpicd10tmv4.diagcode = personchronic.chroniccode AND "
            + "personchronic.pid = person.pid)t1 WHERE t1.hcode = house.hcode";
    
    
    public String personBehaviorChronicQuery = "SELECT personbehavior.* FROM personbehavior,(SELECT t1.pid FROM house,"
            + "(SELECT person.* FROM personchronic,_tmpicd10tmv4,person WHERE _tmpicd10tmv4.diag_chronic = ? "
            + "AND _tmpicd10tmv4.diagcode = personchronic.chroniccode AND personchronic.pid = person.pid)t1"
            + " WHERE t1.hcode = house.hcode)t2 WHERE personbehavior.pid = t2.pid";

    public String houseChronicquery = "SELECT house.* FROM house,(SELECT person.hcode FROM personchronic,_tmpicd10tmv4,person "
            + "WHERE _tmpicd10tmv4.diag_chronic = ? AND _tmpicd10tmv4.diagcode = personchronic.chroniccode "
            + "AND personchronic.pid = person.pid)t1 WHERE t1.hcode = house.hcode";
    
    public String personChronicChronicQuery = "SELECT personchronic.* FROM personchronic,_tmpicd10tmv4 "
            + "WHERE _tmpicd10tmv4.diag_chronic = ? AND _tmpicd10tmv4.diagcode = personchronic.chroniccode";
    
    public String visitChronicQuery = "SELECT visit.* FROM visit,(SELECT person.* FROM personchronic, _tmpicd10tmv4, person "
            + "WHERE _tmpicd10tmv4.diag_chronic = ? AND _tmpicd10tmv4.diagcode = personchronic.chroniccode "
            + "AND personchronic.pid = person.pid)t1 WHERE visit.pid = t1.pid AND t1.pcucodeperson = visit.pcucodeperson";
    
    public String visitDiagChronicQuery = "SELECT visitdiag.* FROM visitdiag,(SELECT visit.* FROM visit,"
            + "(SELECT person.* FROM personchronic, _tmpicd10tmv4, person WHERE _tmpicd10tmv4.diag_chronic = ? "
            + "AND _tmpicd10tmv4.diagcode = personchronic.chroniccode AND personchronic.pid = person.pid)t1 "
            + "WHERE visit.pid = t1.pid AND t1.pcucodeperson = visit.pcucodeperson)t2 WHERE visitdiag.visitno = t2.visitno "
            + "AND t2.pcucode = visitdiag.pcucode";
    
    public String visitDrugChronicQuery = "SELECT visitdrug.* FROM visitdrug,(SELECT visit.* FROM visit,"
            + "(SELECT person.* FROM personchronic, _tmpicd10tmv4, person WHERE _tmpicd10tmv4.diag_chronic = ? "
            + "AND _tmpicd10tmv4.diagcode = personchronic.chroniccode AND personchronic.pid = person.pid)t1 "
            + "WHERE visit.pid = t1.pid AND t1.pcucodeperson = visit.pcucodeperson)t2 WHERE visitdrug.visitno = t2.visitno "
            + "AND t2.pcucode = visitdrug.pcucode";
     * 
    */
    public Query getQuery(){
        Query query = new Query();
        return query;
    }
    
    public ChronicQuery getChronicQuery()
    {
        ChronicQuery chronicQuery = new ChronicQuery();
        return chronicQuery;
    }
    
     public class ChronicQuery {
        
        private String diagChronicCode;
        
        public String personChronicQuery = "SELECT person.* FROM person,house WHERE person.hcode = house.hcode "
                + "AND house.villcode = ? AND house.hno IN (?)";

        public String personBehaviorChronicQuery = "SELECT personbehavior.* FROM personbehavior,"
                + "(SELECT person.* FROM person,house WHERE person.hcode = house.hcode "
                + "AND house.villcode = ? AND house.hno IN (?))t1 WHERE personbehavior.pid = t1.pid "
                + "AND t1.pcucodeperson = personbehavior.pcucodeperson";

        public String houseChronicquery = "SELECT DISTINCT house.* FROM house,(SELECT person.hcode FROM personchronic,_tmpicd10tmv4,person "
                + "WHERE _tmpicd10tmv4.diag_chronic = ? AND _tmpicd10tmv4.diagcode = personchronic.chroniccode "
                + "AND personchronic.pid = person.pid)t1 WHERE t1.hcode = house.hcode AND house.villcode = ? ORDER BY house.hno ASC";

        public String personChronicChronicQuery = "SELECT personchronic.* FROM personchronic,"
                + "(SELECT person.* FROM person,house WHERE person.hcode = house.hcode AND house.villcode = ? "
                + "AND house.hno IN (?))t1 WHERE personchronic.pid = t1.pid AND t1.pcucodeperson = personchronic.pcucodeperson";

        public String visitChronicQuery = "SELECT visit.* FROM visit,(SELECT person.* FROM person,house WHERE person.hcode = house.hcode "
                + "AND house.villcode = ? AND house.hno IN (?))t1 WHERE visit.pid = t1.pid AND visit.pcucodeperson = t1.pcucodeperson";

        public String visitDiagChronicQuery = "SELECT visitdiag.* FROM visitdiag,visit,(SELECT person.* FROM person,house "
                + "WHERE person.hcode = house.hcode AND house.villcode = ? AND house.hno IN (?))t1 "
                + "WHERE visit.pid = t1.pid AND visit.pcucodeperson = t1.pcucodeperson AND visitdiag.visitno = visit.visitno";

        public String visitDrugChronicQuery = "SELECT visitdrug.* FROM visitdrug,visit,(SELECT person.* FROM person,house "
                + "WHERE person.hcode = house.hcode AND house.villcode = ? AND house.hno IN (?))t1 WHERE visit.pid = t1.pid "
                + "AND visit.pcucodeperson = t1.pcucodeperson AND visitdrug.visitno = visit.visitno";

        public void initChronicVariable(String diagChronicCode, String houseNo)
        {
            this.diagChronicCode = diagChronicCode;
            SqlQuery.this.houseNo = houseNo;
        }

        public ResultSet getResultSetPersonChronicQuery() throws SQLException
        {
            PreparedStatement pstmt = Service.Service.connectionSQL.connection.prepareStatement(this.personChronicQuery);
            pstmt.setString(1, diagChronicCode);
            pstmt.setString(2, SqlQuery.this.houseNo);
            System.out.println(pstmt);
            return pstmt.executeQuery();
        }

        public ResultSet getResultSetPersonBehaviorChronicQuery() throws SQLException
        {
            PreparedStatement pstmt = Service.Service.connectionSQL.connection.prepareStatement(this.personBehaviorChronicQuery);
            pstmt.setString(1, diagChronicCode);
            pstmt.setString(2, SqlQuery.this.houseNo);
            System.out.println(pstmt);
            return pstmt.executeQuery();
        }

        public ResultSet getResultSetHouseChronicQuery() throws SQLException
        {
            PreparedStatement pstmt = Service.Service.connectionSQL.connection.prepareStatement(this.houseChronicquery);
            pstmt.setString(1, diagChronicCode);
            pstmt.setString(2, SqlQuery.this.houseNo);
            System.out.println(pstmt);
            return pstmt.executeQuery();
        }

         public ResultSet getResultSetPersonChronicChronicQuery() throws SQLException
        {
            PreparedStatement pstmt = Service.Service.connectionSQL.connection.prepareStatement(this.personChronicChronicQuery);
            pstmt.setString(1, diagChronicCode);
            pstmt.setString(2, SqlQuery.this.houseNo);
            System.out.println(pstmt);
            return pstmt.executeQuery();
        }

         public ResultSet getResultSetVisitChronicQuery() throws SQLException
        {
            PreparedStatement pstmt = Service.Service.connectionSQL.connection.prepareStatement(this.visitChronicQuery);
            pstmt.setString(1, diagChronicCode);
            pstmt.setString(2, SqlQuery.this.houseNo);
            System.out.println(pstmt);
            return pstmt.executeQuery();
        }

          public ResultSet getResultSetVisitDiagChronicQuery() throws SQLException
        {
            PreparedStatement pstmt = Service.Service.connectionSQL.connection.prepareStatement(this.visitDiagChronicQuery);
            pstmt.setString(1, diagChronicCode);
            pstmt.setString(2, SqlQuery.this.houseNo);
            System.out.println(pstmt);
            return pstmt.executeQuery();
        }

           public ResultSet getResultSetVisitDrugChronicQuery() throws SQLException
        {
            PreparedStatement pstmt = Service.Service.connectionSQL.connection.prepareStatement(this.visitDrugChronicQuery);
            pstmt.setString(1, diagChronicCode);
            pstmt.setString(2, SqlQuery.this.houseNo);
            System.out.println(pstmt);
            return pstmt.executeQuery();
        }
    }
    
    public class Query{
        private String houseList;
        private String village;
        private String date = getDate();
        //Calendar calendar = new Calendar();

        
        public void initValues(String houselist,String village){
            this.houseList = houselist;
            this.village = village;
            System.out.println(this.houseList);
            System.out.println(this.village);
        }
        
        private String personQuery;
        
        private String personbehaviorQuery;
        
        private String personchronicQuery;
        
        private String houseQuery;
        
        private String visitQuery;
        
        private String visitdiagQuery;
        
        private String visitdrugQuery;
        
        private String maxVisitQuery;

        private String maxPidQuery;
        
        public ResultSet getPersonQuery(String village, String houseList) throws SQLException{
            personQuery = "SELECT person.* FROM person,house WHERE person.hcode = house.hcode "
                + "AND house.villcode IN (?) AND house.hno IN ('"+houseList+"')";
            PreparedStatement pstmt = Service.Service.connectionSQL.connection.prepareStatement(this.personQuery);
            pstmt.setString(1, village);
            System.out.println(pstmt);
            return pstmt.executeQuery();
        }

        public ResultSet getPersonAllQuery(String village, String houseList) throws SQLException{
            personQuery = "SELECT person.* FROM person";
            PreparedStatement pstmt = Service.Service.connectionSQL.connection.prepareStatement(this.personQuery);
            return pstmt.executeQuery();
        }

        public ResultSet getPersonbahaviorQuery(String village, String houseList) throws SQLException{
            personbehaviorQuery = "SELECT personbehavior.* FROM personbehavior,(SELECT person.pid,person.pcucodeperson FROM person,house "
                + "WHERE person.hcode = house.hcode AND house.villcode IN (?) AND house.hno IN ('"+houseList+"'))t1 "
                + "WHERE t1.pid = personbehavior.pid AND t1.pcucodeperson = personbehavior.pcucodeperson";
            PreparedStatement pstmt = Service.Service.connectionSQL.connection.prepareStatement(this.personbehaviorQuery);
            pstmt.setString(1, village);
            System.out.println(pstmt);
            return pstmt.executeQuery();
        }
        
        public ResultSet getPersonchronicQuery(String village, String houseList) throws SQLException{
            personchronicQuery = "SELECT personchronic.* FROM personchronic,(SELECT person.pid,person.pcucodeperson FROM person,house "
                + "WHERE person.hcode = house.hcode AND house.villcode IN (?) AND house.hno IN ('"+houseList+"'))t1 "
                + "WHERE t1.pid = personchronic.pid AND personchronic.pcucodeperson = t1.pcucodeperson;";
            PreparedStatement pstmt = Service.Service.connectionSQL.connection.prepareStatement(this.personchronicQuery);
            pstmt.setString(1, village);
            System.out.println(pstmt);
            return pstmt.executeQuery();
        }
        
        public ResultSet getHouseQuery(String village, String houseList) throws SQLException{
            houseQuery = "SELECT house.* FROM house WHERE house.villcode IN (?) AND house.hno IN ('"+houseList+"')";
            PreparedStatement pstmt = Service.Service.connectionSQL.connection.prepareStatement(this.houseQuery);
            pstmt.setString(1, village);
            System.out.println(pstmt);
            return pstmt.executeQuery();
        }

        public ResultSet getHouseanimalQuery(String village, String houseList) throws SQLException{
            String houseanimalQuery = "SELECT houseanimal.* FROM houseanimal,house WHERE houseanimal.pcucode = house.pcucode AND house.hno IN ('"+houseList+"')"
                    + "AND house.hcode = houseanimal.hcode";
            PreparedStatement pstmt = Service.Service.connectionSQL.connection.prepareStatement(houseanimalQuery);
            pstmt.setString(1, village);
            System.out.println(pstmt);
            return pstmt.executeQuery();
        }

        public ResultSet getHousegenuculexQuery(String village, String houseList) throws SQLException{
            String housegenuculexQuery = "SELECT housegenuculex.* FROM housegenuculex,house WHERE housegenuculex.pcucode = house.pcucode AND house.hno IN ('"+houseList+"')"
                    + "AND house.hcode = housegenuculex.hcode";
            PreparedStatement pstmt = Service.Service.connectionSQL.connection.prepareStatement(housegenuculexQuery);
            pstmt.setString(1, village);
            System.out.println(pstmt);
            return pstmt.executeQuery();
        }
        
        public ResultSet getvisitQuery(String village, String houseList) throws SQLException{
            visitQuery = "SELECT visit.* FROM visit,(SELECT person.pid,person.pcucodeperson FROM person,house "
                + "WHERE person.hcode = house.hcode AND house.villcode IN (?) AND house.hno IN ('"+houseList+"'))t1 "
                + "WHERE dateupdate > '"+date+"' AND t1.pid = visit.pid AND t1.pcucodeperson = visit.pcucodeperson";
            //System.out.println(dateFormat.format(datenow.getYear()-1));
            PreparedStatement pstmt = Service.Service.connectionSQL.connection.prepareStatement(this.visitQuery);
            pstmt.setString(1, village);
            System.out.println(pstmt);
            return pstmt.executeQuery();
        }
        
        public ResultSet getVisitdiagQuery(String village, String houseList) throws SQLException{
            visitdiagQuery = "SELECT visitdiag.* FROM visitdiag,(SELECT visit.visitno FROM visit,(SELECT person.pid,person.pcucodeperson FROM person,house "
                + "WHERE person.hcode = house.hcode AND house.villcode IN (?) AND house.hno IN ('"+houseList+"'))t1 "
                + "WHERE dateupdate > '"+date+"' AND t1.pid = visit.pid AND t1.pcucodeperson = visit.pcucodeperson)t2 WHERE visitdiag.visitno = t2.visitno";
            PreparedStatement pstmt = Service.Service.connectionSQL.connection.prepareStatement(this.visitdiagQuery);
            pstmt.setString(1, village);
            System.out.println(pstmt);
            return pstmt.executeQuery();
        }
        
        public ResultSet getVisitdrugQuery(String village, String houseList) throws SQLException{
            visitdrugQuery = "SELECT visitdrug.* FROM visitdrug,(SELECT visit.visitno FROM visit,(SELECT person.pid,person.pcucodeperson FROM person,house "
                + "WHERE person.hcode = house.hcode AND house.villcode IN (?) AND house.hno IN ('"+houseList+"'))t1 "
                + "WHERE t1.pid = visit.pid AND t1.pcucodeperson = visit.pcucodeperson)t2 WHERE visitdrug.visitno = t2.visitno";
            PreparedStatement pstmt = Service.Service.connectionSQL.connection.prepareStatement(this.visitdrugQuery);
            pstmt.setString(1, village);
            System.out.println(pstmt);
            return pstmt.executeQuery();
        }
        
        public ResultSet getMaxVisit(String village, String houseList) throws SQLException{
            maxVisitQuery = "SELECT MAX(visitno) AS maxVisit FROM (SELECT visit.* FROM visit,(SELECT person.pid,person.pcucodeperson FROM person,house "
                + "WHERE person.hcode = house.hcode AND house.villcode IN (?) AND house.hno IN ('"+houseList+"'))t1 "
                + "WHERE t1.pid = visit.pid AND t1.pcucodeperson = visit.pcucodeperson)t3";
            PreparedStatement pstmt = Service.Service.connectionSQL.connection.prepareStatement(this.maxVisitQuery);
            pstmt.setString(1, village);
            System.out.println(pstmt);
            return pstmt.executeQuery();
        }

        public ResultSet getMaxPid(String village, String houseList) throws SQLException{
            maxPidQuery = "SELECT MAX(person.pid) FROM person,house WHERE person.hcode = house.hcode "
                + "AND house.villcode IN (?) AND house.hno IN ('"+houseList+"')";
            PreparedStatement pstmt = Service.Service.connectionSQL.connection.prepareStatement(this.maxPidQuery);
            pstmt.setString(1, village);
            System.out.println(pstmt);
            return pstmt.executeQuery();
        }

        public ResultSet getcdisease(){
            String cdiseaseQuery = "SELECT cdisease.* FROM cdisease INNER JOIN (SELECT distinct diagcode FROM visitdiag)t1 ON t1.diagcode = cdisease.diseasecode";
            ResultSet rs = Service.Service.connectionSQL.getResultSet(cdiseaseQuery);
            return rs;
        }

        public ResultSet getRsQuery(String tableName, String village, String houseList) throws SQLException{
            if(tableName.equals("visit")){
                return this.getvisitQuery(village, houseList);
            } else if(tableName.equals("visitdiag")){
                return this.getOtherVisitQuery(tableName, village, houseList);
            } else if(tableName.equals("visitdrug")){
                return this.getOtherVisitQuery(tableName, village, houseList);
            } else if(tableName.equals("person")){
                return this.getPersonQuery(village, houseList);
            } else if(tableName.equals("personbehavior")){
                return this.getPersonbahaviorQuery(village, houseList);
            } else if(tableName.equals("house")){
                return this.getHouseQuery(village, houseList);
            } else if(tableName.equals("personchronic")){
                return this.getPersonchronicQuery(village, houseList);
            }  else if(tableName.equals("visitfp")){
                return this.getOtherVisitQuery(tableName, village, houseList);
            } else if(tableName.equals("visitlabcancer")){
                return this.getOtherVisitQuery(tableName, village, houseList);
            } else if(tableName.equals("visitlabblood")){
                return this.getOtherVisitQuery(tableName, village, houseList);
            } else if(tableName.equals("visitancdeliver")){
                return this.getOtherVisitQuery(tableName, village, houseList);
            } else if(tableName.equals("visitancmothercare")){
                return this.getOtherVisitQuery(tableName, village, houseList);
            } else if(tableName.equals("visitbabycare")){
                return this.getOtherVisitQuery(tableName, village, houseList);
            } else if(tableName.equals("visitnutrition")){
                return this.getOtherVisitQuery(tableName, village, houseList);
            }else if(tableName.equals("visitepi")){
                return this.getOtherVisitQuery(tableName, village, houseList);
            }else if(tableName.equals("visitdiagappoint")){
                return this.getOtherVisitQuery(tableName, village, houseList);
            }else if(tableName.equals("visitdentalcheck")){
                return this.getOtherVisitQuery(tableName, village, houseList);
            }else if(tableName.equals("visitdiag506address")){
                return this.getOtherVisitQuery(tableName, village, houseList);
            }else if(tableName.equals("visithomehealthindividual")){
                return this.getOtherVisitQuery(tableName, village, houseList);
            }else if(tableName.equals("visitscreenspecialdisease")){
                return this.getOtherVisitQuery(tableName, village, houseList);
            }else if(tableName.equals("visitanc")){
                return this.getOtherVisitQuery(tableName, village, houseList);
            }else if(tableName.equals("visitepiappoint")){
                return this.getOtherVisitQuery(tableName, village, houseList);
            }/*else if(tableName.equals("cdisease")){
                return this.getcdisease();
            }else if(tableName.equals("cdisease")){
                return this.getcdisease();
            }else if(tableName.equals("cdisease")){
                return this.getcdisease();
            }else if(tableName.equals("cdisease")){
                return this.getcdisease();
            }else if(tableName.equals("cdisease")){
                return this.getcdisease();
            }else if(tableName.equals("cdisease")){
                return this.getcdisease();
            }*/else{
                return null;
            }
        }

        public ResultSet getRsQueryAll(String tablename) throws SQLException{
            if(tablename.equals("person")||tablename.equals("personbehavior")||
               tablename.equals("house")||tablename.equals("personchronic")){
                return this.getAllQuery(tablename);
            }else{
                return this.getOtherVisitQueryAll(tablename);
            }
        }

        public ResultSet getOtherVisitQuery(String tableName, String village, String houseList) throws SQLException{
            String otherVisitQuery = "SELECT "+tableName+".* FROM "+tableName+",(SELECT visit.visitno FROM visit,(SELECT person.pid,person.pcucodeperson FROM person,house "
                + "WHERE person.hcode = house.hcode AND house.villcode IN (?) AND house.hno IN ('"+houseList+"'))t1 "
                + "WHERE dateupdate > '"+date+"' AND t1.pid = visit.pid AND t1.pcucodeperson = visit.pcucodeperson)t2 WHERE "+tableName+".visitno = t2.visitno";
            PreparedStatement pstmt = Service.Service.connectionSQL.connection.prepareStatement(otherVisitQuery);
            pstmt.setString(1, village);
            System.out.println(pstmt);
            return pstmt.executeQuery();
        }

        /////////////////////////////// get All village ////////////////////////////////////////////
        public ResultSet getAllQuery(String tablename) throws SQLException{
            String Query = "SELECT * FROM "+tablename;
            ResultSet rs = Service.Service.connectionSQL.getResultSet(Query);
            return rs;
        }

        public ResultSet getOtherVisitQueryAll(String tableName) throws SQLException{
            String otherVisitQuery = "SELECT * FROM "+tableName+" WHERE dateupdate > '"+date+"' ORDER BY dateupdate ASC;";
            ResultSet rs = Service.Service.connectionSQL.getResultSet(otherVisitQuery);
            return rs;
        }
        /////////////////////////////////////////////////////////////////////////////////////////////
                
    }


    private String getDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",java.util.Locale.US);
        Date datenow = new Date();
        String dateString = dateFormat.format(datenow).substring(4);
        String oneyearbefore = String.valueOf(datenow.getYear()+1900-1);
        return oneyearbefore+dateString;
    }
    
    
            
}
