package com.example.durbinlabs.alarmmanager;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {
    private EditText t4, title, message, date, time;
   // private AdminSQLiteOpenHelper admin;
    private SQLiteDatabase bd;
    private ContentValues registro;
    private EditText tvDisplayDate;
    private DatePicker dpResult;
    private Button btnChangeDate;
    // date
    private int year;
    private int month;
    private int day;
    static final int DATE_DIALOG_ID = 999;

    PendingIntent pendingIntent;
    // hora
    private int minute;
    private int hour;
    private TimePicker timePicker;
    private TextView textViewTime;
    private Button button;
    private static final int TIME_DIALOG_ID = 998;
    Calendar calendario = Calendar.getInstance();
    int hora, min,dia,mes,ano,hora11;
    String cadenaF, cadenaH,fecha_sistema,hora_sistema;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarma);

       //admin = new AdminSQLiteOpenHelper(this, vars.bd, null, vars.version);
       // bd = admin.getWritableDatabase();
        dia = calendario.get(Calendar.DAY_OF_MONTH);
        mes = calendario.get(Calendar.MONTH)+1;
        ano = calendario.get(Calendar.YEAR);
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        min = calendario.get(Calendar.MINUTE);
        fecha_sistema=mes+"-"+dia+"-"+ano+" ";
        hora_sistema = hora+":"+min;
        setCurrentDateOnView();
        addListenerOnButton();
        setCurrentTimeOnView();
        title = (EditText) findViewById(R.id.title);
        message = (EditText) findViewById(R.id.message);
        date = (EditText) findViewById(R.id.date);
        time = (EditText) findViewById(R.id.time_et);
       // service();
    }
    public void service() {
        Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis(); //first run of alarm is immediately //
        int intervalMillis = 1 * 3 * 1000; //3 Second
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis, intervalMillis, pIntent);
    }

    public void llenar111(View view) {

       notification(hour,minute);
       Toast.makeText(getApplicationContext(), "alarm registered", Toast.LENGTH_LONG).show();
    }
    public void setCurrentTimeOnView() {

        textViewTime = (EditText) findViewById(R.id.time_et);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);
    }

    public void addListenerOnButton() {

        btnChangeDate = (Button) findViewById(R.id.setdate);
        btnChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID);

            }

        });
        // hora

        button = (Button) findViewById(R.id.settime_btn);
        button.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                showDialog(TIME_DIALOG_ID);

            }

        });

    }
    // hora

    public void setCurrentDateOnView() {

        tvDisplayDate = (EditText) findViewById(R.id.date);
        dpResult = (DatePicker) findViewById(R.id.datePicker);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        tvDisplayDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));

        // set current date into datepicker
        dpResult.init(year, month, day, null);

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);
            case TIME_DIALOG_ID:

                return new TimePickerDialog(this, timePickerListener, hour, minute,false);

        }
        return null;
    }
    // hora
    private TimePickerDialog.OnTimeSetListener timePickerListener =  new TimePickerDialog.OnTimeSetListener() {

        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            hour = selectedHour;
            minute = selectedMinute;
            textViewTime.setText(new StringBuilder().append(padding_str(hour)).append(":").append(padding_str(minute)));
            timePicker.setCurrentHour(hour);
            timePicker.setCurrentMinute(minute);
        }
    };

    private static String padding_str(int c) {

        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
    // hora
    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            tvDisplayDate.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));
            // set selected date into datepicker also
            dpResult.init(year, month, day, null);
        }
    };

    private void notification(int h ,int m ){
        //Locale alocal = new Locale
        int intervalMillis = 1 * 3 * 1000; //3 Second
        Calendar calendar = Calendar.getInstance();
        // we can set time by open date and time picker dialog
        calendar.set(Calendar.HOUR_OF_DAY, h);
        calendar.set(Calendar.MINUTE,m);
        calendar.set(Calendar.SECOND, 0);

        //Toast.makeText(this,  showTime(h,m), Toast.LENGTH_SHORT).show();

        Intent intent1 = new Intent(AlarmActivity.this, MyAlarmReceiver.class);
        pendingIntent = PendingIntent.getService( this,0,intent1, pendingIntent.FLAG_UPDATE_CURRENT);
        pendingIntent = PendingIntent.getBroadcast(
                this,0,intent1,
                pendingIntent.FLAG_UPDATE_CURRENT

        );
        if(pendingIntent == null) {
            int iki = 10;
        }

        AlarmManager  first_am = (AlarmManager) AlarmActivity.this
                .getSystemService(AlarmActivity.this.ALARM_SERVICE);
        first_am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),// Here the Alarm is Set
                AlarmManager.INTERVAL_DAY, pendingIntent);


        /*AlarmManager  first_am = (AlarmManager) AlarmActivity.this
                .getSystemService(AlarmActivity.this.ALARM_SERVICE);
        first_am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),// Here the Alarm is Set
                intervalMillis, pendingIntent);*/
        //  alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis, intervalMillis, pIntent);

    }

}
