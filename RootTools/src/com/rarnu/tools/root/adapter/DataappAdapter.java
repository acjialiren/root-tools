package com.rarnu.tools.root.adapter;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.rarnu.devlib.base.adapter.BaseAdapter;
import com.rarnu.tools.root.GlobalInstance;
import com.rarnu.tools.root.R;
import com.rarnu.tools.root.common.DataappInfo;
import com.rarnu.tools.root.holder.DataappAdapterHolder;
import com.rarnu.tools.root.utils.ApkUtils;

public class DataappAdapter extends BaseAdapter<DataappInfo> {

	private Handler h;
	private boolean checkable = true;
	private int type;

	public DataappAdapter(Context context, List<DataappInfo> list, Handler h,
			int type) {
		super(context, list);
		this.h = h;
		this.type = type;
	}

	public void setAdapterCheckable(boolean checkable) {
		this.checkable = checkable;
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final DataappInfo item = list.get(position);
		View v;
		if (convertView == null) {
			v = inflater.inflate(R.layout.dataapp_item, parent, false);
		} else {
			v = convertView;
		}
		DataappAdapterHolder holder = (DataappAdapterHolder) v.getTag();
		if (holder == null) {
			holder = new DataappAdapterHolder();
			holder.icon = (ImageView) v.findViewById(R.id.item_icon);
			holder.name = (TextView) v.findViewById(R.id.item_name);
			holder.path = (TextView) v.findViewById(R.id.item_path);
			holder.select = (CheckBox) v.findViewById(R.id.chk_select);

			v.setTag(holder);
		}

		if (item != null) {
			holder.select.setChecked(item.checked);

			if (type == 1) {
				holder.icon.setBackgroundDrawable(GlobalInstance.pm
						.getApplicationIcon(item.info));
				holder.name.setText(GlobalInstance.pm
						.getApplicationLabel(item.info));
				holder.path.setText(item.info.dataDir);
			} else {
				holder.icon.setBackgroundDrawable(ApkUtils.getIconFromPackage(
						v.getContext(), item.info));
				holder.name.setText(ApkUtils.getLabelFromPackage(
						v.getContext(), item.info));
				holder.path.setText(item.info.packageName + ".apk");
			}

			holder.select.setEnabled(checkable);

			holder.select.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					item.checked = ((CheckBox) v).isChecked();
					h.sendEmptyMessage(1);
				}
			});

		}

		return v;
	}

	@Override
	public String getValueText(DataappInfo item) {
		return GlobalInstance.pm.getApplicationLabel(item.info).toString()
				+ item.info.packageName;
	}
}
