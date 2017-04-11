/*
 * Author: VincentChou
 * Date: 2017.4.11
 * Version: 1.0
 */
package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import util.MD5Util;

public class UploadStepsService extends Service<Void> {
	private int steps;
	private String uid;
	private String pcValue;

	public UploadStepsService(int steps, String uid, String pcValue) {
		this.steps = steps;
		this.uid = uid;
		this.pcValue = pcValue;
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				upload();
				return null;
			}
		};
	}

	private void upload() throws Exception {
		Gson gson = new Gson();
		Object[] array = { new Stats(steps) };
		String stats = gson.toJson(array);

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://walk.ledongli.cn/rest/dailystats/upload/v3?uid=" + uid);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("pc", pcValue));
		nvps.add(new BasicNameValuePair("stats", stats));
		// [{"calories":246.2754725221026,"date":1491840000,"distance":5741.625240000003,"duration":4527.243999481201,"steps":8073,"key":"9aecce044510df3e1215ae0812d48123"}]
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpClient.execute(httpPost);
		int status = response.getStatusLine().getStatusCode();
		HttpEntity entity = response.getEntity();
		if (status == 500)
			throw new ClientProtocolException("Uid does not exist!");
		else if (status < 200 || status >= 300)
			throw new ClientProtocolException("Unexpected response status: " + status);
		if (entity == null)
			throw new ClientProtocolException("Response entity is null");
		Map<String, JsonElement> retJson = gson.fromJson(EntityUtils.toString(entity), new TypeToken<Map<String, JsonElement>>(){}.getType());
		if (retJson.get("errorCode").getAsInt() != 0)
			throw new Exception(retJson.get("ret").toString());
	}
}

class Stats {
	@SerializedName("calories")
	private double mCalories;

	@SerializedName("date")
	private long mDate = new Date().getTime() / 1000;

	@SerializedName("distance")
	private double mDistance;

	@SerializedName("duration")
	private double mDuration;

	@SerializedName("steps")
	private int mSteps;

	@SerializedName("key")
	private String mToken = "";

	public Stats(int steps) {
		mCalories = mDistance = mDuration = mSteps = steps;
		generateKey();
	}

	public void generateKey() {
		Object[] arrayOfObject = new Object[6];
		arrayOfObject[0] = "ldl_pro";
		arrayOfObject[1] = ((int) this.mDate + "");
		arrayOfObject[2] = (this.mSteps + "");
		arrayOfObject[3] = ((int) this.mDistance + "");
		arrayOfObject[4] = ((int) this.mCalories + "");
		arrayOfObject[5] = ((int) this.mDuration + "");
		this.mToken = MD5Util.MD5(String.format("%s@%s#%s$%s^%s&%s", arrayOfObject)).toLowerCase();
	}
}