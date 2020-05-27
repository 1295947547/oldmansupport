package com.example.oldmansupport.li;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.Contacts.People;
import android.provider.ContactsContract.PhoneLookup;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oldmansupport.R;

public class CallActivity extends CheckPermissionsActivity {
	/** Called when the activity is first created. */
	ListView listView;
	AutoCompleteTextView textView;
	TextView emptytextView;
	protected CursorAdapter mCursorAdapter;
	protected Cursor mCursor = null;
	protected ContactAdapter2 ca2;
	ArrayList<ContactInfo> contactList = new ArrayList<ContactInfo>();

	protected String numberStr = "";
	protected String[] autoContact = null;
	protected String[] wNumStr = null;
	private static final int DIALOG_KEY = 0;
	private Button AddContacts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_main);
		listView = (ListView) findViewById(R.id.list);
		textView = (AutoCompleteTextView) findViewById(R.id.edit);
		emptytextView = (TextView) findViewById(R.id.empty);
		Button btn_add = (Button) findViewById(R.id.btn_add);
		Button btn_back = (Button) findViewById(R.id.btn_back);
		AddContacts = (Button) findViewById(R.id.AddContacts);

		AddContacts.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final LinearLayout serverviewLayout = (LinearLayout) getLayoutInflater()
						.inflate(R.layout.activity_contact_addcontact, null);
				new AlertDialog.Builder(CallActivity.this)
						.setTitle("请输入联系人姓名和电话")
						.setView(serverviewLayout)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
														int which) {
										EditText NameEdit = (EditText) serverviewLayout
												.findViewById(R.id.contactName);

										EditText TelEdit = (EditText) serverviewLayout
												.findViewById(R.id.contactTel);

										Log.v("添加", "loading");
										insertContact(CallActivity.this,
												NameEdit.getText().toString(),
												TelEdit.getText().toString());
										Log.v("添加成功", "success");
										Toast.makeText(CallActivity.this,
												"添加联系人成功！", Toast.LENGTH_SHORT)
												.show();
										//刷新联系人列表
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
														int which) {
									}
								}).show();
			}
		});

		emptytextView.setVisibility(View.GONE);


		wNumStr = new String[]{"123", "456"};

		new GetContactTask().execute("");


		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View view,
									int position, long id) {

				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ contactList.get(position).getUserNumber()));
				CallActivity.this.startActivity(intent);
			}
		});

		btn_add.setOnClickListener(btnClick);
		btn_back.setOnClickListener(btnClick);
	}


	private OnClickListener btnClick = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.btn_add:
					Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
							+ textView.getText().toString()));
					CallActivity.this.startActivity(intent);
					break;
				case R.id.btn_back:
					finish();
					break;
			}
		}
	};

	private TextWatcher mTextWatcher = new TextWatcher() {
		public void beforeTextChanged(CharSequence s, int start, int before,
									  int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before,
								  int after) {

			String autoText = s.toString();
			if (autoText.length() >= 13) {
				Pattern pt = Pattern.compile("\\(([1][3,5,8]+\\d{9})\\)");
				Matcher mc = pt.matcher(autoText);
				if (mc.find()) {
					String sNumber = mc.group(1);
					DealWithAutoComplete(contactList, sNumber);

					Intent intent = new Intent(Intent.ACTION_CALL,
							Uri.parse("tel:" + sNumber));
					CallActivity.this.startActivity(intent);

					ca2.setItemList(contactList);
					ca2.notifyDataSetChanged();
				}
			}
		}

		public void afterTextChanged(Editable s) {
		}

	};

	private class GetContactTask extends AsyncTask<String, String, String> {
		public String doInBackground(String... params) {

			GetLocalContact();
			GetSimContact("content://icc/adn");
			GetSimContact("content://sim/adn");
			return "";
		}

		@Override
		protected void onPreExecute() {
			showDialog(DIALOG_KEY);
		}

		@Override
		public void onPostExecute(String Re) {

			if (contactList.size() == 0) {
				emptytextView.setVisibility(View.VISIBLE);
			} else {

				Comparator comp = new Mycomparator();
				Collections.sort(contactList, comp);

				numberStr = GetNotInContactNumber(wNumStr, contactList)
						+ numberStr;
				ca2 = new ContactAdapter2(CallActivity.this, contactList);
				listView.setAdapter(ca2);
				listView.setTextFilterEnabled(true);

				autoContact = new String[contactList.size()];
				for (int c = 0; c < contactList.size(); c++) {
					autoContact[c] = contactList.get(c).contactName + "("
							+ contactList.get(c).userNumber + ")";
				}

				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						CallActivity.this,
						android.R.layout.simple_dropdown_item_1line,
						autoContact);
				textView.setAdapter(adapter);
				textView.addTextChangedListener(mTextWatcher);
			}
			removeDialog(DIALOG_KEY);
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_KEY: {
			ProgressDialog dialog = new ProgressDialog(this);
			dialog.setMessage("获取通讯录中...请稍候");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			return dialog;
		}
		}
		return null;
	}

	private void GetLocalContact() {

		ContentResolver cr = getContentResolver();

		Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);
		while (cursor.moveToNext()) {
			ContactInfo cci = new ContactInfo();

			int nameFieldColumnIndex = cursor
					.getColumnIndex(PhoneLookup.DISPLAY_NAME);
			cci.contactName = cursor.getString(nameFieldColumnIndex);

			int id = cursor.getInt(cursor
					.getColumnIndex(ContactsContract.Contacts._ID));
			Cursor phone = cr.query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
					new String[] { Integer.toString(id) }, null);


			while (phone.moveToNext()) {
				String strPhoneNumber = phone
						.getString(phone
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				System.out.println(strPhoneNumber);
				cci.userNumber = GetNumber(strPhoneNumber);
				cci.isChecked = false;

				if (!IsContain(contactList, cci.userNumber)) {
					if (IsUserNumber(strPhoneNumber)) {
						if (IsAlreadyCheck(wNumStr, cci.userNumber)) {
							cci.isChecked = true;
							numberStr += "," + cci.userNumber;
						}
						contactList.add(cci);
					}
				}
			}
			phone.close();
		}
		cursor.close();
	}


	private void GetSimContact(String add) {

		try {
			Intent intent = new Intent();
			intent.setData(Uri.parse(add));
			Uri uri = intent.getData();
			mCursor = getContentResolver().query(uri, null, null, null, null);
			if (mCursor != null) {
				while (mCursor.moveToNext()) {
					ContactInfo sci = new ContactInfo();
					int nameFieldColumnIndex = mCursor.getColumnIndex("name");
					sci.contactName = mCursor.getString(nameFieldColumnIndex);

					int numberFieldColumnIndex = mCursor
							.getColumnIndex("number");
					sci.userNumber = mCursor.getString(numberFieldColumnIndex);

					sci.userNumber = GetNumber(sci.userNumber);
					sci.isChecked = false;

					if (IsUserNumber(sci.userNumber)) {
						if (!IsContain(contactList, sci.userNumber)) {
							if (IsAlreadyCheck(wNumStr, sci.userNumber)) {
								sci.isChecked = true;
								numberStr += "," + sci.userNumber;
							}
							contactList.add(sci);

						}
					}
				}
				mCursor.close();
			}
		} catch (Exception e) {
			Log.i("eoe", e.toString());
		}
	}

	private boolean IsContain(ArrayList<ContactInfo> list, String un) {
		for (int i = 0; i < list.size(); i++) {
			if (un.equals(list.get(i).userNumber)) {
				return true;
			}
		}
		return false;
	}

	private boolean IsAlreadyCheck(String[] Str, String un) {
		if (Str == null) {
			return false;
		}
		for (int i = 0; i < Str.length; i++) {
			if (un.equals(Str[i])) {
				return true;
			}
		}
		return false;
	}

	private String GetNotInContactNumber(String[] Str,
			ArrayList<ContactInfo> list) {
		String re = "";
		for (int i = 0; i < Str.length; i++) {
			if (IsUserNumber(Str[i])) {
				for (int l = 0; l < list.size(); l++) {
					if (Str[i].equals(list.get(l).userNumber)) {
						Str[i] = "";
						break;
					}
				}
				if (!Str[i].equals("")) {
					re += "," + Str[i];
				}
			}
		}
		return re;
	}

	private void DealWithAutoComplete(ArrayList<ContactInfo> list, String un) {
		for (int i = 0; i < list.size(); i++) {
			if (un.equals(list.get(i).userNumber)) {
				if (!list.get(i).isChecked) {
					list.get(i).isChecked = true;
					numberStr += "," + un;
					textView.setText("");
				}
			}
		}
	}

	public class Mycomparator implements Comparator {
		public int compare(Object o1, Object o2) {
			ContactInfo c1 = (ContactInfo) o1;
			ContactInfo c2 = (ContactInfo) o2;
			Comparator cmp = Collator.getInstance(java.util.Locale.CHINA);
			return cmp.compare(c1.contactName, c2.contactName);
		}

	}

	public static boolean IsUserNumber(String num) {
		boolean re = false;
		if (num.length() >= 11) {
			if (num.startsWith("1")) {
				re = true;
			}

		}
		return re;
	}

	public static String GetNumber(String num2) {
		String num;
		if (num2 != null) {
			System.out.println(num2);
			num = num2.replaceAll("-", "");
			if (num.startsWith("+86")) {
				num = num.substring(3);
			} else if (num.startsWith("86")) {
				num = num.substring(2);
			} else if (num.startsWith("86")) {
				num = num.substring(2);
			}
		} else {
			num = "";
		}
		return num;
	}

	private Uri insertContact(Context context, String name, String phone) {

		ContentValues values = new ContentValues();
		values.put(People.NAME, name);
		Uri uri = getContentResolver().insert(People.CONTENT_URI, values);
		Uri numberUri = Uri.withAppendedPath(uri,
				People.Phones.CONTENT_DIRECTORY);
		values.clear();

		values.put(Contacts.Phones.TYPE, People.Phones.TYPE_MOBILE);
		values.put(People.NUMBER, phone);
		getContentResolver().insert(numberUri, values);

		return uri;
	}
}