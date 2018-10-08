package com.example.user.drawpolygon;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.drawpolygon.data.model.Geo;

import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;

public class GeoTableDataAdapter extends TableDataAdapter<Geo> {

    private static final int TEXT_SIZE = 18;

    public GeoTableDataAdapter(Context context, List<Geo> data) {
        super(context, data);
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        Geo geo = getRowData(rowIndex);
        View renderedView = null;
        switch (columnIndex){
            case 0:
                renderedView = renderString(String.valueOf(geo.getPointIndex()));
                break;
            case 1:
                renderedView = renderString(String.valueOf(geo.getLatitude()));
                break;
            case 2:
                renderedView = renderString(String.valueOf(geo.getLongitude()));
                break;
        }
        return renderedView;
    }

    private View renderString(final String value){
        final TextView textView = new TextView(getContext());
        textView.setText(value);
        textView.setPadding(20, 10, 20, 10);
        textView.setTextSize(TEXT_SIZE);
        textView.setTextColor(Color.BLACK);
        return textView;
    }
}
