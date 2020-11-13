package com.example.cosplan.data.Coplay.RefImg;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

public class RefenceImgAdapter extends BaseAdapter {
    private List<ReferenceImg> mRefImgs;
    private Context mContext;
    public RefenceImgAdapter(Context context){mContext=context;}

    public void setRefImgs(List<ReferenceImg>refImgs){
        mRefImgs=refImgs;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mRefImgs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReferenceImg temp=(ReferenceImg) getItem(position);
        ImageView imageView=new ImageView(mContext);
        imageView.setImageBitmap(temp.mCosplayRefImgImage);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(340,350));
        return imageView;
    }
}
