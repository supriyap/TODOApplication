package supersample.todoapplication.fragment;


import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import supersample.todoapplication.R;

/**
 * Edit TODOitem fragment
 */
public class EditDialogFragment extends DialogFragment implements View.OnClickListener {


    private final static String BUNDLE_KEY = "fragment_bundle_key";
    private EditText editItem;
    private Button saveButton;
    private DialogFragmentListener mDialogFragmentListener;

    public EditDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.edit_item_layout, container, false);
        editItem = (EditText) rootView.findViewById(R.id.edit_item_edittext);
        saveButton = (Button) rootView.findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);
        String editItemText = getArguments().getString(BUNDLE_KEY);
        if (!TextUtils.isEmpty(editItemText)) {
            editItem.setText(editItemText);
            editItem.setSelection(editItemText.length());
        }
        getDialog().setTitle(getResources().getString(R.string.edit_layout_title));
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public static EditDialogFragment newInstance(String item) {
        EditDialogFragment editDialogFragment = new EditDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY, item);
        editDialogFragment.setArguments(bundle);
        return editDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_button:
                if (!TextUtils.isEmpty(editItem.getText().toString())) {
                    mDialogFragmentListener.onSave(editItem.getText().toString());
                    this.dismiss();
                }
                break;
        }
    }


    public interface DialogFragmentListener {
        void onSave(String updatedString);
    }

    public void setDialogFragmentListener(DialogFragmentListener listener) {
        this.mDialogFragmentListener = listener;
    }
}
