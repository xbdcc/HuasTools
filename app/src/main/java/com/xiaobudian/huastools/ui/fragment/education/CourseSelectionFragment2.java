//package com.xiaobudian.huastools.ui.fragment.education;
//
//import android.app.ProgressDialog;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.xiaobudian.huastools.AppComponent;
//import com.xiaobudian.huastools.R;
//import com.xiaobudian.huastools.data.DataUtil;
//import com.xiaobudian.huastools.data.EducationApi;
//import com.xiaobudian.huastools.data.HttpUrl;
//import com.xiaobudian.huastools.model.CourseSelection;
//import com.xiaobudian.huastools.ui.activity.education.CourseSelectionDetailActivity;
//import com.xiaobudian.huastools.ui.component.DaggerEducationComponent;
//import com.xiaobudian.huastools.ui.fragment.BaseFragment;
//import com.xiaobudian.huastools.ui.module.EducationModule;
//import com.xiaobudian.huastools.ui.presenter.CourseSelectionPresenter;
//import com.xiaobudian.huastools.ui.view.CourseSelectionView;
//import com.xiaobudian.huastools.util.ToastUtil;
//import com.xiaobudian.huastools.util.Utility;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.select.Elements;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import javax.inject.Inject;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
///**
// * Created by caochang on 2016/4/17.
// */
//public class CourseSelectionFragment2 extends BaseFragment implements CourseSelectionView {
//
//    private static final String TAG = "CourseSelectionFragment2";
//
//    @Bind(R.id.b_course_selection_refresh)
//    Button mBCourseSelectionRefresh;
//    @Bind(R.id.tv_result)
//    TextView mTvResult;
//    @Bind(R.id.tv_course_has_selection)
//    TextView mTvCourseHasSelection;
//    @Bind(R.id.tv_course_no_selection)
//    TextView mTvCourseNoSelection;
//    @Bind(R.id.lv_course_has_selection)
//    ListView mLvCourseHasSelection;
//    @Bind(R.id.tv_course_can_selection)
//    TextView mTvCourseCanSelection;
//    @Bind(R.id.lv_course_can_selection)
//    ListView mLvCourseCanSelection;
//    @Bind(R.id.pb_loading)
//    ProgressBar mPbLoading;
//    @Bind(R.id.tv_message)
//    TextView mTvMessage;
//    @Bind(R.id.b_retry)
//    Button mBRetry;
//    @Bind(R.id.ll_view)
//    LinearLayout mLlView;
//
//    private CourseSelectionPresenter mCourseSelectionPresenter;
//    @Inject
//    EducationApi mEducationApi;
//
//    private ProgressDialog dialog;
//    private String result = null;
//    private ArrayList<CourseSelection> l_all_course;
//    private ArrayList<CourseSelection> l_has_selected;
//    private ArrayList<CourseSelection> l_can_selected;
//    private ListAdapter has_selected_adapter;
//    private ListAdapter can_selected_adapter;
//    boolean has_selected = false;
//    private static final int unkonwn_error = 0;
//    private static final int main_data = 1;
//    private static final int list_data = 2;
//    private static final int can_not_select_course = 3;
//    private static final int no_select_course = 4;
//    private static final int submit_or_quit = 5;
//    private int quit = 0;
//    private int sumit = 1;
//    private String jx0502id;
//    private String jx0502zbid;
//    private String jx0404id;
//    private String url_list = null;
//    private String url_submit = null;
//    private int i;
//    private String message;
//    private int where;
//    private boolean reFreshData;
//    private CourseSelection selection;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
//        View view = inflater.inflate(R.layout.fragment_course_selection, container, false);
//        Log.d(TAG, "onCreateView");
//        ButterKnife.bind(this, view);
//
//        mCourseSelectionPresenter=new CourseSelectionPresenter(mEducationApi,this);
//
//        hideView();
//        return view;
//    }
//
//    @Override
//    protected void setupFragmentComponent(AppComponent appComponent) {
//        DaggerEducationComponent.builder()
//                .appComponent(appComponent)
//                .educationModule(new EducationModule(this))
//                .build()
//                .inject(this);
//    }
//
//
//    public void loadData() {
//        hideView();
//        mCourseSelectionPresenter.getCourseSelectionCount(educationCookie);
//    }
//
//
//    @Override
//    public void parseData(String result) {
//        if(result==null){
//            ToastUtil.showError(getActivity());
//        }else{
////            handler.sendEmptyMessage(main_data);
//            this.result=result;
//            parseUrl();
//        }
//    }
//
//    @Override
//    public void parseList(String result) {
//        Log.i(TAG,"获取列表数据");
//        Log.i(TAG,"--->"+result);
//        if(result==null){
//            ToastUtil.showError(getActivity());
//        }else{
////                handler.sendEmptyMessage(list_data);
//            Document document=Jsoup.parse(result);
//            Elements elements=document.select("script");
//            result="";
//            //正则表达式筛选得到需要的initData()方法里面的数据
//            Pattern pattern=Pattern.compile("(?<=tmpKc).*(?=)");//775个
//            Matcher matcher=pattern.matcher(elements.toString());
//            i=0;
//            while(matcher.find()){
//                i++;
//                result+=matcher.group();
//            }
//            Log.i(TAG,"共"+i+"个tmpKc相关的");
//            result=result.replaceAll("Array\\(\\);","<a>").replaceAll("xkkcData", "</a>")
//                    .replaceAll("\"", "").replaceAll("=", "<b>").replaceAll(";", "</b>")
//                    .replace("yxkcData", "<c>");
//            document=Jsoup.parse(result);
//            elements=document.select("a");
//            Elements e_item= null;
//            //总列表
//            l_all_course=new ArrayList<>();
//            l_has_selected=new ArrayList<>();
//            l_can_selected=new ArrayList<>();
//            int k=0;
//            for(i=0;i<elements.size();i++){
//                System.out.println(elements.get(i).text());
//                e_item=elements.get(i).select("b");
//                //里面的每一项，得到选课或正选总项数
//                if(e_item.size()>17){
//                    k++;
////                l_all_course.add(e_item.get(1).text());
//                    CourseSelection selection=new CourseSelection();
//                    selection.setCourse_name(e_item.get(1).text().toString());
//                    selection.setCourse_study_score(e_item.get(4).text().toString());
//                    selection.setCourse_limit_selected(e_item.get(6).text().toString());
//                    selection.setCourse_study_teacher(e_item.get(7).text().toString());
//                    selection.setCourse_study_total_time(e_item.get(8).text().toString());
//                    selection.setCourse_study_time(e_item.get(9).text().toString());
//                    selection.setCourse_study_place(e_item.get(10).text().toString());
//                    selection.setCourse_submit_data(e_item.get(11).text().toString());
//                    selection.setCourse_total_time(e_item.get(15).text().toString());
//                    selection.setCourse_category(e_item.get(16).text().toString());
//                    l_all_course.add(selection);
//                }
//            }
//            Log.i(TAG,"--------------------总共课程数："+k);
//            //判断是否选课
//            for(i=0;i<k-1;i++){
//                //判断是否有选课
//                if(l_all_course.get(i).getCourse_name().equals(l_all_course.get(k-1).getCourse_name())){
//                    Log.i(TAG,"已选课程"+l_all_course.get(k-1));
//                    l_has_selected.add(l_all_course.get(i));
//                    has_selected=true;
//                }else{
//                    l_can_selected.add(l_all_course.get(i));
//                }
//            }
//
//            if(l_all_course.size()>0){
//                if(!has_selected){
//                    l_can_selected.add(l_all_course.get(k-1));
//                }
//                Log.i(TAG,"--------------------已选"+l_has_selected.size());
//                Log.i(TAG, "--------------------可选"+l_can_selected.size());
//                Log.i(TAG,l_all_course.get(0).getCourse_study_score());
//                Log.i(TAG,l_can_selected.get(0).getCourse_study_score());
//
//                mTvCourseCanSelection.setVisibility(View.VISIBLE);
//                mLvCourseCanSelection.setVisibility(View.VISIBLE);
//                refreshCanSelected();
//                refreshHasSelected();
//                mTvMessage.setVisibility(View.GONE);
//
//            }else{
//                mTvMessage.setText(R.string.no_select_course);
//                mTvMessage.setVisibility(View.VISIBLE);
//            }
//        }
//    }
//
//
//    public void parseUrl(){
//        try{
//            List<String> list=new ArrayList<String>();
//            //获取script里面的数据
//            Document document= Jsoup.parse(result);
//            Elements elements=document.select("script");
//            //正则表达式筛选得到需要的initData()方法里面的数据
//            Pattern pattern=Pattern.compile("function\\s*?initData\\(.*?\\)\\s*?\\{[^{}]*?(\\{.*?\\})*[^{}]*?\\}");
//            Matcher matcher=pattern.matcher(elements.toString());
//            while(matcher.find()){
//                result=matcher.group();
//            }
//            //替换标签得到需要的数组
//            result=result.replaceAll("=\\s\"", "<a>").replaceAll("\";", "</a>");
//            Document document2=Jsoup.parse(result);
//            Elements elements2=document2.select("a");
//            for(int i=0;i<elements2.size();i++){
//                Log.i(TAG,"0000000>"+elements2.get(i).text());
//                list.add(elements2.get(i).text());
//            }
//            //获得链接
//            url_list="jsxsd/xk/getXkInfo?jx0502zbid="+list.get(5)+"&jx0502id="+list.get(6);
//            jx0502id=list.get(6);
//            jx0502zbid=list.get(5);
//            url_list=EducationApi.BASE_URL+url_list;
//            Log.i(TAG,"---url_list:"+url_list);
//            mCourseSelectionPresenter.getCourseSelectionList(educationCookie,url_list);
//        }catch (Exception e){
//            Log.i(TAG,"选课未开放！");
//            mTvMessage.setText(R.string.can_not_select_course);
//            mTvMessage.setVisibility(View.VISIBLE);
//        }
//    }
//
//    public void refreshHasSelected(){
//        mTvCourseCanSelection.setVisibility(View.VISIBLE);
//        if(l_has_selected.size()>0){
//            //已选课程
//            has_selected_adapter=new ListAdapter(l_has_selected,0);
//            mLvCourseHasSelection.setAdapter(has_selected_adapter);
//            Utility.setListViewHeightBasedOnChildren(mLvCourseHasSelection);
//            mLvCourseHasSelection.setVisibility(View.VISIBLE);
//            mTvCourseNoSelection.setVisibility(View.GONE);
//            Log.i(TAG, l_has_selected.get(0).getCourse_name());
//            mLvCourseHasSelection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    selection = l_has_selected.get(position);
//                    sendData();
//                }
//            });
//        }else{
//            mLvCourseHasSelection.setVisibility(View.GONE);
//            mTvCourseNoSelection.setVisibility(View.VISIBLE);
//        }
//    }
//
//    public void refreshCanSelected(){
//        //可选课程
//        can_selected_adapter=new ListAdapter(l_can_selected,1);
//        mLvCourseCanSelection.setAdapter(can_selected_adapter);
//        Utility.setListViewHeightBasedOnChildren(mLvCourseCanSelection);
//
//        mLvCourseCanSelection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                selection = l_can_selected.get(position);
//                sendData();
//            }
//        });
//    }
//
//    private void sendData(){
//        Intent intent=new Intent();
//        intent.setClass(getActivity(), CourseSelectionDetailActivity.class);
//        Bundle bundle=new Bundle();
//        bundle.putSerializable("selection",selection);
//        intent.putExtras(bundle);
//        startActivity(intent);
//    }
//
//    public class ListAdapter extends BaseAdapter {
//        private ArrayList<CourseSelection> courseSelections;
//        private int status;
//        public ListAdapter(ArrayList<CourseSelection> courseSelections,int status){
//            this.courseSelections=courseSelections;
//            this.status=status;
//        }
//
//        @Override
//        public int getCount() {
//            return courseSelections.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return courseSelections.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//            convertView= LayoutInflater.from(getActivity()).inflate(R.layout.item_course_selection, null);
//            TextView tv_course_selection= (TextView) convertView.findViewById(R.id.tv_course_selection);
//            TextView tv_course_name= (TextView) convertView.findViewById(R.id.tv_course_name);
//            TextView tv_course_score= (TextView) convertView.findViewById(R.id.tv_course_score);
//
//            tv_course_name.setText(courseSelections.get(position).getCourse_name());
//            tv_course_score.setText(courseSelections.get(position).getCourse_study_score());
//
//            if(status==0){
//                tv_course_selection.setText("退选");
//
//            } else if(status==1){
//                tv_course_selection.setText("选课");
////                jx0404id=courseSelections.get(position).getCourse_submit_data();
//            }
//
//            tv_course_selection.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(status==0){
//                        url_submit= HttpUrl.education_course_quit;
//                        jx0404id=courseSelections.get(position).getCourse_submit_data();
//                        Log.i(TAG,"点击了退选，退选网址为："+url_submit);
//                    }else if(status==1){
//                        url_submit=HttpUrl.education_course_submit;
//                        jx0404id=courseSelections.get(position).getCourse_submit_data();
//                        Log.i(TAG,"点击了选课，选课网址为："+url_submit);
//                    }
//                    where=position;
//                    mCourseSelectionPresenter.getCourseSelectionSubmit(educationCookie,jx0404id,jx0502id,jx0502zbid,status);
//                }
//            });
//            return convertView;
//        }
//    }
//
//    @Override
//    public void parseSubmit(String result) {
//        Document document=Jsoup.parse(result);
//        message=document.select("body").text();
//        //正则表达式筛选结果信息
//        Pattern pattern=Pattern.compile("\"(msgContent)\":\"(.*?)\"");
//        Matcher matcher=pattern.matcher(message);
//        while(matcher.find()){
//            message=matcher.group(2);
//        }
//        ToastUtil.showToast(getActivity(), message);
//        if(message.equals("选课成功")){
//            CourseSelection selection=l_can_selected.get(where);
//            l_can_selected.remove(selection);
//            l_has_selected.add(selection);
//            refreshHasSelected();
//            refreshCanSelected();
//            Log.d(TAG,"选课成功");
//        }else if(message.equals("退选成功")){
//            CourseSelection selection=l_has_selected.get(where);
//            l_has_selected.remove(selection);
//            l_can_selected.add(selection);
//            refreshHasSelected();
//            refreshCanSelected();
//            Log.d(TAG,"退选成功");
//        }else{
//            //超时需重新登录
//            Elements elements=document.select("body").select("div[class=dlmi]");
//            result=elements.select("font").text();
//            if(!result.equals("")){
//                ToastUtil.showToast(getActivity(),result);
//            }
//        }
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(DataUtil.Education.LONGIN_SUCCESS);
//        getActivity().registerReceiver(receiver, intentFilter);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        getActivity().unregisterReceiver(receiver);
//    }
//
//    BroadcastReceiver receiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction().equals(DataUtil.Education.LONGIN_SUCCESS)) {
//                Log.i(TAG, "成功登录教务系统，刷新选课信息。");
//                loadData();
//            }
//        }
//    };
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        ButterKnife.unbind(this);
//    }
//
//    @Override
//    public void showView() {
//        mLlView.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void hideView() {
//        mLlView.setVisibility(View.GONE);
//    }
//
////    @Override
////    public void showMessage() {
////        mTvMessage.setVisibility(View.VISIBLE);
////    }
////
////    @Override
////    public void hideMessage() {
////        mTvMessage.setVisibility(View.GONE);
////    }
//
//    @Override
//    public void showLoading() {
//        mPbLoading.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void hideLoading() {
//        mPbLoading.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void showRetry() {
//        mBRetry.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void hideRetry() {
//        mBRetry.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void showError(String message) {
//        mTvMessage.setText(message);
//    }
//
//    @Override
//    public Context context() {
//        return getActivity();
//    }
//
//    @OnClick({R.id.b_retry, R.id.b_course_selection_refresh})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.b_retry:
//                break;
//            case R.id.b_course_selection_refresh:
//                loadData();
//                break;
//        }
//    }
//}