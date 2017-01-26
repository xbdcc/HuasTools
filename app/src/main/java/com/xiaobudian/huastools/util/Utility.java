package com.xiaobudian.huastools.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * ScrollView嵌套ListView重新计算高度类
 * @author xiaobudian
 *
 */
public class Utility {

	public static void setListViewHeightBasedOnChildren(ListView listView){
		ListAdapter adapter=listView.getAdapter();
		if (adapter==null) {
			return;
		}
		int i=0;
		for(int j=0;;j++){
			if (j>=adapter.getCount()) {
				ViewGroup.LayoutParams params=listView.getLayoutParams();
				params.height=(i+listView.getDividerHeight()*(-1+adapter.getCount()));
				listView.setLayoutParams(params);
				return;
			}
			View view=adapter.getView(j, null, listView);
			view.measure(0, 0);
			i+=view.getMeasuredHeight();
		}
	}
	
}
