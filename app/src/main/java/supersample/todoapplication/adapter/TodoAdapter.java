package supersample.todoapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import supersample.todoapplication.R;
import supersample.todoapplication.model.TodoItem;

public class TodoAdapter extends BaseAdapter {

    private ArrayList<TodoItem> mTodoList;
    private Context mContext;

    public TodoAdapter(Context context, ArrayList<TodoItem> todoList) {
        mContext = context;
        mTodoList = todoList;
    }

    @Override
    public int getCount() {
        return mTodoList == null ? 0 : mTodoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTodoList == null? null :mTodoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.todo_list_item, parent, false);
        }
        TextView todoText = (TextView) convertView.findViewById(R.id.itemtext);
        TodoItem item = (TodoItem) getItem(position);
        todoText.setText(item.getTodoItem());

        return convertView;
    }
}
