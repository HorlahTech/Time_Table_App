package com.lukman.timetableapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.gms.vision.text.Element;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.lukman.timetableapp.data.TImeTableContract;
import com.lukman.timetableapp.data.TimeTabledbHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ViewTimeTable extends AppCompatActivity {
    public static final String LOG_TAG = ViewTimeTable.class.getSimpleName();
    RecyclerView table_recycler;
    TableAdapter tableAdapter;
    AutoCompleteTextView select_semester,SelectSession;
    ArrayAdapter selectSemesterItems, sessionItems;
    String  selectSemester_item3, selectCourse_item1, way;
    LinearLayout tablevalueId;
    TableLayout tableDataId;
    String[] Semesteritems = {"First Semester", "Second Semester"};
    boolean isFalse = true;
    com.itextpdf.layout.element.Table table;
    Cursor cursor = null;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_time_table);
        table_recycler = findViewById(R.id.table_recycler);

        select_semester = findViewById(R.id.SelectSemester);
        selectSemesterItems = new ArrayAdapter<String>(this, R.layout.dropdown_list, Semesteritems);
        select_semester.setAdapter(selectSemesterItems);
        select_semester.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
              selectSemester_item3 = adapterView.getItemAtPosition(position).toString();
//                values.put(TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_SEMESTER, semester_item3);

            }
        });

        SelectSession = findViewById(R.id.SelectSession);
        sessionItems = new ArrayAdapter<String>(ViewTimeTable.this, R.layout.dropdown_list, getSession());
        SelectSession.setAdapter(sessionItems);
        SelectSession.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selectCourse_item1 = adapterView.getItemAtPosition(position).toString();


            }
        });
        Button tableButton = findViewById(R.id.tableButton);
         tablevalueId = findViewById(R.id.tablevalueId);
         tableDataId = findViewById(R.id.tableDataId);
        tableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFalse = true;
                tablevalueId.setVisibility(View.GONE);
                tableDataId.setVisibility(View.VISIBLE);
                table_recycler.setHasFixedSize(true);
                table_recycler.setLayoutManager(new LinearLayoutManager(ViewTimeTable.this));
                tableAdapter = new TableAdapter(ViewTimeTable.this, getList());
                table_recycler.setAdapter(tableAdapter);

            }
        });
    }
    int counter = 1;
    @Override
    public void onBackPressed() {
        //counter++;
       // if (counter == 1) {
            tablevalueId.setVisibility(View.VISIBLE);
            tableDataId.setVisibility(View.GONE);
            counter = 0;
        //} else  {
         //   super.onBackPressed();
        //}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.download_pdf, menu);
        return isFalse;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch  (item.getItemId()) {
            case R.id.downloadPdf:

                try{

                    try {



                        createpdf();
                    } catch ( FileNotFoundException f ) {
                        f.printStackTrace();
                        Toast.makeText(getApplicationContext(), "hhhhhhhhhh"+ f, Toast.LENGTH_SHORT).show();
                    }
//


//                Toast.makeText(getApplicationContext(), "it workedddddddddddddddd", Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), "please select session and semester\n" + e, Toast.LENGTH_LONG).show();
               }

                return true;


        }


        return super.onOptionsItemSelected(item); }

    private  ArrayList<String> getSession(){

    TimeTabledbHelper timeTabledbHelper = new TimeTabledbHelper(ViewTimeTable.this);
    SQLiteDatabase db = timeTabledbHelper.getReadableDatabase();
    String[] Projection = {

            TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_SESSION,
    };
    timeTabledbHelper.getReadableDatabase();
    ArrayList<String> Sessionitems = new ArrayList<>();

    Cursor cursor = db.query(
            TImeTableContract.ExamScheduleEntry.TABLE_NAME,
            Projection,
            null,
            null,
            null,
            null,
            null
    );
    while (cursor.moveToNext()) {
        String e_Session = cursor.getString(0);
        Sessionitems.add(e_Session);
    }

    cursor.close();
    return Sessionitems;
}


    private List<Table> getList() {

        TimeTabledbHelper timeTabledbHelper = new TimeTabledbHelper(this);
        SQLiteDatabase db = timeTabledbHelper.getReadableDatabase();
        String[] Projection = {
                TImeTableContract.ExamScheduleEntry._ID,
                TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_DATE,
                TImeTableContract.ExamScheduleEntry.COLUMN_COURSE,
                TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_VENUE,
                TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_TIME
        };
        String selection =TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_SESSION + " = ? AND " + TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_SEMESTER + " = ? ";
        String[] selectionArgs = {selectCourse_item1, selectSemester_item3};

        timeTabledbHelper.getReadableDatabase();
        List<Table> tableList = new ArrayList<>();
     //   ArrayList<Table> saveTableArrayData = new ArrayList<>();

        Cursor cursor = db.query(
                TImeTableContract.ExamScheduleEntry.TABLE_NAME,
                Projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            do {
               int id = Integer.parseInt(cursor.getString(0));
                String T_Day = cursor.getString(1);
                String T_Course = cursor.getString(2);
                String T_Venue = cursor.getString(3);
                String T_Time = cursor.getString(4);
                tableList.add(new Table( T_Day, T_Course, T_Venue, T_Time));

            } while (cursor.moveToNext());
        }
        cursor.close();

        return tableList;
    }
