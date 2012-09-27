
package vivek.com.medicinfo.library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import vivek.com.medicinfo.savedata;

public class UserFunctions {
	
	private JSONParser jsonParser;
	
	private static String loginURL = "http://server.m2workhackathon.org/medicinfo/user.php";
	private static String jobURL = "http://server.m2workhackathon.org/medicinfo/data.php";
	
	private static String login_tag = "login";
	private static String register_tag = "register";
	private static String logout_tag = "logout";
	private static String profile_tag = "profile";
	
	
	// constructor
	public UserFunctions(){
		jsonParser = new JSONParser();
	}
	

	public JSONObject loginUser(String email, String password){
	
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("action", login_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
		
		return json;
	}
	

	public JSONObject registerUser(String name, String email, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		
		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
		// return json
		return json;
	}
	
	
	public JSONObject requestwork(){
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("action", "request"));
		params.add(new BasicNameValuePair("token", savedata.tokenID));
		JSONObject json = jsonParser.getJSONFromUrl(jobURL, params);
		
		return json;
	}
	
	
	
	public JSONObject logout(String tokenID){
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("action", logout_tag));
		params.add(new BasicNameValuePair("token", tokenID));
		JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
		
		return json;
	}
	
	
	public boolean isUserLoggedIn()
	{
		return false;
	}
	
public JSONObject userprofile(){
		String tokenID = savedata.tokenID;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("action",profile_tag));
		params.add(new BasicNameValuePair("token", tokenID));
		JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
		
		return json;
	}


public JSONObject submitwork(String sUBMIT_id, String sUBMIT_valueofcategory,
		String sUBMIT_getPescription) {

	List<NameValuePair> params = new ArrayList<NameValuePair>();
	params.add(new BasicNameValuePair("action", "dataentry"));
	params.add(new BasicNameValuePair("token", savedata.tokenID));
	params.add(new BasicNameValuePair("id", sUBMIT_id));
	params.add(new BasicNameValuePair("category", sUBMIT_valueofcategory));
	params.add(new BasicNameValuePair("text", sUBMIT_getPescription));
	
	// getting JSON Object
	JSONObject json = jsonParser.getJSONFromUrl(jobURL, params);
	// return json
	return json;
}


public JSONObject verifywork(String sUBMIT_id, String sUBMIT_isverify) {

	List<NameValuePair> params = new ArrayList<NameValuePair>();
	params.add(new BasicNameValuePair("action", "dataverify"));
	params.add(new BasicNameValuePair("token", savedata.tokenID));
	params.add(new BasicNameValuePair("id", sUBMIT_id));
	params.add(new BasicNameValuePair("isverified", sUBMIT_isverify));
	
	JSONObject json = jsonParser.getJSONFromUrl(jobURL, params);
	// return json
	return json;
}
	

	
}
