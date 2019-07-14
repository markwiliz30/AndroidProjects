package com.example.crudapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class OnClickListenerCreateStudent implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        final Context context = v.getRootView().getContext();

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.student_input_form, null, true);

        final EditText editTextStudentFirstname = (EditText)formElementsView.findViewById(R.id.editTextStudentFirstname);
        final EditText editTextStudentEmail = (EditText)formElementsView.findViewById(R.id.editTextStudentEmail);

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Crete Student")
                .setPositiveButton("Add",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                                String studentFirstname = editTextStudentFirstname.getText().toString();
                                String studentEmail = editTextStudentEmail.getText().toString();

                                ObjectStudent objectStudent = new ObjectStudent();
                                objectStudent.firstname = studentFirstname;
                                objectStudent.email = studentEmail;

                                boolean createSuccessful = new TableControllerStudent(context).create(objectStudent);

                                if(createSuccessful){
                                    Toast.makeText(context, "Student information was saved.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Unable to save student information.", Toast.LENGTH_SHORT).show();
                                }

                                ((MainActivity)context).countRecords();
                                ((MainActivity)context).readRecords();
                            }
                        }).show();
    }
}
