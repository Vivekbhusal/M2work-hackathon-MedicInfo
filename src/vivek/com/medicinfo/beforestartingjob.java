package vivek.com.medicinfo;

import org.json.JSONException;
import org.json.JSONObject;

import vivek.com.medicinfo.library.UserFunctions;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class beforestartingjob extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.beforejobstart);
		
		asyncConnection downloadjobtype = new asyncConnection();
        downloadjobtype.execute();
		
		
	}
	
	 private class asyncConnection extends AsyncTask<Void, Void, Void>{
		 	
		 	String error_msg ;
	    	JSONObject json;
	    	String res="0";
	    	
	    	String type, id, image, category, text;
	    	
			@Override
			protected Void doInBackground(Void... params) {
				
				UserFunctions userFunction = new UserFunctions();
				
				json = userFunction.requestwork();
				try {
					if (json.getString("success") != null) 
					{
						res = json.getString("success"); 
						if(Integer.parseInt(res) == 1){
							
							JSONObject job = json.getJSONObject("job");
								type = job.getString("type");
								id = job.getString("id");
								image = job.getString("image");
								category = job.getString("category");
								text = job.getString("text");									
						}
						else{
							
							error_msg = json.getString("error_msg");
							Log.i("errormsg",error_msg);
						}									
					}
					else{
						error_msg="Oops.. Something went wrong";
						Log.i("errormsg",error_msg);
					}
				}catch(JSONException e)
				{
					e.printStackTrace();
				}
				
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				
				if(Integer.parseInt(res)==1)
				{
					if(type.equalsIgnoreCase("dataentry"))
					{
						Intent i = new Intent(getApplicationContext(), StartJob.class);
							i.putExtra("type", type);
							i.putExtra("id", id);
							i.putExtra("image", image);
							i.putExtra("category", category);
							i.putExtra("text", text);
						startActivity(i);	
						finish();					
					}
					else
					{
						Intent i = new Intent(getApplicationContext(), startverifyjob.class);
							i.putExtra("type", type);
							i.putExtra("id", id);
							i.putExtra("image", image);
							i.putExtra("category", category);
							i.putExtra("text", text);
						startActivity(i);
						finish();
					}
				}
				else{
					Intent i = new Intent(getApplicationContext(), logout.class);
					i.putExtra("msg", error_msg);
					startActivity(i);
					finish();
				}
				
				 
			}
	 }
	

}
