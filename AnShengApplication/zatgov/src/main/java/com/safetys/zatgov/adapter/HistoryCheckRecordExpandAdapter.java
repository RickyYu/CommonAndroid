package com.safetys.zatgov.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.R;
import com.safetys.zatgov.bean.CheckListInfo;
import com.safetys.zatgov.bean.SafetyCheck;
import com.safetys.zatgov.bean.WgyHiddenItemInfo;
import com.safetys.zatgov.ui.view.PullToRefresh;
import com.safetys.zatgov.ui.view.SearchBar;
import com.safetys.zatgov.utils.DialogUtil;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * @author ricky
 * @time 2017-10-11
 */
public class HistoryCheckRecordExpandAdapter extends BaseExpandableListAdapter {

	private LayoutInflater mInflater;
	private ArrayList<CheckListInfo> groupDatas;
	private List<List<WgyHiddenItemInfo>> childDatas;// Child数据
	private Activity activity;

	public HistoryCheckRecordExpandAdapter(Activity activity,
			ArrayList<CheckListInfo> groupDatas,
			List<List<WgyHiddenItemInfo>> childDatas) {
		this.activity = activity;
		this.groupDatas = groupDatas;
		this.childDatas = childDatas;
		this.mInflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getGroupCount() {

		return groupDatas.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {

		return childDatas.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {

		return groupDatas.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {

		return childDatas.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {

		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {

		return childPosition;
	}

	@Override
	public boolean hasStableIds() {

		return true;
	}

	@Override
	public View getGroupView(final int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.list_view_string_and_arrow_and_date_expand_item,
					null);
			holder = new GroupHolder();
			holder.mTextView1 = (TextView) convertView.findViewById(R.id.text1);
			holder.mTextView2 = (TextView) convertView.findViewById(R.id.text2);
			holder.mTextView3 = (TextView) convertView.findViewById(R.id.text3);
			holder.mTextView4 = (TextView) convertView
					.findViewById(R.id.tv_review_times);

			convertView.setTag(holder);
		} else {
			holder = (GroupHolder) convertView.getTag();
		}

		holder.mTextView1.setText("检查场所:"
				+ groupDatas.get(groupPosition).getCheckGround());
		holder.mTextView2.setText("未整改隐患数量("
				+ groupDatas.get(groupPosition).getWzgNum() + ")个");
		holder.mTextView3.setText("检查日期："
				+ groupDatas.get(groupPosition).getCheckTimeBegin());
		holder.mTextView4.setText("");

		return convertView;

	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		LogUtil.i("child -----  groupPosition:" + groupPosition+"childPosition:"+childPosition);
		View view = convertView;
		ChildHolder holder = null;
		if (view == null) {
			holder = new ChildHolder();
			view = mInflater.inflate(R.layout.expand_list_hidden_item, null);
			holder.mTextView1 = (TextView) view.findViewById(R.id.et_left);
			holder.iv_now = (ImageView) view.findViewById(R.id.iv_now);
			holder.btn_delete = view.findViewById(R.id.btn_delete);
			holder.btn_delete.setVisibility(View.GONE);
			view.setTag(holder);
		} else {
			holder = (ChildHolder) view.getTag();
		}
		holder.iv_now.setImageResource(R.mipmap.wgy_pic);
		WgyHiddenItemInfo itemInfo = childDatas.get(groupPosition).get(
				childPosition);
		holder.mTextView1.setText(itemInfo.getDescription());
		if (itemInfo.getImageFile() != null) {

			Bitmap bm = BitmapFactory.decodeFile(itemInfo.getImageFile()
					.getAbsolutePath());
			// 将图片显示到ImageView中
			holder.iv_now.setImageBitmap(bm);
		} else {
			holder.iv_now.setVisibility(View.GONE);
		}

		return view;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	class GroupHolder {
		TextView mTextView1;
		TextView mTextView2;
		TextView mTextView3;
		TextView mTextView4;
	}

	class ChildHolder {
		public TextView mTextView1;
		public ImageView iv_now;
		public View btn_delete;
	}

	private PullToRefresh listView;
	private ArrayList<SafetyCheck> safetyCheckDatas;
	private SearchBar searchBar;
	private CheckListAdapter checkListAdapter;
	private Dialog dialogOne;
	private int totalCount_One = 0;// 总数
	private int mCurrentPage_One = 0;// 当前页

	/**
	 * 复查记录列表
	 */
	private void showCheckTable() {
		// 新建檢查表
		View inflate = LayoutInflater.from(activity).inflate(
				R.layout.wgy_checklist, null);
		listView = (PullToRefresh) inflate.findViewById(R.id.list_checklist);
		safetyCheckDatas = new ArrayList<SafetyCheck>();
		SearchBar mSearchBar_One = (SearchBar) inflate
				.findViewById(R.id.checklist_search_bar);
		mSearchBar_One.setHintSearch("请输入检查表名");
		mSearchBar_One.setOnSearchListener(new SearchBar.onSearchListener() {
			@Override
			public void onSearchButtonClick(String searchStr) {
				// reloadingDatas();
			}
		});

		checkListAdapter = new CheckListAdapter(activity, safetyCheckDatas);
		listView.setAdapter(checkListAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (dialogOne != null) {
					dialogOne.dismiss();
				}

				// loadingCheckDatas(safetyCheckDatas.get(position).getId());



			}
		});

		listView.setOnRefreshListener(new PullToRefresh.OnRefreshListener() {

			@Override
			public void onLoadingMore() {
				// 加载更多
				if (mCurrentPage_One > totalCount_One) {
					ToastUtils.showMessage(activity, "没有更多的数据了。");
					listView.finishLoading(false);
				} else {
					loadingDatas();
				}
			}

		});

		// 如果第一次按，是加载，之后按都为重新加载
		loadingDatas();
		if (!DialogUtil.isFastDoubleClick()) {
			dialogOne = DialogUtil.getlistDialog(activity, "复查记录", inflate,
					null);
			dialogOne.getWindow().setType(
					WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
			dialogOne.show();
		}
	}

	/**
	 * 加载数据
	 */
	private void loadingDatas() {
		safetyCheckDatas.add(new SafetyCheck(1, "2017年10月11日",
				"2017年10月11日      复查人员：张磊"));
		safetyCheckDatas.add(new SafetyCheck(1, "2017年10月09日",
				"2017年10月09日      复查人员：赵晨、刘平"));
		checkListAdapter.notifyDataSetChanged();
		listView.finishLoading(false);
	}

	private void restData() {
		totalCount_One = 0;
		mCurrentPage_One = 0;
		safetyCheckDatas.clear();
	}

	private void reloadingDatas() {
		restData();
		checkListAdapter.notifyDataSetChanged();
		loadingDatas();
	}
}
