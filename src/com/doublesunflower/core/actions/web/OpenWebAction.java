package com.doublesunflower.core.actions.web;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.doublesunflower.core.actions.base.AbstractAction;
import com.doublesunflower.core.common.exceptions.BusinessException;

public class OpenWebAction extends AbstractAction {

	@Override
	public Object perform(Activity activity, Object params) throws BusinessException {
		try{
			Intent i = new Intent();
			i.setClassName( "com.minicontacts", "com.minicontacts.core.ui.web.WebActivity" );
		    activity.startActivity(i);
		}catch(Exception ex){
			Log.e("Error", ex.getMessage());
			throw new BusinessException(ex);
		}
		return null;
	}

}
