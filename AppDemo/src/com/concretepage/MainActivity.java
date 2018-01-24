package com.concretepage;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	private String[] names = {"Brahma", "Vishnu", "Mahesh"};
	private String[] numbers = {"7359753876","3865986365","5286746529"};
	@Override
    protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    for(int i=0; i< names.length;i++){
	    	writeContact(names[i], numbers[i]);
	    }
        TextView view = (TextView)findViewById(R.id.out);
        view.setText("Contact saved");
    }
	private void writeContact(String displayName, String number) {
	    ArrayList<ContentProviderOperation> contentProviderOperations = new ArrayList<ContentProviderOperation>();
	    //insert raw contact using RawContacts.CONTENT_URI
	    contentProviderOperations.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
	        .withValue(RawContacts.ACCOUNT_TYPE, null).withValue(RawContacts.ACCOUNT_NAME, null).build());   
	    //insert contact display name using Data.CONTENT_URI
        contentProviderOperations.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)
            .withValueBackReference(Data.RAW_CONTACT_ID, 0).withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
            .withValue(StructuredName.DISPLAY_NAME, displayName).build());
        //insert mobile number using Data.CONTENT_URI
        contentProviderOperations.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)
            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
            .withValue(Phone.NUMBER, number).withValue(Phone.TYPE, Phone.TYPE_MOBILE).build());
        try {
            getApplicationContext().getContentResolver().
					applyBatch(ContactsContract.AUTHORITY, contentProviderOperations);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			e.printStackTrace();
		}
     }
	 @Override
	 protected void onDestroy() {
   	         super.onDestroy();
	 }
	 @Override
	 public boolean onCreateOptionsMenu(Menu menu) {
		  return true;
	 }
}