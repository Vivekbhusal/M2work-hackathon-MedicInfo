package vivek.com.medicinfo;

import org.json.JSONException;
import org.json.JSONObject;

import vivek.com.medicinfo.library.UserFunctions;
import vivek.com.medicinfo.library.checkconnection;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Myprofile extends Activity{
	
	TextView nameV, emailV, jobdoneV, amountV;
	
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        // Set View to register.xml
	        setContentView(R.layout.myprofile);
	        
	       
	        if(checkconnection.checkInternetConnection(this))
	        {
	        	nameV = (TextView) findViewById(R.id.MYProfile_TVname);
		        emailV = (TextView) findViewById(R.id.MYProfile_TVEmail);
		        jobdoneV = (TextView) findViewById(R.id.MYProfile_TVJobdone);
		        amountV = (TextView) findViewById(R.id.MYProfile_TVAmountEarned);
		        
		        asyncConnection downloadprofile = new asyncConnection();
		        downloadprofile.execute();
	        }
	        else{
	        	final Dialog dialog = new Dialog(Myprofile.this);
				 dialog.setContentView(R.layout.nointernet);
				 dialog.setTitle("Alert!!!");

				 Button button = (Button) dialog.findViewById(R.id.button1);
				
				 button.setOnClickListener(new OnClickListener() {
				     public void onClick(View v) {
				    		    	 
				    	 dialog.dismiss();
				    	 finish();
				     }
				     });
				 dialog.show();
	        }
	        
	        
	        
	        
	        
	    }

	 
	 private class asyncConnection extends AsyncTask<Void, Void, Void>{

	    	String error_msg ;
	    	String email, name, income, completed;
	    	JSONObject json;
	    	String res="0";
	    	
			@Override
			protected Void doInBackground(Void... params) {
			
				UserFunctions userFunction = new UserFunctions();
							
				json = userFunction.userprofile();
				try {
					if (json.getString("success") != null) 
					{
						res = json.getString("success"); 
						if(Integer.parseInt(res) == 1){
							
							JSONObject user = json.getJSONObject("user");
							email = user.getString("email");
							name = user.getString("name");
							
							JSONObject jobdetails = json.getJSONObject("jobdetails");
							income = jobdetails.getString("income");
							completed = jobdetails.getString("completed");
																
						}
						else{
							
							error_msg = json.getString("error_msg");
						}									
					}
					else{
						error_msg="Oops.. Something went wrong";
					}
				
				
				
				}catch(JSONException e)
				{
					e.printStackTrace();
				}
				
				
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				 
				if(Integer.parseInt(res)==0)
				{
					Toast toast = Toast.makeText(getApplicationContext(), error_msg, Toast.LENGTH_SHORT);
					toast.show();
					Intent i = new Intent(getApplicationContext(), Homepage.class);
					startActivity(i);	
					
				}
				else{
					nameV.setText(name);
					emailV.setText(email);
					jobdoneV.setText(completed);
					amountV.setText(income);
					
				}
				
				
				
			}
	 }

}
