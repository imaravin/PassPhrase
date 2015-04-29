package me.aravinth.passphrase;


/**
 * Created by aravinth on 17-Apr-15.
 */
public class Random {


    public static String getPassword(int length,boolean small,boolean number,boolean alphanumeric ,boolean uppercase,boolean spl) {
         String lower = "abcdefghijklmnopqrstuvwxyz";
         String numchar="1234567890";
         String splchar="!@#$%&&*.?";
        String alphanum="abcdefghijklmnopqrstuvwxyz1234567890";
         String upper="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String value="";
        if(small)
            value=value+lower;
        if(number)
            value=value+numchar;
        if(alphanumeric)
            value=value+alphanum;
        if(uppercase)
            value=value+upper;
        if(spl)
            value=value+splchar;
        StringBuffer sb=new StringBuffer();
        java.util.Random rnd=new java.util.Random();

        while(length>0)
        {
            sb.append(value.charAt(rnd.nextInt(value.length())));
           length--;
        }

    return  sb.toString();
    }
}
