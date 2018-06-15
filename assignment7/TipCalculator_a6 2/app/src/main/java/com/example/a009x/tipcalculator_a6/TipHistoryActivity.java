package com.example.a009x.tipcalculator_a6;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class TipHistoryActivity extends Activity {

    private ListView tipListView;
    private TipDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_history);

        tipListView = (ListView) findViewById(R.id.tipListView);
        db = new TipDB(this);
    }

    @Override
    public void onResume(){
        super.onResume();

        //get the tips from the database
        ArrayList<Tip> tips = db.getBills();
        TipListAdapter adapter = new TipListAdapter(this, tips);
        tipListView.setAdapter(adapter);

    }
}