package user.example.namnol;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ArrayAdapter;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder>{
    private ArrayList<RoomDTO> mList;


    // 아이템 클릭시 실행 함수
    private ItemClick itemClick;

    public interface ItemClick{
        public void onClick(View view);
    }

    // 아이템 클릭시 실행 함수 등록 함수
    public void setItemClick(ItemClick itemClick){
        this.itemClick = itemClick;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvKind, tvCurr, tvKey;
        public final View mView;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvKind = (TextView) itemView.findViewById(R.id.tv_kind);
            tvCurr = (TextView) itemView.findViewById(R.id.tv_curr);
            tvKey = (TextView) itemView.findViewById(R.id.tv_key);
        }
    }

    public RoomAdapter(ArrayList<RoomDTO> list){
        this.mList = list;
    }

    @NonNull
    @Override
    public RoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item, parent, false);
        return new RoomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomAdapter.ViewHolder holder, int position) {
        final int Position = position;

        holder.tvTitle.setText(String.valueOf(mList.get(position).getTitle()));
        holder.tvKind.setText(String.valueOf(mList.get(position).getKind()));
        holder.tvCurr.setText(String.valueOf(mList.get(position).getCurr()));
        holder.tvKey.setText(String.valueOf(mList.get(position).getKey()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemClick != null){
                    itemClick.onClick(view);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
