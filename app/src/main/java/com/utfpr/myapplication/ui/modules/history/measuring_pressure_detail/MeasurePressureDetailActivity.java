package com.utfpr.myapplication.ui.modules.history.measuring_pressure_detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.utfpr.myapplication.R;
import com.utfpr.myapplication.models.History;
import com.utfpr.myapplication.models.HistoryChartEntry;
import com.utfpr.myapplication.ui.common.BaseActivity;
import com.utfpr.myapplication.databinding.ActivityMeasurePressureDetailBinding;
import com.utfpr.myapplication.utils.ColorUtils;
import com.utfpr.myapplication.utils.StringUtils;

import java.util.ArrayList;

public class MeasurePressureDetailActivity extends BaseActivity<MeasurePressureDetailViewModel, ActivityMeasurePressureDetailBinding>{

    private static final String HISTORY_KEY = "history-key";
    private static final String HISTORY_ID_KEY = "history-id-key";

    public static void launchWithId(Context context, String id){
        Intent intent = new Intent(context, MeasurePressureDetailActivity.class);
        intent.putExtra(HISTORY_ID_KEY, id);
        context.startActivity(intent);
    }

    public static void launchWithHistory(Context context, History history){
        Intent intent = new Intent(context, MeasurePressureDetailActivity.class);
        intent.putExtra(HISTORY_KEY, history);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if(getIntent() != null){
            if(getIntent().hasExtra(HISTORY_KEY)){
                initViews(getIntent().getParcelableExtra(HISTORY_KEY));
            }

            if(getIntent().hasExtra(HISTORY_ID_KEY)){
                getViewModel().loadHistory(FirebaseAuth.getInstance().getUid(), getIntent().getStringExtra(HISTORY_ID_KEY));
                observeViewModel();
            }
        }

    }

    private void observeViewModel() {
        getViewModel().getHistoryMutableLiveData().observe(this, history -> {
            if(history != null){
                initViews(history);
            }
        });
    }

    private void initViews(History history) {
        getDataBind().resultTextview.setText(StringUtils.getResultType(this, history.getResult()));
        getDataBind().resultTextview.setTextColor(ColorUtils.getResultTypeColor(this, history.getResult()));
        getDataBind().whatDoNowTextview.setText(StringUtils.getWhatToDoTest(this, history.getResult()));
        setUpGraph(history);
    }

    private void setUpGraph(History history){

        getDataBind().chart.setDrawGridBackground(false);

        // no description text
        getDataBind().chart.getDescription().setEnabled(false);

        // enable touch gestures
        getDataBind().chart.setTouchEnabled(true);

        // enable scaling and dragging
        getDataBind().chart.setDragEnabled(false);
        getDataBind().chart.setScaleEnabled(false);
         getDataBind().chart.setScaleXEnabled(false);
         getDataBind().chart.setScaleYEnabled(false);

        // if disabled, scaling can be done on x- and y-axis separately
        getDataBind().chart.setPinchZoom(true);

        // x-axis limit line
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        XAxis xAxis = getDataBind().chart.getXAxis();

//        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setEnabled(true);
        //xAxis.addLimitLine(llXAxis); // add x-axis limit line

//        LimitLine ll1 = new LimitLine(150f, "Upper Limit");
//        ll1.setLineWidth(4f);
//        ll1.enableDashedLine(10f, 10f, 0f);
//        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
//        ll1.setTextSize(10f);

        YAxis rightAxis = getDataBind().chart.getAxisRight();
        rightAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
//        rightAxis.addLimitLine(ll1);
        rightAxis.setAxisMaximum(150f);
        rightAxis.setAxisMinimum(-50f);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setValueFormatter(new MyCustomYAxisValueFormatter());

        // limit lines are drawn behind data (and not on top)
        rightAxis.setDrawLimitLinesBehindData(false);
        rightAxis.setDrawLabels(false);

        getDataBind().chart.getAxisLeft().setEnabled(true);

        // add data
        setData(history.getEntries());

        getDataBind().chart.animateX(2500);

        // get the legend (only possible after setting data)
        Legend l = getDataBind().chart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);

        //dont forget to refresh the drawing
         getDataBind().chart.invalidate();
    }

    private void setData(ArrayList<HistoryChartEntry> entries) {

        ArrayList<Entry> values = new ArrayList<>();

        for (HistoryChartEntry historyChartEntry : entries) {
            values.add(new Entry(historyChartEntry.getX(), historyChartEntry.getY()));
        }

        LineDataSet set1;

        if (getDataBind().chart.getData() != null &&
                getDataBind().chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet)getDataBind().chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            getDataBind().chart.getData().notifyDataChanged();
            getDataBind().chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "Batimentos");

            set1.setDrawIcons(false);
            set1.setDrawValues(false);

            // set the line to be drawn like this "- - - - - -"
            set1.setColor(Color.RED);
            set1.setLineWidth(2f);

            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(true);
            set1.setDrawCircles(true);
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            }
            else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            getDataBind().chart.setData(data);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public MeasurePressureDetailViewModel getViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(MeasurePressureDetailViewModel.class);
    }

    @Override
    public Integer getActivityLayout() {
        return R.layout.activity_measure_pressure_detail;
    }
}
