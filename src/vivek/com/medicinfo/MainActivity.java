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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText Email, Password;
	Button Login;
	TextView Joinus, ErrorMsg;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
        setContentView(R.layout.login);
        ErrorMsg = (TextView) findViewById(R.id.errormsg);
        
        if(checkconnection.checkInternetConnection(this))
        {       	
        	
        	if(!savedata.tokenID.equalsIgnoreCase("i"))
            {
            	Intent i = new Intent(getApplicationContext(), Homepage.class);
    			startActivity(i);
            }
            

            Email = (EditText) findViewById(R.id.L_ETEmail);
            Password = (EditText) findViewById(R.id.L_ETPass);
            Joinus = (TextView) findViewById(R.id.link_to_register);
            Login = (Button) findViewById(R.id.btnLogin);
           
            Joinus.setOnClickListener(new View.OnClickListener() {
    			
    			public void onClick(View v) {
    				Intent i = new Intent(getApplicationContext(), Register.class);
    				startActivity(i);
    			}
    		});
            
            
            Login.setOnClickListener(new View.OnClickListener() {
    			
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				
    				String email = Email.getText().toString();
    				String password = Password.getText().toString();
    				if(email.length()==0 || password.length()==0)
    				{
    					Toast toast = Toast.makeText(getApplicationContext(), "Please enter Username/Password", Toast.LENGTH_SHORT);
    					toast.show();
    				}
    				else{
    					asyncConnection apiconnection = new asyncConnection();
    					apiconnection.execute(email,password);
    					
    				}
    			}
        		});
        }
        else{
        	final Dialog dialog = new Dialog(MainActivity.this);
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
        	ErrorMsg.setText("Check Internet Connection....");
		
        }
        
    }
    
    
    private class asyncConnection extends AsyncTask<String, Void, Void>{

    	String error_msg ;
    	String tokenID="i";
    	
    	
		@Override
		protected Void doInBackground(String... params) {
		
			UserFunctions userFunction = new UserFunctions();
			String email = params[0];
			String password = params[1];
			
			Log.i("username", email);
			Log.i("Password", password);
						
			JSONObject json = userFunction.loginUser(email, password);
			try {
				if (json.getString("success") != null) 
				{
					String res = json.getString("success"); 
					if(Integer.parseInt(res) == 1){
						
						String token = json.getString("token");
//						savedata.tokenID= token;
						tokenID = token;
//						savedata.logintime = (String) new Date().getTime();
									
					}
					else{
						
						error_msg = json.getString("error_msg");
						tokenID="i";
					}									
				}
				else{
					error_msg="Oops.. Something went wrong";
					tokenID="i";
				}
			
			
			
			}catch(JSONException e)
			{
				e.printStackTrace();
			}
			
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			
			if(tokenID.equalsIgnoreCase("i"))
			{
				ErrorMsg.setText(error_msg);
			}
			else
			{
				savedata.tokenID = tokenID;
				Toast toast = Toast.makeText(getApplicationContext(), "Successfully Logged In", Toast.LENGTH_SHORT);
				toast.show();
				Intent i = new Intent(getApplicationContext(), Homepage.class);
				startActivity(i);	
				finish();
			}
			
		}
		

        	
        }		
}
