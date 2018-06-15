package com.example.a009x.tipcalculator_a6;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TipLayout extends RelativeLayout implements View.OnClickListener {

    private TextView billDateTextView;
    private TextView billAmountTextView;
    private TextView tipPercentTextView;
    private Button deleteButton;

    private Context context;
    private TipDB db;
    private Tip tip;

    public TipLayout(Context context){
        super(context);
    }

    public TipLayout(Context context, Tip tip){
        super(context);
        db = new TipDB(context);

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.listview_tip, this, true);

        billDateTextView=(TextView) findViewById(R.id.billDateTextView);
        billAmountTextView=(TextView) findViewById(R.id.billAmountTextView);
        tipPercentTextView=(TextView) findViewById(R.id.tipPercentTextView);
        deleteButton=(Button) findViewById(R.id.deleteButton);

        deleteButton.setOnClickListener(this);

        setTip(tip);



    }


    public void setTip(Tip tip) {
        this.tip = tip;

        billDateTextView.setText(tip.getDateStringFormatted());
        billAmountTextView.setText(tip.getBillAmountFormatted());
        tipPercentTextView.setText(tip.getTipPercentFormatted());

    }

    @Override
    public void onClick(View view) {
        db.deleteTip(tip.getId());
        context.startActivity(new Intent(context, TipHistoryActivity.class));
    }
}
