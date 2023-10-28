package in.co.psd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SessionManager {

    SharedPreferences sharedprefernce;
    SharedPreferences.Editor editor;

    Context context;
    int PRIVATE_MODE=0;

    private static final String PREF_NAME = "sharedcheckLogin";
    private static final String USERID = "userid";
    private static final String AUTHKEY = "authkey";
    private static final String IS_LOGIN = "islogin";
    private static final String IS_MOBILENO = "ismobileno";
    private static final String IS_PASSWORD = "ispassword";


    public SessionManager(Context context){

        this.context =  context;
        sharedprefernce = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedprefernce.edit();
    }

    public void setISMOBILENO(String mobileNO){

        editor.putString(IS_MOBILENO,mobileNO);
        editor.commit();

    }

    public String getISMOBILENO(){

        return  sharedprefernce.getString(IS_MOBILENO,"defvalue");
    }

    public void setISPASSWORD(String password){

        editor.putString(IS_PASSWORD,password);
        editor.commit();

    }

    public String getISPASSWORD(){

        return  sharedprefernce.getString(IS_PASSWORD,"defvalue");
    }

    public void setUSERID(String userid){

        editor.putString(USERID,userid);
        editor.commit();

    }

    public String getUSERID(){

        return  sharedprefernce.getString(USERID,"defvalue");
    }

    public void setAUTHKEY(String authkey){

        editor.putString(AUTHKEY,authkey);
        editor.commit();

    }

    public String getAUTHKEY(){

        return  sharedprefernce.getString(AUTHKEY,"defvalue");
    }

    public Boolean isLogin(){
        return sharedprefernce.getBoolean(IS_LOGIN, false);

    }
    public void setLogin(){

        editor.putBoolean(IS_LOGIN, true);
        editor.commit();

    }



    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);


    }

}
