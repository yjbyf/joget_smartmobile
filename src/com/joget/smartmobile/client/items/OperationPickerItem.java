package com.joget.smartmobile.client.items;

import java.util.LinkedHashMap;

import com.smartgwt.mobile.client.widgets.form.fields.SelectItem;

public class OperationPickerItem extends SelectItem {

	public OperationPickerItem(String name) {
		super(name);
		super.setValueMap(StaffValueMap.getInstance(this));
	}

	public OperationPickerItem(String name, String title) {
		this(name);
		super.setTitle(title);
	}

	public OperationPickerItem(String name, String title, String hint) {
		this(name, title);
		super.setHint(hint);
	}

	/**
	 * 操作列表静态实例
	 * 
	 * @author user1
	 * 
	 */
	public static class StaffValueMap {
		static final long serialVersionUID = -9114366705329415471L;
		private static LinkedHashMap<String, String> instance;

		private static void init() {
			instance = new LinkedHashMap<String, String>();
			// 取得fnd_user表中的所有记录
			instance.put("Approved", "Approved");
			instance.put("Reject", "Reject");
		}

		public static LinkedHashMap<String, String> getInstance(SelectItem obj) {
			if (instance == null) {
				init();
				return instance;
			} else {
				return instance;
			}

		}
	}
}
