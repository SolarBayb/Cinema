package lpnu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Matrix<T> {
    private int rows;
    private int columns;

    @JsonIgnore
    private List<List<T>> data;

    @JsonIgnore
    private T value;

    public Matrix(){

    }

    public Matrix(int rows, int columns, T value) {
        this.rows = rows;
        this.columns = columns;
        data = new ArrayList<>();
        for (int i = 0; i < getRows(); i++) {
            ArrayList<T> list = new ArrayList<>();
            for (int k = 0; k < getColumns(); k++) {
                list.add(value);
            }
            data.add(list);
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public List<List<T>> getData() {
        return data;
    }

    public void setData(List<List<T>> data) {
        this.data = data;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
