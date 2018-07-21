package com.utfpr.myapplication.ui.modules.history.measuring_pressure_detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

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
import com.utfpr.myapplication.ui.common.BaseActivity;
import com.utfpr.myapplication.databinding.ActivityMeasurePressureDetailBinding;

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

        if(getIntent() != null){
            if(getIntent().hasExtra(HISTORY_KEY)){
                initViews(getIntent().getParcelableExtra(HISTORY_KEY));
            }

            if(getIntent().hasExtra(HISTORY_ID_KEY)){
                getViewModel().loadHistory(FirebaseAuth.getInstance().getUid(), getIntent().getStringExtra(HISTORY_ID_KEY));
                observeViewModel();
            }
        }

        setUpGraph();
    }

    private void observeViewModel() {
        getViewModel().getHistoryMutableLiveData().observe(this, history -> {
            if(history != null){
                initViews(history);
            }
        });
    }

    private void initViews(History history) {

    }

    private void setUpGraph(){

        getDataBind().chart.setDrawGridBackground(false);

        // no description text
        getDataBind().chart.getDescription().setEnabled(false);

        // enable touch gestures
        getDataBind().chart.setTouchEnabled(true);

        // enable scaling and dragging
        getDataBind().chart.setDragEnabled(true);
        getDataBind().chart.setScaleEnabled(true);
        // getDataBind().chart.setScaleXEnabled(true);
        // getDataBind().chart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        getDataBind().chart.setPinchZoom(true);

        // set an alternative background color
        // getDataBind().chart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
//        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
//        mv.setChartView(getDataBind().chart); // For bounds control
//        getDataBind().chart.setMarker(mv); // Set the marker to the chart

        // x-axis limit line
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        XAxis xAxis = getDataBind().chart.getXAxis();

        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
        //xAxis.addLimitLine(llXAxis); // add x-axis limit line



//        LimitLine ll1 = new LimitLine(150f, "Upper Limit");
//        ll1.setLineWidth(4f);
//        ll1.enableDashedLine(10f, 10f, 0f);
//        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
//        ll1.setTextSize(10f);

        YAxis leftAxis = getDataBind().chart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
//        leftAxis.addLimitLine(ll1);
        leftAxis.setAxisMaximum(150f);
        leftAxis.setAxisMinimum(-50f);
        leftAxis.setDrawGridLines(false);
        //leftAxis.setYOffset(20f);
//        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(false);
        leftAxis.setDrawLabels(false);

        getDataBind().chart.getAxisRight().setEnabled(false);

        //getDataBind().chart.getViewPortHandler().setMaximumScaleY(2f);
        //getDataBind().chart.getViewPortHandler().setMaximumScaleX(2f);

        // add data

        setData(10, 30);
//        getDataBind().chart.setVisibleXRange(20);
//        getDataBind().chart.setVisibleYRange(20f, AxisDependency.LEFT);
//        getDataBind().chart.centerViewTo(20, 50, AxisDependency.LEFT);

        getDataBind().chart.animateX(2500);
        //getDataBind().chart.invalidate();

        // get the legend (only possible after setting data)
        Legend l = getDataBind().chart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);

        // // dont forget to refresh the drawing
        // getDataBind().chart.invalidate();
    }

    private void setData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            if (i%3 == 0) {
                values.add(new Entry(i, 100));
            }else {
                values.add(new Entry(i, 0));
            }
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
//            set1.enableDashedLine(10f, 5f, 0f);
//            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.RED);
//            set1.setCircleColor(Color.RED);
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
    public MeasurePressureDetailViewModel getViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(MeasurePressureDetailViewModel.class);
    }

    @Override
    public Integer getActivityLayout() {
        return R.layout.activity_measure_pressure_detail;
    }
}
