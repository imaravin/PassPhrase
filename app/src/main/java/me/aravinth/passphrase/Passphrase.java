package me.aravinth.passphrase;

/**
 * Created by aravinth on 08-May-15.
 */
public class Passphrase {
    public static String passPhrase(int length,boolean lower,boolean upper,boolean number,boolean spl)
    {
        char[] spl2={'!','@','#','$','%','^','&','*','(',')','.',',','"',':'};
        char[] lowspl={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','!','@','#','$','%','^','&','*','(',')','.',',','"',':'};
        char[] upspl={'A','B','C','D','E','F','G','H','I','J','K','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','!','@','#','$','%','^','&','*','(',')','.',',','"',':'};
        char[] mix={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','!','@','#','$','%','^','&','*','(',')','.',',','"',':'};
        char[] splnum={'!','@','#','$','%','^','&','*','(',')','.',',','"',':','1','2','3','4','5','6','7','8','9','0'};
        if(lower&&upper&&number&&spl)
            return RandomStringUtils.randomAscii(length);
        if(lower&&!upper&&number&&spl)
            return RandomStringUtils.randomAscii(length).toLowerCase();
        if(!lower&&upper&&number&&spl)
            return RandomStringUtils.randomAscii(length).toUpperCase();

        if(lower&&upper&&!number&&!spl)
            return RandomStringUtils.randomAlphabetic(length);
        if(lower&&!upper&&!number&&!spl)
            return RandomStringUtils.randomAlphabetic(length).toLowerCase();
        if(!lower&&upper&&!number&&!spl)
            return RandomStringUtils.randomAlphabetic(length).toUpperCase();

        if(!lower&&!upper&&number&&!spl)
            return RandomStringUtils.randomNumeric(length);


        if(lower&&upper&&number&&!spl)
            return RandomStringUtils.randomAlphanumeric(length);
        if(lower&&!upper&&number&&!spl)
            return RandomStringUtils.randomAlphanumeric(length).toLowerCase();
        if(!lower&&upper&&number&&!spl)
            return RandomStringUtils.randomAlphanumeric(length).toUpperCase();


        if(!lower&&!upper&&number && spl)
            return RandomStringUtils.random(length, splnum);

        if(!lower&&!upper&&!number && spl)
            return RandomStringUtils.random(length,spl2);


        if(lower&&upper&&!number&&spl)
            return RandomStringUtils.random(length,mix);
        if(lower&&!upper&&!number&&spl)
            return RandomStringUtils.random(length,lowspl);
        if(!lower&&upper&&!number&&spl)
            return RandomStringUtils.random(length,upspl);

        return "";
    }




}