//    private List<Table> getListForPDF() {
//
//        TimeTabledbHelper timeTabledbHelper = new TimeTabledbHelper(this);
//        SQLiteDatabase db = timeTabledbHelper.getReadableDatabase();
//        String[] Projection = {
//                TImeTableContract.ExamScheduleEntry._ID,
//                TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_DATE,
//                TImeTableContract.ExamScheduleEntry.COLUMN_COURSE,
//                TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_VENUE,
//                TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_TIME
//        };
//        String selection =TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_SESSION + " = ? AND " + TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_SEMESTER + " = ? ";
//        String[] selectionArgs = {selectCourse_item1, selectSemester_item3};
//
//        timeTabledbHelper.getReadableDatabase();
//        List<Table> tableListPDF = new ArrayList<>();
//     //   ArrayList<Table> saveTableArrayData = new ArrayList<>();
//
//        Cursor cursor = db.query(
                //TImeTableContract.ExamScheduleEntry.TABLE_NAME,
//                Projection,
//                selection,
//                selectionArgs,
//                TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_DATE,
//                null,
//                TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_TIME
//        );
//        if (cursor.moveToFirst()) {
//            do {
//               int id = Integer.parseInt(cursor.getString(0));
//                String T_Day = cursor.getString(1);
//                String T_Course = cursor.getString(2);
//                String T_Venue = cursor.getString(3);
//                String T_Time = cursor.getString(4);
//                tableListPDF.add(new Table( T_Day, T_Course, T_Venue, T_Time));
//
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//
//        return tableListPDF;
    //}
//    private void createpdf( ) throws FileNotFoundException {
//
//        String PDF_Day, PDF_Course, PDF_Venue, PDF_Time, PDF_Semester, PDF_Session;
//        TimeTabledbHelper timeTabledbHelper = new TimeTabledbHelper(this);
//        SQLiteDatabase db = timeTabledbHelper.getReadableDatabase();
//        String[] Projection = {
//                TImeTableContract.ExamScheduleEntry._ID,
//                TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_DATE,
//                TImeTableContract.ExamScheduleEntry.COLUMN_COURSE,
//                TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_VENUE,
//                TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_TIME,
//                TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_SESSION,
//                TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_SEMESTER,
//
//
//        };
//        String selection = TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_SESSION + " = ? AND " + TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_SEMESTER + " = ? ";
//        String[] selectionArgs = {selectCourse_item1, selectSemester_item3};
//
//        timeTabledbHelper.getReadableDatabase();
//      //  List<Table> tableListPDF = new ArrayList<>();
//        //   ArrayList<Table> saveTableArrayData = new ArrayList<>();
//
//        Cursor cursor = db.query(
//                TImeTableContract.ExamScheduleEntry.TABLE_NAME,
//                Projection,
//                selection,
//                selectionArgs,
//                TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_DATE,
//                null,
//                null
//        );
//        if (cursor.moveToFirst()) {
//            do {
//                int id = Integer.parseInt(cursor.getString(0));
//                PDF_Day =  cursor.getString(1);
//                PDF_Course = cursor.getString(2);
//                PDF_Venue =  cursor.getString(3);
//                PDF_Time =  cursor.getString(4);
//
//                Log.d(LOG_TAG, PDF_Day +"\n"+ PDF_Course +"\n"+  PDF_Venue +"\n"+ PDF_Time);
//                if (PDF_Day != null && PDF_Course != null && PDF_Venue != null && PDF_Time != null  ){
//                //table.addCell(new Cell(3, 1).add(new Paragraph(PDF_Day+"")));
//                table.addCell( new Paragraph(PDF_Course+""));
//                table.addCell(new Cell(3, 1).add(new Paragraph(PDF_Venue+"")));
//                table.addCell( new Paragraph(PDF_Time+""));
//                table.addCell("60");
//                }else{
//                    Toast.makeText(getApplicationContext(), "fghjkkjhgfdfgh", Toast.LENGTH_SHORT).show();
//                }
//            } while (cursor.moveToNext());
//            PDF_Semester =(String) cursor.getString(5);
//            PDF_Session = (String) cursor.getString(6);
//
//            //tableListPDF.add(new Table( T_Day, T_Course, T_Venue, T_Time));
//
//            String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
//            File file = new File(pdfPath, PDF_Session+"" + PDF_Semester+"" + "timetable.pdf");
//            OutputStream outputStream = new FileOutputStream(file);
//
//
//
//        PdfWriter writer = new PdfWriter(file);
//        PdfDocument pdfDocument = new PdfDocument(writer);
//        Document document = new Document(pdfDocument);
//
//

