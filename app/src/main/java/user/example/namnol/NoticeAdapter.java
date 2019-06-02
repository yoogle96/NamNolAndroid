package user.example.namnol;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import user.example.namnol.NoticeDTO;
import user.example.namnol.R;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

    //데이터 배열 선언
    private ArrayList<NoticeDTO> mList;

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvWriter, tvContent, tvPubDate;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvWriter = (TextView) itemView.findViewById(R.id.tv_writer);
            tvPubDate = (TextView) itemView.findViewById(R.id.tv_pub_date);
        }
    }

    //생성자
    public NoticeAdapter(ArrayList<NoticeDTO> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public NoticeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeAdapter.ViewHolder holder, int position) {

        holder.tvTitle.setText(String.valueOf(mList.get(position).getTitle()));
        holder.tvWriter.setText(String.valueOf(mList.get(position).getWriter()));
        holder.tvPubDate.setText(String.valueOf(mList.get(position).getPub_date()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}