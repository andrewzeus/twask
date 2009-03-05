/*
 * miniContacts
 * Copyright (C) 2007-2008 Matías Molinas
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/


package com.doublesunflower;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.doublesunflower.core.actions.contacts.OpenContactListAction;
import com.doublesunflower.core.actions.map.OpenMapAction;
import com.doublesunflower.core.actions.web.OpenWebAction;
import com.doublesunflower.core.common.Action;
import com.doublesunflower.core.common.exceptions.BusinessException;
import com.doublesunflower.core.ui.base.RowItem;
import com.doublesunflower.core.ui.util.Util;

import com.doublesunflower.twask.view.activities.Twask;

public class MiniContacts extends ListActivity {

	public static final int CONTACTS_MENU = 0;
	public static final int TASK_MENU = 1;
	public static final int MAPS_MENU = 2;
	public static final int EXIT_MENU = 3;

	protected int selectedMenu;
	protected ListAdapter listAdapter;
	protected ArrayList<RowItem> contactsMenuItems;
	protected ArrayList<RowItem> applicationMenuItems;
	protected ArrayList<RowItem> mapsMenuItems;
	protected ArrayList<RowItem> exitMenuItems;
	
	protected ImageButton btnSearch = null;
	protected ImageButton btnContacts = null;
	protected ImageButton btnApplication = null;
	protected ImageButton btnMaps = null;
	protected ImageButton btnExit = null;
	
	protected ImageView iconTitle = null;
	protected TextView txtTitle = null;
	
	protected Action openContactListAction = null;
	protected Action openMapAction = null;
	protected Action openWebAction = null;

	@Override
	public void onCreate(Bundle icicle) {
		try {
			super.onCreate(icicle);
			setContentView(R.layout.main);
			initializeUI();
			selectMenu(TASK_MENU);
		} catch (Exception ex) {
			Util.showErrorDialog(this, ex);
		}
	}
	
	protected void selectMenu(int menu){
		selectedMenu = menu;
		switch (menu) {
			case (CONTACTS_MENU): {
				iconTitle.setImageDrawable(getResources().getDrawable(R.drawable.system_users));
				txtTitle.setText(R.string.menu_contacts);
				break;
			}
			case (TASK_MENU): {
				iconTitle.setImageDrawable(getResources().getDrawable(R.drawable.appointment_new));
				txtTitle.setText(R.string.menu_task);
				break;
			}
			case (MAPS_MENU): {
				iconTitle.setImageDrawable(getResources().getDrawable(R.drawable.world));
				txtTitle.setText(R.string.menu_maps);
				break;
			}
			case (EXIT_MENU): {
				iconTitle.setImageDrawable(getResources().getDrawable(R.drawable.system_logout));
				txtTitle.setText(R.string.menu_exit);
				break;
			}
		}
		loadMenu();
	}

	protected void initializeUI() {
		
		openContactListAction = new OpenContactListAction();
		openMapAction = new OpenMapAction();
		openWebAction = new OpenWebAction();
		
		TableRow trToolbar = (TableRow) findViewById(R.id.trToolbar);
		//trToolbar.setBackgroundDrawable(getWallpaper());
		
		iconTitle = (ImageView) findViewById(R.id.iconTitle);
		txtTitle = (TextView) findViewById(R.id.txtTitle);
		
		btnSearch = (ImageButton) findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	Search();
            }
        }); 

		btnContacts = (ImageButton) findViewById(R.id.btnContacts);
		btnContacts.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	selectMenu(CONTACTS_MENU);
            }
        }); 
		
		btnApplication = (ImageButton) findViewById(R.id.btnApplication);
		btnApplication.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	selectMenu(TASK_MENU);
            }
        }); 
		
		btnMaps = (ImageButton) findViewById(R.id.btnMaps);
		btnMaps.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	selectMenu(MAPS_MENU);
            }
        }); 
		
		btnExit = (ImageButton) findViewById(R.id.btnExit);
		btnExit.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	selectMenu(EXIT_MENU);
            }
        }); 

		TableRow trTitle = (TableRow) findViewById(R.id.trTitle);
		//trTitle.setBackgroundDrawable(getWallpaper());

		contactsMenuItems = new ArrayList<RowItem>();
		contactsMenuItems.add(new RowItem(
				getResources().getString(R.string.menu_contacts_item_contacts),
				getResources().getString(R.string.msg_in_operation),
				getResources().getDrawable(R.drawable.system_users)));
		
		

		applicationMenuItems = new ArrayList<RowItem>();
		applicationMenuItems.add(new RowItem(
				getResources().getString(R.string.menu_tasks_item_task),
				getResources().getString(R.string.msg_in_operation),
				getResources().getDrawable(R.drawable.document_new)));
		applicationMenuItems.add(new RowItem(
				getResources().getString(R.string.menu_tasks_item_tag),
				getResources().getString(R.string.msg_in_operation),
				getResources().getDrawable(R.drawable.tag_find)));
		
		

		mapsMenuItems = new ArrayList<RowItem>();
		mapsMenuItems.add(new RowItem(
				getResources().getString(R.string.menu_maps_item_favorite_places),
				getResources().getString(R.string.msg_in_operation),
				getResources().getDrawable(R.drawable.world)));
		
		

		exitMenuItems = new ArrayList<RowItem>();
		exitMenuItems.add(new RowItem(
				getResources().getString(R.string.menu_exit_item_exit),
				getResources().getString(R.string.msg_in_operation),
				getResources().getDrawable(R.drawable.emblem_unreadable)));
	}
	
	protected void Search(){
		new AlertDialog.Builder(this)            	
        .setTitle(getResources().getString(R.string.title_search_dialog))
        .setItems(R.array.items_global_search_dialog,
        	new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int selectedItem) {
            	String[] syncOptions = 
            		getResources().getStringArray(R.array.items_global_search_dialog); 
            	if (selectedItem==0){
            		GoogleSearch();
            	}else{
            		new AlertDialog.Builder(MiniContacts.this)                        
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
            }
         })
       .show();
	}
	
	protected void GoogleSearch(){
		try {
			openWebAction.perform(this, null);
		} catch (BusinessException be) {
			Util.showErrorDialog(this, be);
		}
	}

	private void loadMenu() {
		switch (selectedMenu) {
			case (CONTACTS_MENU): {
				listAdapter = new SimpleAdapter(
						this,
						contactsMenuItems,
						R.layout.row,
						/*new String[] {RowItem.ICON, RowItem.ROW_TEXT_1, RowItem.ROW_TEXT_2},
		                new int[]{ R.id.iconRow, R.id.txtRow1, R.id.txtRow2});*/
						new String[] {RowItem.ROW_TEXT_1, RowItem.ROW_TEXT_2},
		                new int[]{R.id.txtRow1, R.id.txtRow2});
				break;
			}
			case (TASK_MENU): {
				listAdapter = new SimpleAdapter(
						this,
						applicationMenuItems,
						R.layout.row,
						new String[] {RowItem.ROW_TEXT_1, RowItem.ROW_TEXT_2},
		                new int[]{ R.id.txtRow1, R.id.txtRow2});
				break;
			}
			case (MAPS_MENU): {
				listAdapter = new SimpleAdapter(
						this,
						mapsMenuItems,
						R.layout.row,
						new String[] {RowItem.ROW_TEXT_1, RowItem.ROW_TEXT_2},
		                new int[]{R.id.txtRow1, R.id.txtRow2});
				break;
			}
			case (EXIT_MENU): {
				listAdapter = new SimpleAdapter(
						this,
						exitMenuItems,
						R.layout.row,
						new String[] {RowItem.ROW_TEXT_1, RowItem.ROW_TEXT_2},
		                new int[]{R.id.txtRow1, R.id.txtRow2});
				break;
			}
		}

		// Display it
		setListAdapter(listAdapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		selectMenuItem(position);
	}
	
	protected void selectMenuItem(int menuItem){
		try {
			switch (selectedMenu) {
				case (CONTACTS_MENU): {
					switch (menuItem) {
						case 0: {//System Contacts
							openContactListAction.perform(this, null);
							break;
						}
					}
					break;
				}
				case (TASK_MENU): {
					switch (menuItem) {
					case 0: {//Tasks
						//showUnderConstructionActivity();
						Intent twaskListIntent = new Intent(this, Twask.class);
				        twaskListIntent.putExtra(Twask.VARIABLES_TAG, (Bundle)null);
				        startActivity(twaskListIntent);
				        //finish();
						break;
					}
					case 1: {//Tags
						showUnderConstructionActivity();
						break;
					}
				}
				break;
				}
				case (MAPS_MENU): {
					switch (menuItem) {
						case 0: {//Favorite places..
							openMapAction.perform(this, null);
							break;
						}
					}
					break;
				}
				case (EXIT_MENU): {
					switch (menuItem) {
						case 0: {//Exit
							new AlertDialog.Builder(MiniContacts.this)            	
			                .setTitle(getResources().getString(R.string.dialog_title_question))
			                .setMessage(getResources().getString(R.string.dialog_exit_question))
			                .setIcon(R.drawable.help_browser)
			                .setPositiveButton(getResources().getString(R.string.dialog_button_yes)
			                		, new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface dialog, int whichButton) {
			                       setResult(RESULT_OK);
			                       finish();
			                    }
			                })
			               .setNegativeButton(getResources().getString(R.string.dialog_button_no),
			            		   new DialogInterface.OnClickListener() {
			                   public void onClick(DialogInterface dialog, int whichButton) {
			                       
			                   }
			               })
			               .show();
							break;
						}
					}
					break;
				}
			}
			
		} catch (BusinessException be) {
			Util.showErrorDialog(this, be);
		}
	}
	
	protected void showUnderConstructionActivity(){
		//TODO:TEMPORAL!!!!!!!!!
		Intent i = new Intent();
		i.setClassName( "com.minicontacts", "com.minicontacts.core.ui.util.UnderConstructionActivity" );
		startActivity( i );
	}

}