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

import com.example.dczs.MyApplication;
import com.example.dczs.R;
import com.example.dczs.entity.OrderFormBean;
import com.example.dczs.myinterface.OnItemClickLisenterOrder;
import com.example.dczs.utils.ToastUtils;

import java.util.List;

public class OrderProcessingAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private View view;
    private List<OrderFormBean> orderFormBeansList;
    private OnItemClickLisenterOrder mOnItemClickLisenter;
    private int selectPosition = -1;

    public OrderProcessingAdapter( Context context, List<OrderFormBean> orderFormBeans) {
        mContext = context;
        orderFormBeansList = orderFormBeans;
    }

    public void setOrderCompletedList(List<OrderFormBean> orderFormBeans){
        this.orderFormBeansList = orderFormBeans;
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
        view = LayoutInflater.from(mContext).inflate(R.layout.indent_adapt_layout, parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewholder = (ViewHolder) holder;
        final int pos = position;
        try{

            viewholder.mLlIndent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setSelectPosition(pos);
                    if (null != mOnItemClickLisenter) {
                        mOnItemClickLisenter.onItemClick(pos, 0);
                    }
                }
            });

            viewholder.mBtStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setSelectPosition(pos);
                    if (null != mOnItemClickLisenter) {
                        mOnItemClickLisenter.onItemClick(pos, 1);
                    }

                }
            });

            if (MyApplication.getAnEntitySet().getUserInfoBean().getUserLevel().equals("0")){
                viewholder.mBtStatus.setEnabled(false);

                if (orderFormBeansList.get(position).getOrderStatus().equals("1")){
                    viewholder.mBtStatus.setText("正在派送中");
                }else {
                    viewholder.mBtStatus.setText("正在配菜");
                }

            }else if (MyApplication.getAnEntitySet().getUserInfoBean().getUserLevel().equals("1")){
                viewholder.mBtStatus.setEnabled(false);
                if (orderFormBeansList.get(position).getOrderStatus().equals("1")){
                    viewholder.mBtStatus.setText("正在派送中");
                }else {
                    viewholder.mBtStatus.setText("正在配菜");
                }
            }else if (MyApplication.getAnEntitySet().getUserInfoBean().getUserLevel().equals("2")){
                if (orderFormBeansList.get(position).getOrderStatus().equals("1")){
                    viewholder.mBtStatus.setText("正在派送中");
                }else {
                    viewholder.mBtStatus.setText("正在配菜");
                }
            }

            viewholder.mTvNameIndent.setText(orderFormBeansList.get(position).getOrderName());
            viewholder.mTvPriceIndent.setText(orderFormBeansList.get(position).getOrderPrice());
            viewholder.mImgIndent.setImageBitmap(ToastUtils.stringtoBitmap(orderFormBeansList.get(position).getMenuPictures()));
            viewholder.mTvFoodIndent.setText(orderFormBeansList.get(position).getOrderSynopsis());



        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return orderFormBeansList == null ? 0 : orderFormBeansList.size();
    }

    //定义的ViewHolder类继承RecyclerView.ViewHolder 并找到布局中的ID
    public class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout mLlIndent;
        private ImageView mImgIndent;
        private TextView mTvNameIndent;
        private TextView mTvPriceIndent;
        private TextView mTvFoodIndent;
        private Button mBtStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            mLlIndent = (LinearLayout) itemView.findViewById(R.id.ll_indent);
            mImgIndent = (ImageView) itemView.findViewById(R.id.img_indent);
            mTvNameIndent = (TextView) itemView.findViewById(R.id.tv_name_indent);
            mTvPriceIndent = (TextView) itemView.findViewById(R.id.tv_price_indent);
            mTvFoodIndent = (TextView) itemView.findViewById(R.id.tv_food_indent);
            mBtStatus = (Button) itemView.findViewById(R.id.bt_status);

        }
    }

}
