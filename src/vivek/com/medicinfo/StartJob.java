package vivek.com.medicinfo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import vivek.com.medicinfo.library.UserFunctions;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public class StartJob extends Activity implements OnTouchListener{
	
	String type, id, image, category, text;
	ImageView jobimage;
	EditText pescription;
	Spinner categorylist;
	Button submit;
	
	public LinearLayout  progressSV;
	public RelativeLayout workscreen;
	
	Bitmap mIcon_val = null;
	URL newurl = null;
		
	private static final String TAG = "Touch";
	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();

	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;

	PointF start = new PointF();
	PointF mid = new PointF();
	float oldDist = 1f;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	       
	        setContentView(R.layout.startjob);
	        
	        Bundle extras = getIntent().getExtras();
	        if (extras != null) {
	            type = extras.getString("type");
	            id = extras.getString("id");
	            image = extras.getString("image");
	            category = extras.getString("category");
	            text = extras.getString("text");
	        }
	        else
	        {
	        	Intent i = new Intent(getApplicationContext(), logout.class);
	        	startActivity(i);
	        	finish();
	        }
	        
	        progressSV = (LinearLayout) findViewById(R.id.loading_sv);
	        workscreen = (RelativeLayout) findViewById(R.id.workscreen);
	        
	        jobimage = (ImageView) findViewById(R.id.startjobImage);
	        pescription = (EditText) findViewById(R.id.ETPescription);
	        categorylist = (Spinner) findViewById(R.id.category);
	        submit = (Button) findViewById(R.id.btnSubmit);
	             
	        YourDownload abc = new YourDownload();
			abc.execute();
			jobimage.setOnTouchListener(this);
			if(text.equalsIgnoreCase("null"))
			{
				pescription.setText("Tap here to write..");
			}
			else
			{
				pescription.setText(text);
			}
			
			List<String> list = new ArrayList<String>();
			list.add("det_history");
			list.add("det_diagnosis");
			list.add("det_prescription");
			
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        	categorylist.setAdapter(dataAdapter);
        	
        	
        	submit.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					
					String valueofcategory = String.valueOf(categorylist.getSelectedItem());
					String getPescription = pescription.getText().toString();
					
					 submitdata datasubmit = new submitdata();
						datasubmit.execute(id,valueofcategory, getPescription);
					
				}
			});
			 
	    }
	 

	 private class submitdata extends AsyncTask<String, Void, Void> {

		 String error_msg;
			
			@Override
	        protected void onPreExecute() {
				workscreen.setVisibility(View.GONE);
	 			progressSV.setVisibility(View.VISIBLE);
					 			
	 		}
	        @Override
	        protected Void doInBackground(String... params) {
	        
	        	String SUBMIT_id = params[0];
	        	String SUBMIT_valueofcategory = params[1];
	        	String SUBMIT_getPescription = params[2];
	        	
	        	UserFunctions userFunction = new UserFunctions();
							
				JSONObject json = userFunction.submitwork(SUBMIT_id, SUBMIT_valueofcategory,SUBMIT_getPescription);
				try {
					if (json.getString("success") != null) 
					{
						String res = json.getString("success"); 
						if(Integer.parseInt(res) == 1){
							
							error_msg = "Sucessfully Submitted..."+"Start another job";
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
	     
	        	Intent i = new Intent(getApplicationContext(), afterjobsubmit.class);
					i.putExtra("error_msg", error_msg);
				startActivity(i);	
				finish();
	    }
	}
	  
	 
	 private class YourDownload extends AsyncTask<Void, Void, Void> {

			
			@Override
	        protected void onPreExecute() {
				workscreen.setVisibility(View.GONE);
	 			progressSV.setVisibility(View.VISIBLE);
	 			
	 		}
	        @Override
	        protected Void doInBackground(Void... params) {
	        
	    		try {
	    			String FUll_URL = "http://server.m2workhackathon.org/medicinfo/infoimages/"+image;
	    			newurl = new URL(FUll_URL);
	    			
	    			mIcon_val = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
	    		} catch (MalformedURLException e) {
	    			e.printStackTrace();
	    		}  catch (IOException e) {
	    			e.printStackTrace();
	    		} 
	            
				return null;
	        }
	        @Override
	        protected void onPostExecute(Void result) {
	     
	        	progressSV.setVisibility(View.GONE);
	        	workscreen.setVisibility(View.VISIBLE);
	        	jobimage.setImageBitmap(mIcon_val);   	
	    }
	}
	 
	 
	 

	public boolean onTouch(View v, MotionEvent event) {
		ImageView view = (ImageView) v;

	      // Dump touch event to log
	    dumpEvent(event);

	      // Handle touch events here...
	      switch (event.getAction() & MotionEvent.ACTION_MASK) {
	      case MotionEvent.ACTION_DOWN:
	         savedMatrix.set(matrix);
	         start.set(event.getX(), event.getY());
	         Log.d(TAG, "mode=DRAG");
	         mode = DRAG;
	         break;
	      case MotionEvent.ACTION_POINTER_DOWN:
	         oldDist = spacing(event);
	         Log.d(TAG, "oldDist=" + oldDist);
	         if (oldDist > 10f) {
	            savedMatrix.set(matrix);
	            midPoint(mid, event);
	            mode = ZOOM;
	            Log.d(TAG, "mode=ZOOM");
	         }
	         break;
	      case MotionEvent.ACTION_UP:
	      case MotionEvent.ACTION_POINTER_UP:
	         mode = NONE;
	         Log.d(TAG, "mode=NONE");
	         break;
	      case MotionEvent.ACTION_MOVE:
	         if (mode == DRAG) {
	            // ...
	            matrix.set(savedMatrix);
	            matrix.postTranslate(event.getX() - start.x,
	                  event.getY() - start.y);
	         }
	         else if (mode == ZOOM) {
	            float newDist = spacing(event);
	            Log.d(TAG, "newDist=" + newDist);
	            if (newDist > 10f) {
	               matrix.set(savedMatrix);
	               float scale = newDist / oldDist;
	               matrix.postScale(scale, scale, mid.x, mid.y);
	            }
	         }
	         break;
	      }

	      view.setImageMatrix(matrix);
	      return true; // indicate event was handled
	   }

	   /** Show an event in the LogCat view, for debugging */
	   private void dumpEvent(MotionEvent event) {
	      String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
	            "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
	      StringBuilder sb = new StringBuilder();
	      int action = event.getAction();
	      int actionCode = action & MotionEvent.ACTION_MASK;
	      sb.append("event ACTION_").append(names[actionCode]);
	      if (actionCode == MotionEvent.ACTION_POINTER_DOWN
	            || actionCode == MotionEvent.ACTION_POINTER_UP) {
	         sb.append("(pid ").append(
	               action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
	         sb.append(")");
	      }
	      sb.append("[");
	      for (int i = 0; i < event.getPointerCount(); i++) {
	         sb.append("#").append(i);
	         sb.append("(pid ").append(event.getPointerId(i));
	         sb.append(")=").append((int) event.getX(i));
	         sb.append(",").append((int) event.getY(i));
	         if (i + 1 < event.getPointerCount())
	            sb.append(";");
	      }
	      sb.append("]");
	      Log.d(TAG, sb.toString());
	   }

	   /** Determine the space between the first two fingers */
	   private float spacing(MotionEvent event) {
	      float x = event.getX(0) - event.getX(1);
	      float y = event.getY(0) - event.getY(1);
	      return FloatMath.sqrt(x * x + y * y);
	   }

	   /** Calculate the mid point of the first two fingers */
	   private void midPoint(PointF point, MotionEvent event) {
	      float x = event.getX(0) + event.getX(1);
	      float y = event.getY(0) + event.getY(1);
	      point.set(x / 2, y / 2);
	}
	 
	
}
