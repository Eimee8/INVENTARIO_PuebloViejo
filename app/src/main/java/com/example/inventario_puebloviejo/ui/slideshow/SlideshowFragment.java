package com.example.inventario_puebloviejo.ui.slideshow;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.inventario_puebloviejo.BroadcastReceiver;
import com.example.inventario_puebloviejo.MainActivity;
import com.example.inventario_puebloviejo.NotificationReceiver;
import com.example.inventario_puebloviejo.R;
import com.example.inventario_puebloviejo.databinding.FragmentSlideshowBinding;
import com.example.inventario_puebloviejo.db.DataBase;
import com.example.inventario_puebloviejo.db.Date;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class SlideshowFragment extends Fragment {
    private static final int REQUEST_CODE_CREATE_PDF = 123;
    Spinner spinnerTipo;
    Spinner spinnerEstatus;

    EditText serie, descripcion;
    Spinner tipo, status;
    Button btnGuardar, btnllegada, btnentrega;

    DataBase db;
    ArrayAdapter<CharSequence> spinnerAdapter;
    ArrayAdapter<CharSequence> spinnerEstatusAdapter;
    private ArrayList<Date> date;
    private Calendar calendarLlegada;
    private Calendar calendarEntrega;

    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = new DataBase(this.getContext());

        date = new ArrayList();

        date = db.mostrarEquipos();

        spinnerTipo = root.findViewById(R.id.TipoAgenda);
        CharSequence[] opciones = getResources().getTextArray(R.array.opciones_tipoAgenda);
        spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, opciones);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(spinnerAdapter);

        spinnerEstatus = root.findViewById(R.id.EstatusAgenda);
        CharSequence[] opcionesEstatus = getResources().getTextArray(R.array.opciones_estatusAgenda);
        spinnerEstatusAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, opcionesEstatus);
        spinnerEstatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstatus.setAdapter(spinnerEstatusAdapter);

        serie = root.findViewById(R.id.ingresarid);
        descripcion = root.findViewById(R.id.descripcionAgenda);
        tipo = root.findViewById(R.id.TipoAgenda);
        status = root.findViewById(R.id.EstatusAgenda);

        btnllegada = root.findViewById(R.id.fchllegada);
        calendarLlegada = Calendar.getInstance();

        btnllegada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDatePickerDialog();
            }
        });

        btnentrega = root.findViewById(R.id.fchentrega);
        calendarEntrega = Calendar.getInstance();

        btnentrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDatePickerDialogE();
            }
        });

        btnentrega = root.findViewById(R.id.fchentrega);
        Calendar calendarEntrega = Calendar.getInstance();

        btnGuardar = root.findViewById(R.id.guardar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serie = binding.ingresarid.getText().toString();
                String descripcion = binding.descripcionAgenda.getText().toString();
                String tipo = spinnerTipo.getSelectedItem().toString();
                String status = spinnerEstatus.getSelectedItem().toString();
                String formatoFecha = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(formatoFecha, Locale.getDefault());
                String fecha_llegada = sdf.format(calendarLlegada.getTime());
                String fecha_entrega = sdf.format(calendarEntrega.getTime());

                if (serie.isEmpty() || descripcion.isEmpty() || tipo.isEmpty() || status.isEmpty()) {
                    Toast.makeText(getContext(), "Campos Vacíos", Toast.LENGTH_SHORT).show();
                } else {

                    boolean equipoAreaExistente = db.equipoAreaExistente(serie);

                    if (equipoAreaExistente) {
                        Toast.makeText(getContext(), "El equipo ya existe", Toast.LENGTH_SHORT).show();
                    } else {

                        boolean correct = db.insertEquipoAgenda(serie, tipo, status, descripcion, fecha_llegada, fecha_entrega);

                        if (correct) {
                            Toast.makeText(getContext(), "Equipo registrado correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Error al registrar el equipo", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });


        return root;
    }

    private void mostrarDatePickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendarLlegada.set(Calendar.YEAR, year);
                calendarLlegada.set(Calendar.MONTH, monthOfYear);
                calendarLlegada.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                actualizarFechaSeleccionada();
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                dateSetListener,
                calendarLlegada.get(Calendar.YEAR),
                calendarLlegada.get(Calendar.MONTH),
                calendarLlegada.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
    private void actualizarFechaSeleccionada() {
        String formatoFecha = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(formatoFecha, Locale.getDefault());

        String fechaSeleccionada = sdf.format(calendarLlegada.getTime());
        Toast.makeText(requireContext(), "Fecha seleccionada: " + fechaSeleccionada, Toast.LENGTH_SHORT).show();
    }

    private void mostrarDatePickerDialogE() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendarEntrega.set(Calendar.YEAR, year);
                calendarEntrega.set(Calendar.MONTH, monthOfYear);
                calendarEntrega.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                actualizarFechaSeleccionadaE();
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                dateSetListener,
                calendarEntrega.get(Calendar.YEAR),
                calendarEntrega.get(Calendar.MONTH),
                calendarEntrega.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void actualizarFechaSeleccionadaE() {
        String formatoFecha = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(formatoFecha, Locale.getDefault());

        String fechaSeleccionada = sdf.format(calendarEntrega.getTime());
        Toast.makeText(requireContext(), "Fecha seleccionada: " + fechaSeleccionada, Toast.LENGTH_SHORT).show();

        agregarNotificacion(calendarLlegada);
    }

    private void agregarNotificacion(Calendar fechaNotificacion) {
        NotificationManager notificationManager = (NotificationManager) requireContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String canalId = "canal_id";
            CharSequence nombreCanal = "Nombre del Canal";
            String descripcionCanal = "Descripción del Canal";
            int importancia = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel canal = new NotificationChannel(canalId, nombreCanal, importancia);
            canal.setDescription(descripcionCanal);

            notificationManager.createNotificationChannel(canal);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), "canal_id")
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("Recordatorio")
                .setContentText("Tu evento está programado para mañana")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        fechaNotificacion.add(Calendar.DAY_OF_MONTH, -1);
        long tiempoNotificacion = fechaNotificacion.getTimeInMillis();

        Intent intent = new Intent(requireContext(), NotificationReceiver.class);
        int requestCode = 1;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(requireContext(), requestCode, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, tiempoNotificacion, pendingIntent);

        int notificationId = 1;
        notificationManager.notify(notificationId, builder.build());
    }

}