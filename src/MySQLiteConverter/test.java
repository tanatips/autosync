/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MySQLiteConverter;

/**
 *
 * @author Administrator
 */
public class test {


    public static void main(String [] args){
      DateTime dateTime = new DateTime();
      dateTime.createDateTime("2011-03-21 13:12:11");
        System.out.println(dateTime.day);
        System.out.println(dateTime.mount);
        System.out.println(dateTime.year);
        System.out.println(dateTime.houre);
        System.out.println(dateTime.min);
        System.out.println(dateTime.sec);

    }

}
