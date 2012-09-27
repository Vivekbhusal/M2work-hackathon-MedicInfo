package vivek.com.medicinfo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Register extends Activity{
	
	Button signup;
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        // Set View to register.xml
	        setContentView(R.layout.signup);
	        
	        signup = (Button) findViewById(R.id.btnsignup);
	        signup.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
				
					
				}
			});
	        
	        
	    }

}
