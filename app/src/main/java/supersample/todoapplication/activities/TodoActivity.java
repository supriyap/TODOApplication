package supersample.todoapplication.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import supersample.todoapplication.R;
import supersample.todoapplication.adapter.TodoAdapter;
import supersample.todoapplication.fragment.EditDialogFragment;
import supersample.todoapplication.model.TodoItem;
import supersample.todoapplication.utils.SQLiteDatabaseHelper;

public class TodoActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {
    private ListView mListView;
    private EditText todoEditText;
    private Button mAddbtn;
    private SQLiteDatabaseHelper databaseHelper;
    private ArrayList<TodoItem> mTodoList = new ArrayList<>();
    private TodoAdapter mTodoAdapter;
    private int editPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        mListView = (ListView) findViewById(R.id.todolistview);
        todoEditText = (EditText) findViewById(R.id.newitemtext);
        mAddbtn = (Button) findViewById(R.id.addbtn);
        mAddbtn.setOnClickListener(this);

        databaseHelper = new SQLiteDatabaseHelper(this);
        if (databaseHelper.getToDoItemListFromTable() != null) {
            ArrayList<TodoItem> todoItemList = databaseHelper.getToDoItemListFromTable();
            mTodoList.addAll(todoItemList);

        }
        mTodoAdapter = new TodoAdapter(this, mTodoList);
        mListView.setAdapter(mTodoAdapter);
        mListView.setOnItemLongClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addbtn:
                if (!TextUtils.isEmpty(todoEditText.getText().toString())) {
                    addBtnClicked(todoEditText.getText().toString());
                    todoEditText.setText("");
                }
                break;
        }
    }

    private void addBtnClicked(String newItem) {
        TodoItem item = new TodoItem();
        item.setTodoItem(newItem);
        mTodoList.add(item);
        mTodoAdapter.notifyDataSetChanged();
        databaseHelper.insertTodoItemInDatabase(newItem);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        TodoItem item = (TodoItem) parent.getItemAtPosition(position);
        editPosition = position;
        inflateEditItemFragment(item.getTodoItem());
        return true;
    }

    /**
     * Show dialog fragment on Long click event on list item
     *
     * @param editItem
     */
    private void inflateEditItemFragment(String editItem) {
        EditDialogFragment editDialogFragment = EditDialogFragment.newInstance(editItem);
        editDialogFragment.setDialogFragmentListener(mDialogFragmentListener);
        editDialogFragment.show(getFragmentManager(), "EDITFRAGMENT");
    }

    /**
     * Listener to get Get Updated String from EditDialog Fragment
     */
    private EditDialogFragment.DialogFragmentListener mDialogFragmentListener = new EditDialogFragment.DialogFragmentListener() {
        @Override
        public void onSave(String updatedString) {
            String oldItemString = mTodoList.get(editPosition).getTodoItem();
            TodoItem item = new TodoItem();
            item.setTodoItem(updatedString);
            mTodoList.set(editPosition, item);
            mTodoAdapter.notifyDataSetChanged();
            databaseHelper.updateTodoItem(updatedString, oldItemString);
            Toast.makeText(TodoActivity.this, "Successfully Updated", Toast.LENGTH_LONG).show();
        }
    };


}
