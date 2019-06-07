package user.example.namnol;

import android.content.Context;
import android.widget.ArrayAdapter;

public class spinnerAdapter extends ArrayAdapter<String> {
    public spinnerAdapter(Context context, int textViewResourceId){
        super(context,textViewResourceId);
    }

    @Override
    public int getCount(){
        int count = super.getCount();
        return count>0 ? count-1 : count;
    }
}
