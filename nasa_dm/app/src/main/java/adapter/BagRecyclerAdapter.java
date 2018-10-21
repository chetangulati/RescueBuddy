package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.astudios.disastermanagement.GoBagActivity;
import com.astudios.disastermanagement.R;

import java.util.ArrayList;
import com.astudios.disastermanagement.GoBagActivity;
import essential.Essential;
import model.BagItem;

public class BagRecyclerAdapter extends RecyclerView.Adapter<BagRecyclerAdapter.ViewHolder> {

    Context mContext;
    ArrayList<BagItem> dataList;
    Essential essential;


    private BagRecyclerAdapter() {
    }

    public BagRecyclerAdapter(Context context, ArrayList<BagItem> al) {
        mContext = context;
        if (al != null)
            dataList = al;
        essential = new Essential(context);
    }

    public void dataChanged(ArrayList<String> al) {

    }

    @NonNull
    @Override
    public BagRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_bag, parent, false);

        return new BagRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BagRecyclerAdapter.ViewHolder viewHolder, int i) {
       final BagItem bagItem = dataList.get(i);
        viewHolder.itemMax = bagItem.getMaxCount();
        viewHolder.itemMin = bagItem.getMinCount();
        viewHolder.titleTv.setText(bagItem.getItemTitle());
        viewHolder.currentCountTv.setText(bagItem.getCurrentCount()+"");
        viewHolder.infoTv.setText(bagItem.getItemInfo());
        viewHolder.c = viewHolder.itemMin;
        viewHolder.addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(bagItem.getCurrentCount()<=viewHolder.itemMax) {
                    viewHolder.currentCountTv.setText((bagItem.getCurrentCount() + 1) + "");
                    bagItem.setCurrentCount(bagItem.getCurrentCount() + 1);
                    GoBagActivity goBagActivity = (GoBagActivity)mContext;
                    goBagActivity.updateText(bagItem.getItemWeight());
                    notifyDataSetChanged();
                }
                else{
                    essential.show("Can't carry more!!Bag is too heavy",Essential.INFO);
                }
            }
        });
        viewHolder.subItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bagItem.getCurrentCount()>viewHolder.itemMin) {
                    viewHolder.currentCountTv.setText((bagItem.getCurrentCount() - 1) + "");
                    bagItem.setCurrentCount(bagItem.getCurrentCount() - 1);
                    GoBagActivity goBagActivity = (GoBagActivity)mContext;
                    goBagActivity.modifyText(bagItem.getItemWeight());
                    notifyDataSetChanged();
                }
                else{
                    essential.show("Important item!!Should carry required minimum amount",Essential.INFO);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTv;
        TextView infoTv;
        ImageView itemImage;
        TextView currentCountTv;
        Button addItem;
        Button subItem;
        int itemMax;
        int itemMin;
        int c;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTv = (TextView) itemView.findViewById(R.id.bagItemTitleTv);
            infoTv = (TextView) itemView.findViewById(R.id.bagItemDescTv);
            itemImage = (ImageView) itemView.findViewById(R.id.bagItemImgV);
            currentCountTv = (TextView) itemView.findViewById(R.id.itemCountTv);
            addItem = (Button) itemView.findViewById(R.id.goBagAddBt);
            subItem = (Button) itemView.findViewById(R.id.goBagSubBt);

        }
    }
}
