package com.xiaobudian.huastools.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.xiaobudian.huastools.R;
import com.xiaobudian.huastools.model.Evaluate;

import java.util.ArrayList;

/**
 * Created by xiaobudian on 2016/1/3.
 */
public class EvaluateListAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<Evaluate> mEvaluates;

    public EvaluateListAdapter(Context context, ArrayList<Evaluate> evaluates) {
        super();
        this.context = context;
        this.mEvaluates = evaluates;
    }

    @Override
    public int getCount() {
        return mEvaluates.size();
    }

    @Override
    public Object getItem(int position) {
        return mEvaluates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null) {
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_evaluate_list, null);
            holder.tv_course_name=(TextView) convertView.findViewById(R.id.tv_course_name);
            holder.tv_teacher_name=(TextView) convertView.findViewById(R.id.tv_teacher_name);
            holder.tv_total_score=(TextView) convertView.findViewById(R.id.tv_total_score);
            holder.tv_is_evaluate=(TextView) convertView.findViewById(R.id.tv_is_evaluate);
            holder.tv_is_submit=(TextView) convertView.findViewById(R.id.tv_is_submit);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder) convertView.getTag();
        }
        holder.tv_course_name.setText(mEvaluates.get(position).getName());
        holder.tv_teacher_name.setText(mEvaluates.get(position).getTeacher());
        holder.tv_total_score.setText(mEvaluates.get(position).getScore());
        holder.tv_is_evaluate.setText(mEvaluates.get(position).getIs_evaluate());
        holder.tv_is_submit.setText(mEvaluates.get(position).getIs_submit());

        return convertView;
    }

    class ViewHolder{
        TextView tv_course_name;
        TextView tv_teacher_name;
        TextView tv_total_score;
        TextView tv_is_evaluate;
        TextView tv_is_submit;
    }
}