//        Drawable fuoyeLogo = getDrawable(R.drawable.timetable_schedule_system);
//        Bitmap bitmap = ((BitmapDrawable) fuoyeLogo).getBitmap();
//        ByteArrayOutputStream stream = new com.itextpdf.io.source.ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] bitmapData = stream.toByteArray();
//
//        ImageData imageData = ImageDataFactory.create(bitmapData);
//        Image image = new Image(imageData);
//        image.setHeight(100);
//        image.setWidth(100);
//        float columnWidth[] = {200f, 200f, 200f, 200f, 200f};
//        com.itextpdf.layout.element.Table table = new com.itextpdf.layout.element.Table(columnWidth);
//
//        table.addCell("Day");
//        table.addCell("Course");
//        table.addCell("Venue");
//        table.addCell("Time");
//        table.addCell("Number of Student");
//
//
////                table.addCell(new Cell(3, 1).add(new Paragraph(PDF_Day)));
////                table.addCell(PDF_Course);
////                table.addCell(new Cell(3, 1).add(new Paragraph(PDF_Venue)));
////                table.addCell(PDF_Time);
////                table.addCell("60");
//
//        table.addCell("Introduction to Programming");
//        table.addCell("12:pm");
//        table.addCell("60");
//
//        table.addCell("Introduction to Programming");
//        table.addCell("12:pm");
//        table.addCell("60");
//        Paragraph paragraph = new Paragraph("Federal UniverSity of Oye Ekiti");
//        document.add(image);
//        document.add(paragraph);
//        document.add(table);
//        document.close();
//        Toast.makeText(getApplicationContext(), "pdf Saved to Phone Download Storage", Toast.LENGTH_SHORT).show();
//
//    }
public void createpdf() throws FileNotFoundException {
    //ActivityCompat.requestPermissions(ViewTimeTable, new String[]{READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
////////////////////////////////////////////////////////////

    TimeTabledbHelper timeTabledbHelper = new TimeTabledbHelper(this);
    SQLiteDatabase db = timeTabledbHelper.getReadableDatabase();
    String[] Projection = {
            TImeTableContract.ExamScheduleEntry._ID,
            TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_DATE,
            TImeTableContract.ExamScheduleEntry.COLUMN_COURSE,
            TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_VENUE,
            TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_TIME,
            TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_SEMESTER,
            TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_SESSION
    };
    String selection = TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_SESSION + " = ? AND " + TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_SEMESTER + " = ? ";
    String[] selectionArgs = {selectCourse_item1, selectSemester_item3};

    timeTabledbHelper.getReadableDatabase();
    Cursor cursor = db.query(
            TImeTableContract.ExamScheduleEntry.TABLE_NAME,
            Projection,
            selection,
            selectionArgs,
            TImeTableContract.ExamScheduleEntry.COLUMN_EXAM_DATE,
            null,
            null
    );



    Drawable logo = getDrawable(R.drawable.timetable_schedule_system);
    Bitmap bitmap = ((BitmapDrawable) logo).getBitmap();
    ByteArrayOutputStream stream = new com.itextpdf.io.source.ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
    byte[] bitmapData = stream.toByteArray();

    ImageData imageData = ImageDataFactory.create(bitmapData);
    Image image = new Image(imageData);
    image.setHeight(100);
    image.setWidth(100);
    float[] columnWidth = {200f, 200f, 200f, 200f, 200f};
    com.itextpdf.layout.element.Table table = new com.itextpdf.layout.element.Table(columnWidth);

    table.addCell("Day");
    table.addCell("Course");
    table.addCell("Venue");
    table.addCell("Time");
    table.addCell("Number of Student");
//    String session = cursor.getString(5);
//    String semester = cursor.getString(6);
    if (cursor.moveToFirst()) {

       do{
        int id = Integer.parseInt(cursor.getString(0));
        String date = cursor.getString(1);
        String course = cursor.getString(2);
        String venue = cursor.getString(3);
        String time = cursor.getString(4);

        table.addCell(new Cell().add(new Paragraph(date)));
        table.addCell((new Paragraph(course)));
        table.addCell(new Cell().add(new Paragraph(venue)));
        table.addCell(new Paragraph(time));
        table.addCell("60");
    }while (cursor.moveToNext());


        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        file = new File(pdfPath, "session00" + " "+ "semester2" + " " + "timetable.pdf");
        OutputStream outputStream = new FileOutputStream(file);



        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);
        pdfDocument.setDefaultPageSize(PageSize.A3);
        //PdfWriter.getInstance(document, outputStream);
        Paragraph paragraph = new Paragraph("Federal UniverSity of Oye Ekiti");
        document.add(image);
        document.add(paragraph);
        document.add(table);
        cursor.close();
        document.close();
        Toast.makeText(getApplicationContext(), "it workedddddddddddddddd " , Toast.LENGTH_SHORT).show();
    }



}

}

