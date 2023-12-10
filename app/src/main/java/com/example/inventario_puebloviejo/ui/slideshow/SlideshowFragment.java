package com.example.inventario_puebloviejo.ui.slideshow;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.inventario_puebloviejo.BroadcastReceiver;
import com.example.inventario_puebloviejo.R;
import com.example.inventario_puebloviejo.Registro_equipo;
import com.example.inventario_puebloviejo.databinding.FragmentSlideshowBinding;
import com.example.inventario_puebloviejo.db.DataBase;
import com.example.inventario_puebloviejo.db.Date;

import java.util.ArrayList;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class SlideshowFragment extends Fragment {

    EditText serie, descripcion;
    Spinner tipo, status;
    Button btnGuardar, pdf, btnllegada, btnentrega;

    DataBase db;

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
                String tipo = binding.tipo.getText().toString();
                String status = binding.estatus.getText().toString();
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
                requireContext(), // Utiliza requireContext() para obtener el contexto del fragmento
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
                requireContext(), // Utiliza requireContext() para obtener el contexto del fragmento
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
        // Obtener el sistema de notificaciones
        NotificationManager notificationManager = (NotificationManager) requireContext().getSystemService(Context.NOTIFICATION_SERVICE);

        // Configurar un canal de notificación (si aún no está configurado)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String canalId = "canal_id";
            CharSequence nombreCanal = "Nombre del Canal";
            String descripcionCanal = "Descripción del Canal";
            int importancia = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel canal = new NotificationChannel(canalId, nombreCanal, importancia);
            canal.setDescription(descripcionCanal);

            // Registra el canal en el sistema; puedes personalizar más opciones aquí
            notificationManager.createNotificationChannel(canal);
        }

        // Crear un intent para la actividad que se abrirá al hacer clic en la notificación
        Intent intent = new Intent(requireContext(), SlideshowFragment.class);
        int requestCode = 1;  // Cambia esto a un valor único para cada notificación
        PendingIntent pendingIntent = PendingIntent.getActivity(requireContext(), requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Configurar el builder de la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), "canal_id")
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("Recordatorio")
                .setContentText("Tu evento está programado para mañana")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Calcular la hora para la notificación (un día antes)
        fechaNotificacion.add(Calendar.DAY_OF_MONTH, -1);
        long tiempoNotificacion = fechaNotificacion.getTimeInMillis();

        // Configurar el AlarmManager para la notificación
        AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, tiempoNotificacion, pendingIntent);

        int notificationId = 1; // Cambia esto a un valor único para cada notificación
        notificationManager.notify(notificationId, builder.build());
    }

}