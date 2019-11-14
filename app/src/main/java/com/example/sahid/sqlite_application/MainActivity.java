package com.example.sahid.sqlite_application;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    EditText editName,editSurname,editId;
    Button btnInsert;
    Button btnviewAll;
    Button btnUpdate;
    Button btnDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB=new DatabaseHelper(this);

        editName=(EditText)findViewById(R.id.editText);
        editSurname=(EditText)findViewById(R.id.editText2);
        editId=(EditText)findViewById(R.id.editText3);

        btnInsert=(Button)findViewById(R.id.button);
        btnviewAll=(Button)findViewById(R.id.button2);
        btnUpdate=(Button)findViewById(R.id.button3);
        btnDelete=(Button)findViewById(R.id.button4);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
    }
    public void  DeleteData()
    {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Integer deletedRows = myDB.deleteData(editId.getText().toString());
                if (deletedRows > 0)
                    Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();
            }
        });
    }

    public  void UpdateData()
    {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean isUpdated = myDB.updateData(editId.getText().toString(),
                        editName.getText().toString(),editSurname.getText().toString());
                if (isUpdated  == true)
                {
                    Toast.makeText(MainActivity.this,"Data Updated",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public  void AddData()
    {
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean isInserted= myDB.insertData(editName.getText().toString(),
                        editSurname.getText().toString());
                if (isInserted == true)
                    Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void viewAll()
    {
        btnviewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Cursor res =myDB.getAllData();
                if(res.getCount()==0)
                {
                    //show message
                    showMessage("Error","No Data Found");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while (res.moveToNext())
                {
                    buffer.append("ID : "+res.getString(0)+"\n");
                    buffer.append("Name : "+res.getString(1)+"\n");
                    buffer.append("Surname :"+res.getString(2)+"\n\n");
                }
                //show all data
                showMessage("Data",buffer.toString());
            }
        });
    }
    public  void showMessage(String title,String Message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
