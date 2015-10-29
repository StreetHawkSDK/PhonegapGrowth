package com.streethawk.growth;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import android.content.Context;
import com.streethawk.library.growth.IGrowth;
import com.streethawk.library.growth.Growth;
import android.app.Activity;

/**
 * This class echoes a string called from JavaScript.
 */
public class GrowthWrapper{

	private static GrowthWrapper mGrowthWrapper;

	public static GrowthWrapper getInstance(){
		if(null==mGrowthWrapper)
			mGrowthWrapper = new GrowthWrapper();
		return mGrowthWrapper;
	}

	/**
	 * originateShareWithCampaign returns URL back to application
	 */
	public void originateShareWithCampaign(final Activity activity,JSONArray args,final CallbackContext callback){

		String ID=null;
		String utm_source=null;
		String utm_medium=null;
		String campaign_content=null;
		String utm_term=null;
		String deeplinkUrl=null;
		String defaultUrl=null;

		try{
			ID = args.getString(0);
			utm_source = args.getString(1);
			utm_medium = args.getString(2);
			campaign_content = args.getString(3);
			utm_term = args.getString(4);
			deeplinkUrl = args.getString(5);
			defaultUrl = args.getString(6);
		}catch(JSONException e){
			e.printStackTrace();
		}
		Growth.getInstance(activity).getShareUrlForAppDownload(ID,deeplinkUrl,utm_source,utm_medium,utm_term,campaign_content,defaultUrl, new IGrowth() {
			@Override
			public void onReceiveShareUrl(String shareUrl) {
				if(null!=callback){
					PluginResult presult = new PluginResult(PluginResult.Status.OK,shareUrl);
					presult.setKeepCallback(true);
					callback.sendPluginResult(presult);
				}
			}
			@Override
			public void onReceiveErrorForShareUrl(JSONObject errorResponse) {
				PluginResult presult = new PluginResult(PluginResult.Status.ERROR,errorResponse);
				presult.setKeepCallback(true);
				callback.sendPluginResult(presult);
			}
		});
	}

	/**
	 * originateShareWithSourceSelection will invoke default source picker
	 */
	public void originateShareWithSourceSelection(final Activity activity,JSONArray args){
		String ID = null;
		String deeplinkUrl = null;
		String defaultUrl = null;
		try{
			ID = args.getString(0);
			deeplinkUrl = args.getString(1);
			defaultUrl = args.getString(2);
		}catch (JSONException e){
			e.printStackTrace();
		}
		Growth.getInstance(activity).getShareUrlForAppDownload(ID,deeplinkUrl,null,null,null,null,defaultUrl,null);
	}

}
