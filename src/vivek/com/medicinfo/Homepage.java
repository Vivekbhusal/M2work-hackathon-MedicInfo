package vivek.com.medicinfo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class Homepage extends Activity implements OnClickListener{
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        // Set View to register.xml
	        setContentView(R.layout.homepage);
	        
	        ImageView myprofile = (ImageView) findViewById(R.id.imageprofile);
	        ImageView startjob = (ImageView) findViewById(R.id.imagestartjob);
	        ImageView instruction = (ImageView) findViewById(R.id.imageInstruction);
	        ImageView logout = (ImageView) findViewById(R.id.imagelogout);
	        
	        myprofile.setOnClickListener(this);
	        startjob.setOnClickListener(this);
	        instruction.setOnClickListener(this);
	        logout.setOnClickListener(this);
	                
	    }

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imageprofile:
			Intent profile = new Intent(getApplicationContext(), Myprofile.class);
			startActivity(profile);
			
			break;

		case R.id.imagestartjob:
			Intent job = new Intent(getApplicationContext(), beforestartingjob.class);
			startActivity(job);
					
			break;
		
		case R.id.imageInstruction:
			Intent ins = new Intent(getApplicationContext(), Instruction.class);
			startActivity(ins);
			break;
		
		case R.id.imagelogout:{
	
			new asyncConnection().execute();
			break;
		}
		
		default:
			break;
		}
		
	}
	
	private class asyncConnection extends AsyncTask<Void, Void, Void>{
    	
		@Override
		protected Void doInBackground(Void... params) {
		
			String URL = "http://server.m2workhackathon.org/medicinfo/user.php";
			
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("action", "logout"));
			param.add(new BasicNameValuePair("token", savedata.tokenID));
			
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(URL);
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(param));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			try {
				httpClient.execute(httpPost);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			savedata.tokenID="i";
			
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			Toast toast = Toast.makeText(getApplicationContext(), "Sucessfully Logged out", Toast.LENGTH_LONG);
			toast.show();
			Intent i = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(i);
			finish();
		}
		
		

 }

}
