package com.doublesunflower.core.ui.maps;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.doublesunflower.R;
import com.doublesunflower.core.ui.util.Util;
import com.google.android.maps.MapView;

public class MapActivity extends com.google.android.maps.MapActivity {
	
	private static MapView mapview;
	
	protected ImageButton btnBack;
    protected ImageButton btnNew;
    protected ImageButton btnSearch;
    protected ImageButton btnSynchronize;
    
    protected String query = "";
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
    	super.onCreate(icicle);
    	
        setContentView(R.layout.map);
        
        mapview = (MapView)findViewById(R.id.mapview);
        
        initializeUI();
    }
    
    protected void doSearch(){
		try{
			//TODO:
		}catch (Exception ex){
			Util.showErrorDialog(this, ex);
		}
	}
    
    protected void initializeUI(){
		TableRow trToolbar = (TableRow) findViewById(R.id.trToolbar);
		//trToolbar.setBackground(android.R.drawable.statusbar_background);
		
		TableRow trTitle = (TableRow) findViewById(R.id.trTitle);
		//trTitle.setBackground(android.R.drawable.list_selector_background_focus);
		
		btnBack = (ImageButton)findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener(){
            // @Override
            public void onClick(View arg) {
            	onClickBtnBack();
            }
		});  
		
		btnNew = (ImageButton)findViewById(R.id.btnNew);
		btnNew.setOnClickListener(new OnClickListener(){
            // @Override
            public void onClick(View arg) {
            	onClickBtnNew();
            }
		});    
		
		btnSearch = (ImageButton)findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(new OnClickListener(){
            // @Override
            public void onClick(View arg) {
            	onClickBtnSearch();
            }
		});       
		
		btnSynchronize = (ImageButton)findViewById(R.id.btnSynchronize);
		btnSynchronize.setOnClickListener(new OnClickListener(){
            // @Override
            public void onClick(View arg) {
            	onClickBtnSynchronize();
            }
		});    
		
		TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
		txtTitle.setText(getResources().getString(R.string.menu_maps_item_favorite_places));
		
		ImageView iconTitle = (ImageView) findViewById(R.id.iconTitle);
		iconTitle.setImageDrawable(getResources().getDrawable(R.drawable.world));
    }
    
    protected void onClickBtnBack(){
		finish();
	}
    
    protected void onClickBtnNew(){
		//TODO:
	}
	
	protected void onClickBtnSearch(){
		new AlertDialog.Builder(this)            	
        .setTitle(getResources().getString(R.string.title_search_dialog))
        .setItems(R.array.items_search_dialog,
        	new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int selectedItem) {
            	String[] syncOptions = 
            		getResources().getStringArray(R.array.items_search_dialog); 
        		new AlertDialog.Builder(MapActivity.this)                        
                .setMessage(
                		getResources().getString(R.string.msg_search_option)
                		+ " " + syncOptions[selectedItem]
                		+ " " + getResources().getString(R.string.msg_not_available))                       
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        
                    }
                 })
                .show();
            }
         })
       .show();
	}
	
	public void onNewIntent(final Intent newIntent)
	{
		super.onNewIntent(newIntent);
		if (Intent.ACTION_SEARCH.equals(newIntent.getAction()))
		{
			query = newIntent.getStringExtra(SearchManager.QUERY);
			doSearch();
		}
	}
	
	
	protected void onClickBtnSynchronize(){
		new AlertDialog.Builder(this)            	
        .setTitle(getResources().getString(R.string.title_synchronize_dialog))
        .setItems(R.array.items_synchronize_dialog,
        	new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int selectedItem) {
            	String[] syncOptions = 
            		getResources().getStringArray(R.array.items_synchronize_dialog);                    	                    	
                new AlertDialog.Builder(MapActivity.this)                        
                .setMessage(
                		getResources().getString(R.string.msg_sync_option)
                		+ " " + syncOptions[selectedItem]
                		+ " " + getResources().getString(R.string.msg_not_available))                       
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        
                    }
                 })
                .show();
            }
         })
       .show();
	}
    
    public static void zoomIn() {
    	//mapview.getController().zoomTo(mapview.getZoomLevel() + 1);
    }
    
    public static void zoomOut() {
    	//mapview.getController().zoomTo(mapview.getZoomLevel() - 1);
    }
    
    public static void satellite() {
    	mapview.isSatellite();
    }
    
    public static void traffic() {
    	mapview.isTraffic();
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
}
