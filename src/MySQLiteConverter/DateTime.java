/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MySQLiteConverter;

/**
 *
 * @author Administrator
 */
public class DateTime {
    int year;
    int mount;
    int day;
    int houre;
    int min;
    int sec;

    public DateTime() {

    }
    public DateTime(int year, int mount, int day, int houre, int min, int sec) {
        this.year = year;
        this.mount = mount;
        this.day = day;
        this.houre = houre;
        this.min = min;
        this.sec = sec;
    }

    public int qualsTime(DateTime dateTime){
        if(this.year == dateTime.year
                && this.mount == dateTime.mount
                && this.day == dateTime.day
                && this.houre == dateTime.houre
                && this.min == dateTime.min
                && this.sec == dateTime.sec){
            return 0;
        }
        if(this.year > dateTime.year){
            return 1;
        }else{
            if(this.year < dateTime.year){
                return -1;
            }
        }
        if(this.mount > dateTime.mount){
            return 1;
        }else{
            if(this.mount < dateTime.mount){
                return -1;
            }
        }
        if(this.day > dateTime.day){
            return 1;
        }else{
            if(this.day < dateTime.day){
                return -1;
            }
        }
        if(this.houre > dateTime.houre){
            return 1;
        }else{
            if(this.houre < dateTime.houre){
                return -1;
            }
        }
        if(this.min > dateTime.min){
            return 1;
        }else{
            if(this.min < dateTime.min){
                return -1;
            }
        }
        if(this.sec > dateTime.sec){
            return 1;
        }else{
            if(this.sec < dateTime.sec){
                return -1;
            }
        }
        return 0;
    }
    public void createDateTime(String DateTime){
        if(DateTime == null){
            this.year = 0;
            this.mount = 0;
            this.day = 0;
            this.houre = 0;
            this.min = 0;
            this.sec =0;
            return;
        }
        String [] temp = DateTime.split(" ");
        String []A =temp[0].split("-");
        this.year = Integer.parseInt(A[0]);
        this.mount = Integer.parseInt(A[1]);
        this.day = Integer.parseInt(A[2]);
        try{
            String []B = temp[1].split(":");
            this.houre = Integer.parseInt(B[0]);        
            this.min = Integer.parseInt(B[1]);
            this.sec = (int)Double.parseDouble(B[2]);
        }catch(ArrayIndexOutOfBoundsException ex){
            this.houre=0;
            this.min =0;
            this.sec =0;
        }
//        System.out.println();
//    System.out.println( year);
//    System.out.println( mount);
//    System.out.println( day);
//    System.out.println( houre);
//    System.out.println( min);
//    System.out.println( sec);
    }
    
//public static void main (String [] args){
//    DateTime dateTime = new DateTime();
//    dateTime.createDateTime("2010-01-08 19:21:25.0");
//
//}
}
