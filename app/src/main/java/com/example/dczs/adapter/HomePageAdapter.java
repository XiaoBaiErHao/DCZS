package com.example.dczs.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dczs.R;
import com.example.dczs.entity.AddModifyMenuBean;
import com.example.dczs.myinterface.OnItemClickLisenterOrder;
import com.example.dczs.utils.ToastUtils;

import java.util.List;


public class HomePageAdapter extends RecyclerView.Adapter{

    private Context mContext;
    private View view;
    private List<AddModifyMenuBean> addModifyMenusedBean;
    private OnItemClickLisenterOrder mOnItemClickLisenter;
    private int selectPosition = -1;

    public HomePageAdapter(Context context, List<AddModifyMenuBean> addModifyMenuBeans) {
        addModifyMenusedBean = addModifyMenuBeans;
        mContext = context;
    }

    public void setAddModifyMenuList(List<AddModifyMenuBean> addModifyMenuBeans){
        this.addModifyMenusedBean = addModifyMenuBeans;
    }

    public void setSelectPosition(int position) {
        this.selectPosition = position;
        notifyDataSetChanged();
    }

    public void setOnItemClickLisenter(OnItemClickLisenterOrder onItemClickLisenter) {
        this.mOnItemClickLisenter = onItemClickLisenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.home_page_adapt_layout, parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewholder = (ViewHolder) holder;
        final int pos = position;
        try{

            viewholder.mLlLookOver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setSelectPosition(pos);
                    if (null != mOnItemClickLisenter) {
                        mOnItemClickLisenter.onItemClick(pos, 0);
                    }
                }
            });

            viewholder.mBtPurchase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setSelectPosition(pos);
                    if (null != mOnItemClickLisenter) {
                        mOnItemClickLisenter.onItemClick(pos, 1);
                    }

                }
            });

            viewholder.mTvNameDish.setText(addModifyMenusedBean.get(position).getGreensName());
            viewholder.mTvPriceFood.setText(addModifyMenusedBean.get(position).getGreensPrice());
            viewholder.mImgFoodPictures.setImageBitmap(ToastUtils.stringtoBitmap(addModifyMenusedBean.get(position).getMenuPictures()));
            viewholder.mTvFoodIntroduction.setText(addModifyMenusedBean.get(position).getGreensSynopsis());

        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return addModifyMenusedBean == null ? 0 : addModifyMenusedBean.size();
    }

    //定义的ViewHolder类继承RecyclerView.ViewHolder 并找到布局中的ID
    public class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout mLlLookOver;
        private ImageView mImgFoodPictures;
        private TextView mTvNameDish;
        private TextView mTvPriceFood;
        private TextView mTvFoodIntroduction;
        private Button mBtPurchase;

        public ViewHolder(View itemView) {
            super(itemView);
            mLlLookOver = (LinearLayout) itemView.findViewById(R.id.ll_look_over);
            mImgFoodPictures = (ImageView) itemView.findViewById(R.id.img_food_pictures);
            mTvNameDish = (TextView) itemView.findViewById(R.id.tv_name_dish);
            mTvPriceFood = (TextView) itemView.findViewById(R.id.tv_price_food);
            mTvFoodIntroduction = (TextView) itemView.findViewById(R.id.tv_food_introduction);
            mBtPurchase = (Button) itemView.findViewById(R.id.bt_purchase);

        }
    }
}
