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
import com.example.dczs.entity.ShoppingCartBean;
import com.example.dczs.myinterface.OnItemClickLisenterOrder;
import com.example.dczs.utils.ToastUtils;

import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter{
    private Context mContext;
    private View view;
    private List<ShoppingCartBean> shoppingCartBeanListes;
    private OnItemClickLisenterOrder mOnItemClickLisenter;
    private int selectPosition = -1;

    public ShoppingCartAdapter(Context context, List<ShoppingCartBean> shoppingCartBeanList) {
        mContext = context;
        shoppingCartBeanListes = shoppingCartBeanList;
    }

    public void setShoppingCartList(List<ShoppingCartBean> shoppingCartBeanList){
        this.shoppingCartBeanListes = shoppingCartBeanList;
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
        view = LayoutInflater.from(mContext).inflate(R.layout.shopping_cart_adapt_layout, parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewholder = (ViewHolder) holder;
        final int pos = position;
        try{
            viewholder.mLlLookOveres.setOnClickListener(new View.OnClickListener() {
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

            viewholder.mTvNameDishes.setText(shoppingCartBeanListes.get(position).getArticleName());
            viewholder.mTvPriceFoodes.setText(shoppingCartBeanListes.get(position).getArticlePrice());
            viewholder.mImgFoodPictureses.setImageBitmap(ToastUtils.stringtoBitmap(shoppingCartBeanListes.get(position).getMenuPictures()));
            viewholder.mTvFoodIntroductiones.setText(shoppingCartBeanListes.get(position).getArticleSynopsis());

        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return shoppingCartBeanListes == null ? 0 : shoppingCartBeanListes.size();
    }

    //定义的ViewHolder类继承RecyclerView.ViewHolder 并找到布局中的ID
    public class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout mLlLookOveres;
        private ImageView mImgFoodPictureses;
        private TextView mTvNameDishes;
        private TextView mTvPriceFoodes;
        private TextView mTvFoodIntroductiones;
        private Button mBtPurchase;

        public ViewHolder(View itemView) {
            super(itemView);
            mLlLookOveres = (LinearLayout) itemView.findViewById(R.id.ll_look_overes);
            mImgFoodPictureses = (ImageView) itemView.findViewById(R.id.img_food_pictureses);
            mTvNameDishes = (TextView) itemView.findViewById(R.id.tv_name_dishes);
            mTvPriceFoodes = (TextView) itemView.findViewById(R.id.tv_price_foodes);
            mTvFoodIntroductiones = (TextView) itemView.findViewById(R.id.tv_food_introductiones);
            mBtPurchase = (Button) itemView.findViewById(R.id.bt_purchasees);
        }
    }
}
