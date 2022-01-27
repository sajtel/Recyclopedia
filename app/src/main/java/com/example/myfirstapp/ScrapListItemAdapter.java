package com.example.myfirstapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScrapListItemAdapter extends RecyclerView.Adapter<ScrapListItemAdapter.ScrapViewHolder> {

    Context mContext;
    private Cursor mCursor;
    Activity activity;
    ArrayList<Scraps> scrapList;
    ScrapDBHelper dbHelper;

    //Creating Constructor

    public ScrapListItemAdapter(Context context, Activity activity, ArrayList<Scraps> scrapList){
        this.mContext = context;
        this.activity = activity;
        this.scrapList = scrapList;
    }

    //Calling on the RecyclerView ViewHolder Object

    public class ScrapViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;
        public TextView dateText;
        public TextView IDText;
        public TextView viewMore;

        public ScrapViewHolder(View itemView) {
            super(itemView);
            IDText = itemView.findViewById(R.id.poster_id);
            nameText = itemView.findViewById(R.id.item_name);
            dateText = itemView.findViewById(R.id.item_date);
            viewMore = itemView.findViewById(R.id.view_more);
        }
    }

    //Overriding ScrapViewHolder onCreateViewHolder, OnBindViewHolder, and getItemCount()
    @Override
    public ScrapViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_list_layout, viewGroup, false);
        return new ScrapViewHolder(view);
    }

    //Overriding onBindViewHolder to set text to the column variables
    @Override
    public void onBindViewHolder(ScrapViewHolder holder, int position) {

        holder.nameText.setText(scrapList.get(position).getName());
        holder.IDText.setText(scrapList.get(position).getIdentification());
        holder.dateText.setText(scrapList.get(position).getDateFormatted());

        holder.viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(position);

            }
        });
    }

    //Displaying the dialog in the layout in order to show the scrap item description onCLick view more

    public void showDialog(int pos){
        TextView desc;
        Button back;
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        dialog.setContentView(R.layout.dialog);
        params.copyFrom(dialog.getWindow().getAttributes());
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        back = (Button) dialog.findViewById(R.id.back);
        desc = (TextView) dialog.findViewById(R.id.description);
        desc.setText(scrapList.get(pos).getScrapDescription());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }


    //Overriding getItemCount to retrieve the numbers of rows in the SQL table Scrap list

    @Override
    public int getItemCount() {
        return scrapList.size();
    }

}
